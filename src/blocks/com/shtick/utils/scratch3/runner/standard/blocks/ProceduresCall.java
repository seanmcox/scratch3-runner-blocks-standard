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
public class ProceduresCall implements OpcodeAction {
	private ProceduresDefinition proceduresDefinition;

	/**
	 * @param proceduresDefinition 
	 * 
	 */
	public ProceduresCall(ProceduresDefinition proceduresDefinition) {
		this.proceduresDefinition = proceduresDefinition;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "procedures_call";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String,DataType> getArgumentTypes() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeAction#execute(com.shtick.utils.scratch3.runner.core.ScratchRuntime, com.shtick.utils.scratch3.runner.core.ScriptTupleRunner, com.shtick.utils.scratch3.runner.core.elements.ScriptContext, java.util.Map)
	 */
	@Override
	public OpcodeSubaction execute(
			ScratchRuntime runtime,
			ScriptTupleRunner scriptRunner,
			ScriptContext context, Map<String, Object> arguments, Mutation mutation) {
		String proccode = mutation.getProccode();
		return proceduresDefinition.call(context, proccode, arguments);
	}
}
