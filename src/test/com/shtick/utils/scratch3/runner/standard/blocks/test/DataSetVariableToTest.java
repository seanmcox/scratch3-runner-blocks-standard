package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.standard.blocks.DataSetVariableTo;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadVariable;

class DataSetVariableToTest {

	@Test
	void testOpcode() {
		DataSetVariableTo op = new DataSetVariableTo();
		assertEquals("data_setvariableto",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DataSetVariableTo op = new DataSetVariableTo();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("VALUE", DataType.OBJECT);
		expectedArguments.put("VARIABLE", DataType.POINTER_VARIABLE);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		DataSetVariableTo op = new DataSetVariableTo();
		
		CanSetVariable canSetVariable = new CanSetVariable();

		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("VALUE","something");
		arguments.put("VARIABLE",canSetVariable);
		OpcodeSubaction subaction = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments, new AllBadMutation());
		assertNull(subaction);
		assertEquals("something", canSetVariable.value);
	}
	
	public static class CanSetVariable extends AllBadVariable {
		public Object value;

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadVariable#setValue(java.lang.Object)
		 */
		@Override
		public void setValue(Object value) {
			this.value = value;
		}
		
	}
}
