/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.OpcodeAction;

/**
 * @author scox
 *
 */
public class ControlWait implements OpcodeAction {
	private static Map<String, DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>(1);
		protoArgumentTypes.put("DURATION", DataType.NUMBER);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "control_wait";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String, DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeAction#execute(com.shtick.utils.scratch3.runner.core.ScratchRuntime, com.shtick.utils.scratch3.runner.core.ScriptTupleRunner, com.shtick.utils.scratch3.runner.core.elements.ScriptContext, java.util.Map)
	 */
	@Override
	public OpcodeSubaction execute(ScratchRuntime runtime, ScriptTupleRunner scriptRunner, ScriptContext context,
			Map<String, Object> arguments) {
		Number n0 = (Number)arguments.get("DURATION");
		final long startTime = System.currentTimeMillis();
		final int millis = (int)(n0.doubleValue()*1000);
		return new OpcodeSubaction() {
			
			@Override
			public boolean shouldYield() {
				if((System.currentTimeMillis()-startTime)<millis)
					return true;
				return false;
			}
			
			@Override
			public Type getType() {
				return Type.YIELD_CHECK;
			}
			
			@Override
			public Script getSubscript() {
				return null;
			}

			@Override
			public boolean isSubscriptAtomic() {
				return false;
			}
		};
	}
}
