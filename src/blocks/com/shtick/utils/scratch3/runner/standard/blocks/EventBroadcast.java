/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import com.shtick.utils.scratch3.runner.core.OpcodeAction;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sean.cox
 *
 */
public class EventBroadcast implements OpcodeAction {
	private static final java.util.Map<String,DataType> argumentTypes;
	private EventWhenBroadcastReceived eventWhenBroadcastReceived;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>();
		protoArgumentTypes.put("BROADCAST_INPUT", DataType.POINTER_BROADCAST);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/**
	 * 
	 * @param eventWhenBroadcastReceived
	 */
	public EventBroadcast(EventWhenBroadcastReceived eventWhenBroadcastReceived) {
		this.eventWhenBroadcastReceived = eventWhenBroadcastReceived;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "event_broadcast";
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
			ScriptContext context, Map<String, Object> arguments) {
		eventWhenBroadcastReceived.broadcast((String)arguments.get("BROADCAST_INPUT"));
		return null;
	}
}
