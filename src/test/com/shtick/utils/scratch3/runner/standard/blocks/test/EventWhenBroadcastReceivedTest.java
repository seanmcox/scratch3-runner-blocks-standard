package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Broadcast;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.standard.blocks.EventWhenBroadcastReceived;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript;

class EventWhenBroadcastReceivedTest {

	@Test
	void testOpcode() {
		EventWhenBroadcastReceived op = new EventWhenBroadcastReceived();
		assertEquals("event_whenbroadcastreceived",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		EventWhenBroadcastReceived op = new EventWhenBroadcastReceived();
		HashMap<String, DataType> argumentTypes = new HashMap<>();
		argumentTypes.put("BROADCAST_INPUT", DataType.POINTER_BROADCAST);
		assertEquals(argumentTypes, op.getArgumentTypes());
	}

	@Test
	void testApplicationStarted() {
		EventWhenBroadcastReceived op = new EventWhenBroadcastReceived();

		op.applicationStarted(new AllBadRuntime());
	}

	@Test
	void testRegisterUnregister() {
		{ // Test expected use case
			EventWhenBroadcastReceived op = new EventWhenBroadcastReceived();
			ScriptStartingRuntime runtime = new ScriptStartingRuntime();
			op.applicationStarted(runtime);
			Script script0 = new AllBadScript();
			Script script1 = new AllBadScript();
			
			HashMap<String,Object> registerArgs0 = new HashMap<String,Object>();
			HashMap<String,Object> registerArgs1 = new HashMap<String,Object>();
			{
				registerArgs0.put("BROADCAST_INPUT",new Broadcast() {
					@Override
					public String getName() {
						return "message0";
					}
					
					@Override
					public String getID() {
						return null;
					}
				});
				registerArgs1.put("BROADCAST_INPUT",new Broadcast() {
					@Override
					public String getName() {
						return "message1";
					}
					
					@Override
					public String getID() {
						return null;
					}
				});
			}
			
			assertNull(runtime.scriptRun);

			op.broadcast("message0");
			
			assertEquals(null, runtime.scriptRun);
			
			op.registerListeningScript(script0, registerArgs0,new AllBadMutation());
			op.broadcast("message0");
			assertEquals(script0, runtime.scriptRun);
			runtime.scriptRun = null;
			op.unregisterListeningScript(script0, registerArgs0,new AllBadMutation());
			op.broadcast("message0");
			assertNull(runtime.scriptRun);

			op.registerListeningScript(script1, registerArgs1,new AllBadMutation());
			op.broadcast("message1");
			assertEquals(script1, runtime.scriptRun);
			runtime.scriptRun = null;
			op.unregisterListeningScript(script1, registerArgs1,new AllBadMutation());
			op.broadcast("message1");
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
