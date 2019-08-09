package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.elements.Broadcast;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.standard.blocks.EventBroadcast;
import com.shtick.utils.scratch3.runner.standard.blocks.EventWhenBroadcastReceived;
import com.shtick.utils.scratch3.runner.standard.blocks.test.EventWhenBroadcastReceivedTest.ScriptStartingRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadScript;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class EventBroadcastTest {

	@Test
	void testOpcode() {
		EventBroadcast op = new EventBroadcast(new EventWhenBroadcastReceived());
		assertEquals("event_broadcast",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		EventBroadcast op = new EventBroadcast(new EventWhenBroadcastReceived());
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("BROADCAST_INPUT", DataType.POINTER_BROADCAST);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		EventWhenBroadcastReceived eventWhenBroadcastReceived = new EventWhenBroadcastReceived();
		EventBroadcast op = new EventBroadcast(eventWhenBroadcastReceived);
		
		ScriptStartingRuntime runtime = new ScriptStartingRuntime();
		eventWhenBroadcastReceived.applicationStarted(runtime);
		Script script0 = new AllBadScript();
		HashMap<String,Object> registerArgs0 = new HashMap<String,Object>();
		{
			registerArgs0.put("BROADCAST_OPTION",new Broadcast() {
				@Override
				public String getName() {
					return "message0";
				}
				
				@Override
				public String getID() {
					return null;
				}
			});
		}
		eventWhenBroadcastReceived.registerListeningScript(script0, registerArgs0,new AllBadMutation());
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("BROADCAST_INPUT",new TestBroadcast("message0"));
		OpcodeSubaction subaction = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments, new AllBadMutation());
		assertNull(subaction);
		assertEquals(script0, runtime.scriptRun);
	}
	
	public static class TestBroadcast implements Broadcast{
		public String name;
		
		public TestBroadcast(String name) {
			this.name = name;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.Broadcast#getID()
		 */
		@Override
		public String getID() {
			return null;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.Broadcast#getName()
		 */
		@Override
		public String getName() {
			return name;
		}
	}
}
