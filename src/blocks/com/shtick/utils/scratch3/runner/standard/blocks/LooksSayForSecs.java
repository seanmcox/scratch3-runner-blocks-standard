/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.core.OpcodeAction;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.elements.Target;
import com.shtick.utils.scratch3.runner.standard.StandardFeatureGenerator;

import java.awt.Image;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sean.cox
 *
 */
public class LooksSayForSecs implements OpcodeAction {
	private static final java.util.Map<String,DataType> argumentTypes;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>();
		protoArgumentTypes.put("MESSAGE", DataType.STRING);
		protoArgumentTypes.put("SECS", DataType.NUMBER);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "looks_sayforsecs";
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
		String message = (String)arguments.get("MESSAGE");
		Number secs = (Number)arguments.get("SECS");
		if(!(context.getContextObject() instanceof Target))
			throw new IllegalArgumentException(getOpcode()+" opcode only valid in Sprite context.");
		Target target = (Target)context.getContextObject();

		Image bubbleImage = StandardFeatureGenerator.createTalkBubbleImage(message);
		runtime.setSpriteBubbleImage(target, bubbleImage);
		final long startTime = System.currentTimeMillis();
		return new OpcodeSubaction() {
			
			@Override
			public boolean shouldYield() {
				if((System.currentTimeMillis()-startTime)<secs.doubleValue()*1000)
					return true;
				runtime.setSpriteBubbleImage(null,null);
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
