/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.HashMap;
import java.util.Map;

import com.shtick.utils.scratch3.runner.core.OpcodeValue;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;

/**
 * @author scox
 *
 */
public class ArgumentReporterStringNumber implements OpcodeValue{
	private HashMap<String, DataType> argumentTypes = new HashMap<>();

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "argument_reporter_string_number";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String, DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeValue#execute(com.shtick.utils.scratch3.runner.core.ScratchRuntime, com.shtick.utils.scratch3.runner.core.ScriptTupleRunner, com.shtick.utils.scratch3.runner.core.elements.ScriptContext, java.util.Map)
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context,
			Map<String, Object> arguments, Mutation mutation) {
		String parameterLocalName = ((String[])arguments.get("VALUE"))[0];
		if(context instanceof ProceduresDefinition.ProcedureContext)
			return ((ProceduresDefinition.ProcedureContext)context).getParameterValueByName(parameterLocalName);
		return parameterLocalName; // This is for reporting the parameter name to the procedures_prototype opcode.
	}
}
