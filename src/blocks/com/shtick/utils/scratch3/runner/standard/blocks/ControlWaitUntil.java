/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.shtick.utils.scratch3.runner.core.OpcodeControl;
import com.shtick.utils.scratch3.runner.core.elements.Block;
import com.shtick.utils.scratch3.runner.core.elements.control.FalseJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.TestBlock;

/**
 * @author scox
 *
 */
public class ControlWaitUntil implements OpcodeControl {
	private static Map<String, DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>(1);
		protoArgumentTypes.put("CONDITION", DataType.BOOLEAN);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "control_wait_until";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String, DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeControl#execute(java.util.Map)
	 */
	@Override
	public Block[] execute(Map<String, Object> arguments) {
		System.out.println(arguments);
		Block[] retval = new Block[2];
		retval[0] = new TestBlock(arguments.get("CONDITION"));
		retval[1] = new FalseJumpBlock(0);
		return retval;
	}

}
