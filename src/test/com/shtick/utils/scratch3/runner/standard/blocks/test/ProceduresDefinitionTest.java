package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresDefinition;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresDefinition.ProcedureContext;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScriptContext;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class ProceduresDefinitionTest {

	@Test
	void testOpcode() {
		ProceduresDefinition op = new ProceduresDefinition();
		assertEquals("procedures_definition",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ProceduresDefinition op = new ProceduresDefinition();
		HashMap<String,DataType> expected =  new HashMap<String,DataType>();
		expected.put("custom_block", DataType.POINTER_META);
		assertEquals(expected, op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		ProceduresDefinition op = new ProceduresDefinition();

		ScriptStartingRuntime runtime = new ScriptStartingRuntime();
		op.applicationStarted(runtime);
	}

	@SuppressWarnings("serial")
	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			ProceduresDefinition op = new ProceduresDefinition();
			ScriptStartingRuntime runtime = new ScriptStartingRuntime();
			op.applicationStarted(runtime);
			ScriptContext context = new ObjectifiedContext();
			ContextedScript script0 = new ContextedScript(context);
			
			op.applicationStarted(runtime);
			
			TestMutation testMutation = new TestMutation("proccode");
			HashMap<String,Object> custom_block = new HashMap<String,Object>();
			custom_block.put("arg_id","local_name");
			custom_block.put("mutation",testMutation);
			HashMap<String,Object> arguments = new HashMap<String,Object>();
			arguments.put("custom_block", custom_block);
			op.registerListeningScript(script0, arguments,new AllBadMutation());

			HashMap<String,Object> call_params = new HashMap<String,Object>();
			call_params.put("arg_id","value");
			OpcodeSubaction subaction = op.call(context, testMutation.proccode, call_params);

			assertNotNull(subaction);
			assertEquals(OpcodeSubaction.Type.SUBSCRIPT, subaction.getType());
			assertEquals(false, subaction.isSubscriptAtomic());
			assertEquals(false, subaction.shouldYield());
			Script subscript = subaction.getSubscript();
			assertTrue(subscript.getContext() instanceof ProcedureContext);
			ProcedureContext procedureContext = (ProcedureContext)subscript.getContext();
			assertEquals("proccode",procedureContext.getProcName());
			assertEquals(context,procedureContext.getContextObject());
			assertEquals("value",procedureContext.getParameterValueByName("local_name"));
			
			op.unregisterListeningScript(script0, arguments,new AllBadMutation());
			try {
				subaction = op.call(context, testMutation.proccode, call_params);
				fail("Exception should have been thrown.");
			}
			catch (IllegalArgumentException t) {
				// Exception thrown as expected.
			}
		}
	}
		
	public static class ScriptStartingRuntime extends AllBadRuntime{
		public HashSet<MouseListener> listeners = new HashSet<>();
		public HashMap<Script,ScriptTupleRunner> runners = new HashMap<>();
		public Script scriptRun = null;

		public ScriptStartingRuntime() {
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.standard.blocks.util.AllBadRuntime#startScript(com.shtick.utils.scratch.runner.core.elements.ScriptTuple, boolean)
		 */
		@Override
		public ScriptTupleRunner startScript(Script script) {
			this.scriptRun = script;
			ScriptTupleRunner retval;
			retval = new AllBadRunner();
			runners.put(script,retval);
			return retval;
		}
	}
	
	/**
	 * 
	 * @author scox
	 *
	 */
	public static class TestMutation extends AllBadMutation{
		/**
		 * 
		 */
		public String proccode;
		
		/**
		 * 
		 * @param proccode
		 */
		public TestMutation(String proccode) {
			this.proccode = proccode;
		}
		
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation#getProccode()
		 */
		@Override
		public String getProccode() {
			return proccode;
		}
	}

	/**
	 * 
	 * @author scox
	 *
	 */
	public static class ContextedScript extends AllBadScript {
		public ScriptContext scriptContext;
		
		public ContextedScript(ScriptContext scriptContext) {
			this.scriptContext = scriptContext;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript#getContext()
		 */
		@Override
		public ScriptContext getContext() {
			return scriptContext;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript#clone(com.shtick.utils.scratch3.runner.core.elements.ScriptContext)
		 */
		@Override
		public Script clone(ScriptContext context) {
			return new ContextedScript(context);
		}
	}
	
	/**
	 * 
	 * @author scox
	 *
	 */
	public static class ObjectifiedContext extends AllBadScriptContext{
		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScriptContext#getObjName()
		 */
		@Override
		public String getObjName() {
			return "ObjectifiedContext";
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScriptContext#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return this;
		}
	}
}
