package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresCall;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresDefinition;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresDefinition.ProcedureContext;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class ProceduresCallTest {

	@Test
	void testOpcode() {
		ProceduresCall op = new ProceduresCall(new ProceduresDefinition());
		assertEquals("procedures_call",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ProceduresCall op = new ProceduresCall(new ProceduresDefinition());
		assertNull(op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ProceduresDefinition proceduresDefinition = new ProceduresDefinition();
		ProceduresCall op = new ProceduresCall(proceduresDefinition);
		
		HashMap<String,Object> customBlock = new HashMap<>();
		customBlock.put("DURATION","125");
		customBlock.put("mutation", new ProccodeMutation("proccode"));
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("custom_block",customBlock);
		ScriptContext scriptContext = new ProceduresDefinitionTest.ObjectifiedContext();
		proceduresDefinition.registerListeningScript(new ProceduresDefinitionTest.ContextedScript(scriptContext), arguments, new AllBadMutation());
		HashMap<String,Object> callArguments = new HashMap<>();
		callArguments.put("DURATION","value");
		OpcodeSubaction subaction = op.execute(new AllBadRuntime(), new AllBadRunner(), new ObjectifiedTarget(scriptContext), callArguments, new ProccodeMutation("proccode"));
		assertTrue(subaction instanceof OpcodeSubaction);
		assertEquals(OpcodeSubaction.Type.SUBSCRIPT,subaction.getType());
		assertNotNull(subaction.getSubscript());
		assertFalse(subaction.isSubscriptAtomic());
		assertTrue(subaction.getSubscript().getContext() instanceof ProcedureContext);
		ProcedureContext procedureContext = (ProcedureContext)subaction.getSubscript().getContext();
		assertEquals("proccode",procedureContext.getProcName());
		assertEquals("value",procedureContext.getParameterValueByName("125"));
		assertEquals(scriptContext,procedureContext.getContextObject());
	}
	
	public static class ProccodeMutation extends AllBadMutation {
		public String proccode;
		
		public ProccodeMutation(String proccode) {
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
	
	public static class ObjectifiedTarget extends AllBadTarget{
		public ScriptContext context;

		public ObjectifiedTarget(ScriptContext context) {
			this.context = context;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			// TODO Auto-generated method stub
			return context;
		}
	}
}
