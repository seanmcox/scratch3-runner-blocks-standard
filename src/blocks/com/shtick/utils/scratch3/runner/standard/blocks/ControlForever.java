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

/**
 * @author scox
 *
 */
public class ControlForever implements OpcodeControl {
	private static Map<String, DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>(1);
		protoArgumentTypes.put("SUBSTACK", DataType.SCRIPT);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "control_forever";
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
		Block[] retval = new Block[2];
		retval[0] = new CodeHeadBlock((String)((TypedValue)arguments.get("SUBSTACK")).getValue().get(0));
		retval[1] = new BasicJumpBlock(0);
		return retval;
	}

}
