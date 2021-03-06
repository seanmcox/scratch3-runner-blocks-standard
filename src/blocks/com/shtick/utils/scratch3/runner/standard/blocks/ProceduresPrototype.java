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
public class ProceduresPrototype implements OpcodeValue {
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "procedures_prototype";
	}

	
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String, DataType> getArgumentTypes() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeValue#execute(com.shtick.utils.scratch3.runner.core.ScratchRuntime, com.shtick.utils.scratch3.runner.core.ScriptTupleRunner, com.shtick.utils.scratch3.runner.core.elements.ScriptContext, java.util.Map)
	 */
	@Override
	public Object execute(ScratchRuntime runtime, ScriptTupleRunner runner, ScriptContext context, Map<String, Object> arguments, Mutation mutation) {
		Map<String, Object> retval = new HashMap<>(arguments);
		retval.put("mutation", mutation);
		return retval;
	}
}
