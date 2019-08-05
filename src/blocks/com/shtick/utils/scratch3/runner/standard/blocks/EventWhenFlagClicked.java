/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//import com.shtick.utils.scratch.runner.standard.blocks.Timer;
import com.shtick.utils.scratch3.runner.core.OpcodeHat;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.Script;

/**
 * <p>This opcode isn't well documented. Somewhat irritatingly, the definition derived from inspection breaks the conventions of the ScriptTuple encoding.
 * In particular, it has has array parameters that are not BlockTuples or ScriptTuples.
 * The third parameter, in particular cannot even reliably be treated as if it were a BlockTuple, because its first element cannot be
 * relied on to be a String.</p>
 *
 * <p>See:
 * <ul>
 * <li>https://scratch.mit.edu/discuss/topic/77188/?page=1#post-826804</li>
 * <li>https://wiki.scratch.mit.edu/wiki/JSON_Tutorial</li>
 * <li>https://scratch.mit.edu/discuss/topic/1810/?page=7</li>
 * </ul>
 * </p>
 * 
 * @author sean.cox
 *
 */
public class EventWhenFlagClicked implements OpcodeHat {
	private static final java.util.Map<String,DataType> argumentTypes;
	private LinkedList<Script> listeners = new LinkedList<>();
//	private Timer timerOpcode;
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>();
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}
	
	/**
	 * 
	 */
	public EventWhenFlagClicked(/*com.shtick.utils.scratch3.runner.standard.blocks.Timer timerOpcode*/) {
//		this.timerOpcode = timerOpcode;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "event_whenflagclicked";
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getArgumentTypes()
	 */
	@Override
	public Map<String,DataType> getArgumentTypes() {
		return argumentTypes;
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.OpcodeHat#applicationStarted(com.shtick.utils.scratch.runner.core.ScratchRuntime)
	 */
	@Override
	public void applicationStarted(ScratchRuntime runtime) {
//		timerOpcode.tare();
		synchronized(listeners) {
			for(Script tuple:listeners)
				runtime.startScript(tuple);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map)
	 */
	@Override
	public void registerListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		listeners.add(script);
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map)
	 */
	@Override
	public void unregisterListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		listeners.remove(script);
	}
}
