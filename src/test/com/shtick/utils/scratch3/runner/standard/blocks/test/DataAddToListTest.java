package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.standard.blocks.DataAddToList;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadList;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class DataAddToListTest {

	@Test
	void testOpcode() {
		DataAddToList op = new DataAddToList();
		assertEquals("data_addtolist",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		DataAddToList op = new DataAddToList();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		expectedArguments.put("ITEM", DataType.OBJECT);
		expectedArguments.put("LIST", DataType.POINTER_LIST);
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		DataAddToList op = new DataAddToList();
		
		CanAddList canAddList = new CanAddList();
		LinkedList<Object> expectedList;

		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("ITEM","something");
		arguments.put("LIST",canAddList);
		OpcodeSubaction subaction = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments, new AllBadMutation());
		assertNull(subaction);
		expectedList = new LinkedList<>();
		expectedList.add("something");
		assertEquals(expectedList, canAddList.list);
	}
	
	public static class CanAddList extends AllBadList {
		public LinkedList<Object> list = new LinkedList<>();
		
		@Override
		public void addItem(Object item) {
			list.add(item);
		}
	}
}
