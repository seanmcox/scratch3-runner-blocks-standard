/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.shtick.utils.scratch3.runner.core.OpcodeControl;
import com.shtick.utils.scratch3.runner.core.elements.Block;
import com.shtick.utils.scratch3.runner.core.elements.TypedValue;
import com.shtick.utils.scratch3.runner.core.elements.control.BasicJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.CodeHeadBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.FalseJumpBlock;
import com.shtick.utils.scratch3.runner.core.elements.control.TestBlock;

/**
 * @author scox
 *
 */
public class ControlIfElse implements OpcodeControl {
	private static Map<String, DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>(1);
		protoArgumentTypes.put("CONDITION", DataType.BOOLEAN);
		protoArgumentTypes.put("SUBSTACK", DataType.SCRIPT);
		protoArgumentTypes.put("SUBSTACK2", DataType.SCRIPT);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "control_if_else";
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
		Block[] retval = new Block[5];
		retval[0] = new TestBlock(arguments.get("CONDITION"));
		retval[1] = new FalseJumpBlock(4);
		retval[2] = new CodeHeadBlock((String)((TypedValue)arguments.get("SUBSTACK")).getValue().get(0));
		retval[3] = new BasicJumpBlock(5);
		retval[4] = new CodeHeadBlock((String)((TypedValue)arguments.get("SUBSTACK2")).getValue().get(0));
		return retval;
	}

}
