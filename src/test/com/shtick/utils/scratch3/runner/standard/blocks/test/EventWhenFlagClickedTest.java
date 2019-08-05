package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.standard.blocks.EventWhenFlagClicked;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript;

class EventWhenFlagClickedTest {

	@Test
	void testOpcode() {
		EventWhenFlagClicked op = new EventWhenFlagClicked();
		assertEquals("event_whenflagclicked",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		EventWhenFlagClicked op = new EventWhenFlagClicked();
		assertEquals(new HashMap<String,DataType>(), op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		EventWhenFlagClicked op = new EventWhenFlagClicked();

		ScriptStartingRuntime runtime = new ScriptStartingRuntime();
		op.applicationStarted(runtime);
		assertNull(runtime.scriptRun);
		
		op.registerListeningScript(new AllBadScript(), new HashMap<String,Object>(), new AllBadMutation());
	}

	@SuppressWarnings("serial")
	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			EventWhenFlagClicked op = new EventWhenFlagClicked();
			ScriptStartingRuntime runtime = new ScriptStartingRuntime();
			op.applicationStarted(runtime);
			Script scriptTuple0 = new AllBadScript();
			Script scriptTuple1 = new AllBadScript();
			
			assertNull(runtime.scriptRun);

			op.applicationStarted(runtime);
			
			assertEquals(null, runtime.scriptRun);
			
			op.registerListeningScript(scriptTuple0, new HashMap<String,Object>(),new AllBadMutation());
			op.applicationStarted(runtime);
			assertEquals(scriptTuple0, runtime.scriptRun);
			runtime.scriptRun = null;
			op.unregisterListeningScript(scriptTuple0, new HashMap<String,Object>(),new AllBadMutation());
			op.applicationStarted(runtime);
			assertNull(runtime.scriptRun);

			op.registerListeningScript(scriptTuple1, new HashMap<String,Object>(),new AllBadMutation());
			op.applicationStarted(runtime);
			assertEquals(scriptTuple1, runtime.scriptRun);
			runtime.scriptRun = null;
			op.unregisterListeningScript(scriptTuple1, new HashMap<String,Object>(),new AllBadMutation());
			op.applicationStarted(runtime);
			assertNull(runtime.scriptRun);
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
}
