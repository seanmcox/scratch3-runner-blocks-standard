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
import com.shtick.utils.scratch3.runner.standard.blocks.ControlForever;

class ControlForeverTest {

	@Test
	void testOpcode() {
		ControlForever op = new ControlForever();
		assertEquals("control_forever",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ControlForever op = new ControlForever();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("SUBSTACK", DataType.SCRIPT);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ControlForever op = new ControlForever();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("SUBSTACK",new TestTypedValue(TypedValue.TYPE_NOT_SHADOW, "id1"));
		Block[] result = op.execute(arguments);
		assertEquals(2,result.length);
		assertTrue(result[0] instanceof CodeHeadBlock);
		assertEquals("id1",((CodeHeadBlock)result[0]).getBlockID());
		assertTrue(result[1] instanceof BasicJumpBlock);
		assertEquals(0,((BasicJumpBlock)result[1]).getIndex());
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
