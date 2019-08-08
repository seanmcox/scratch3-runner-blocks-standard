package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.elements.Block;
import com.shtick.utils.scratch3.runner.core.elements.TypedValue;
import com.shtick.utils.scratch3.runner.core.elements.control.BasicJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.CodeHeadBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.FalseJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.TestBlock;
import com.shtick.utils.scratch3.runner.standard.blocks.ControlIfElse;

class ControlIfElseTest {

	@Test
	void testOpcode() {
		ControlIfElse op = new ControlIfElse();
		assertEquals("control_if_else",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ControlIfElse op = new ControlIfElse();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("CONDITION", DataType.BOOLEAN);
		expectedArguments.put("SUBSTACK", DataType.SCRIPT);
		expectedArguments.put("SUBSTACK2", DataType.SCRIPT);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ControlIfElse op = new ControlIfElse();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("CONDITION",Boolean.TRUE);
		arguments.put("SUBSTACK",new TestTypedValue(TypedValue.TYPE_NOT_SHADOW, "id1"));
		arguments.put("SUBSTACK2",new TestTypedValue(TypedValue.TYPE_NOT_SHADOW, "id2"));
		Block[] result = op.execute(arguments);
		assertEquals(5,result.length);
		assertTrue(result[0] instanceof TestBlock);
		assertEquals(arguments.get("CONDITION"),((TestBlock)result[0]).getTest());
		assertTrue(result[1] instanceof FalseJumpBlock);
		assertEquals(4,((FalseJumpBlock)result[1]).getIndex());
		assertTrue(result[2] instanceof CodeHeadBlock);
		assertEquals("id1",((CodeHeadBlock)result[2]).getBlockID());
		assertTrue(result[3] instanceof BasicJumpBlock);
		assertEquals(5,((BasicJumpBlock)result[3]).getIndex());
		assertTrue(result[4] instanceof CodeHeadBlock);
		assertEquals("id2",((CodeHeadBlock)result[4]).getBlockID());
	}
	
	public static class TestTypedValue implements TypedValue{
		public int type;
		public List<Object> value;
		
		public TestTypedValue(int type, String value) {
			this.type = type;
			this.value = new LinkedList<>();
			this.value.add(value);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.TypedValue#getType()
		 */
		@Override
		public int getType() {
			return type;
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.TypedValue#getValue()
		 */
		@Override
		public List<Object> getValue() {
			return value;
		}
	}
}
