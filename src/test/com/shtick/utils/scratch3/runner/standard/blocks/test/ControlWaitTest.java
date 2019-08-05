package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.standard.blocks.ControlWait;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class ControlWaitTest {

	@Test
	void testOpcode() {
		ControlWait op = new ControlWait();
		assertEquals("control_wait",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ControlWait op = new ControlWait();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("DURATION", DataType.NUMBER);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ControlWait op = new ControlWait();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("DURATION",125);
		OpcodeSubaction subaction = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments);
		assertEquals(OpcodeSubaction.Type.YIELD_CHECK,subaction.getType());
		assertNull(subaction.getSubscript());
		assertFalse(subaction.isSubscriptAtomic());
	}
}
