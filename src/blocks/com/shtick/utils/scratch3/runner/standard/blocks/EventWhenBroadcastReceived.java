/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.shtick.utils.scratch3.runner.core.OpcodeHat;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch3.runner.core.elements.Broadcast;
import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.Script;

/**
 * @author sean.cox
 *
 */
public class EventWhenBroadcastReceived implements OpcodeHat {
	private static final java.util.Map<String,DataType> argumentTypes;
	private TreeMap<String,java.util.List<Script>> listeners = new TreeMap<>();
	private ScratchRuntime runtime;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>();
		protoArgumentTypes.put("BROADCAST_OPTION", DataType.POINTER_BROADCAST);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "event_whenbroadcastreceived";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String, DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
		this.runtime = runtime;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map, com.shtick.utils.scratch3.runner.core.elements.Mutation)
	 */
	@Override
	public void registerListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		synchronized(listeners) {
			Broadcast broadcast = (Broadcast)arguments.get("BROADCAST_OPTION");
			if(!listeners.containsKey(broadcast.getName()))
				listeners.put(broadcast.getName(), new LinkedList<>());
			listeners.get(broadcast.getName()).add(script);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map, com.shtick.utils.scratch3.runner.core.elements.Mutation)
	 */
	@Override
	public void unregisterListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		synchronized(listeners) {
			Broadcast broadcast = (Broadcast)arguments.get("BROADCAST_OPTION");
			if(!listeners.containsKey(broadcast.getName()))
				return;
			java.util.List<Script> scripts = listeners.get(broadcast.getName());
			scripts.remove(script);
			if(scripts.size()==0)
				listeners.remove(broadcast.getName());
		}
	}

	/**
	 * 
	 * @param message
	 * @return A Set of ScriptTupleRunners. Modification of the set has no side-effects.
	 */
	public Set<ScriptTupleRunner> broadcast(String message) {
		java.util.List<Script> messageListeners;
		synchronized(listeners) {
			messageListeners = listeners.get(message);
			if(messageListeners == null)
				return new HashSet<>(1);
			messageListeners = new LinkedList<>(messageListeners);
		}
		HashSet<ScriptTupleRunner> runners = new HashSet<>(messageListeners.size());
		for(Script tuple:messageListeners)
			runners.add(runtime.startScript(tuple));
		return runners;
	}
}
