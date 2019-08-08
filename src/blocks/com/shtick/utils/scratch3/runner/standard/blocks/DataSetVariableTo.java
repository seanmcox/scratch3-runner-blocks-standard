/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import com.shtick.utils.scratch3.runner.core.OpcodeAction;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.elements.Variable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sean.cox
 *
 */
public class DataSetVariableTo implements OpcodeAction {
	private static final java.util.Map<String,DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>();
		protoArgumentTypes.put("VALUE", DataType.OBJECT);
		protoArgumentTypes.put("VARIABLE", DataType.POINTER_VARIABLE);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "data_setvariableto";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String,DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeAction#execute(com.shtick.utils.scratch3.runner.core.ScratchRuntime, com.shtick.utils.scratch3.runner.core.ScriptTupleRunner, com.shtick.utils.scratch3.runner.core.elements.ScriptContext, java.util.Map)
	 */
	@Override
	public OpcodeSubaction execute(
			ScratchRuntime runtime,
			ScriptTupleRunner scriptRunner,
			ScriptContext context, Map<String, Object> arguments, Mutation mutation) {
		Object value = arguments.get("VALUE");
		Variable var = (Variable)arguments.get("VARIABLE");
		var.setValue(value);
		return null;
	}
}
