package com.shtick.utils.scratch3.runner.standard.blocks.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.shtick.utils.scratch3.runner.core.Opcode.DataType;
import com.shtick.utils.scratch3.runner.standard.blocks.ArgumentReporterStringNumber;
import com.shtick.utils.scratch3.runner.standard.blocks.ProceduresDefinition.ProcedureContext;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadMutation;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRunner;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadRuntime;
import com.shtick.utils.scratch3.runner.standard.blocks.util.AllBadTarget;

class ArgumentReporterStringNumberTest {

	@Test
	void testOpcode() {
		ArgumentReporterStringNumber op = new ArgumentReporterStringNumber();
		assertEquals("argument_reporter_string_number",op.getOpcode());
	}

	@Test
	void testArgumentTypes() {
		ArgumentReporterStringNumber op = new ArgumentReporterStringNumber();
		HashMap<String,DataType> expectedArguments = new HashMap<>();
		assertEquals(expectedArguments, op.getArgumentTypes());
	}

	@Test
	void testExecution() {
		ArgumentReporterStringNumber op = new ArgumentReporterStringNumber();
		
		HashMap<String,Object> arguments = new HashMap<>();
		arguments.put("VALUE", new String[] {"localname"});
		Object result = op.execute(new AllBadRuntime(), new AllBadRunner(), new AllBadTarget(), arguments, new AllBadMutation());
		assertEquals("localname", result);

		// Test with a ProcedureDefinition.ProcedureContext
		HashMap<String,Object> parameters = new HashMap<>();
		parameters.put("localname", "macarena");
		ProcedureContext procedureContext = new ProcedureContext(new AllBadTarget(), "proc", parameters);
		result = op.execute(new AllBadRuntime(), new AllBadRunner(), procedureContext, arguments, new AllBadMutation());
		assertEquals("macarena", result);
	}
}
