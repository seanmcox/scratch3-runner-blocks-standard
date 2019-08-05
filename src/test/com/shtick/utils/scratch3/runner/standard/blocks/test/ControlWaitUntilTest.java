package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.elements.Block;
import com.shtick.utils.scratch3.runner.core.elements.control.FalseJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.TestBlock;
import com.shtick.utils.scratch3.runner.standard.blocks.ControlWaitUntil;

class ControlWaitUntilTest {

	@Test
	void testOpcode() {
		ControlWaitUntil op = new ControlWaitUntil();
		assertEquals("control_wait_until",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ControlWaitUntil op = new ControlWaitUntil();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("CONDITION", DataType.BOOLEAN);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ControlWaitUntil op = new ControlWaitUntil();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("CONDITION",Boolean.TRUE);
		Block[] result = op.execute(arguments);
		assertEquals(2,result.length);
		assertTrue(result[0] instanceof TestBlock);
		assertTrue(arguments.get("CONDITION")==((TestBlock)result[0]).getTest());
		assertTrue(result[1] instanceof FalseJumpBlock);
		assertEquals(0,((FalseJumpBlock)result[1]).getIndex());
	}
}
