package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresPrototype;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class ProceduresPrototypeTest {

	@Test
	void testOpcode() {
		ProceduresPrototype op = new ProceduresPrototype();
		assertEquals("procedures_prototype",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ProceduresPrototype op = new ProceduresPrototype();
		assertNull(op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ProceduresPrototype op = new ProceduresPrototype();
		
		AllBadMutation mutation = new AllBadMutation();
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("VALUE", new String[] {"localname"});
		HashMap<String,Object> expectedResult = new HashMap<>(arguments);
		expectedResult.put("mutation", mutation);
		Object result = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments, mutation);
		assertEquals(expectedResult, result);
	}
}
