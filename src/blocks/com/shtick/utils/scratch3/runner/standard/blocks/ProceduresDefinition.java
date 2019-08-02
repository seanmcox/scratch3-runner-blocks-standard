/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.shtick.utils.scratch3.runner.core.OpcodeHat;
import com.shtick.utils.scratch3.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch3.runner.core.ScratchRuntime;
import com.shtick.utils.scratch3.runner.core.SoundMonitor;
import com.shtick.utils.scratch3.runner.core.ValueListener;
import com.shtick.utils.scratch3.runner.core.elements.Broadcast;
import com.shtick.utils.scratch3.runner.core.elements.List;
import com.shtick.utils.scratch3.runner.core.elements.Mutation;
import com.shtick.utils.scratch3.runner.core.elements.Script;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.elements.Target;
import com.shtick.utils.scratch3.runner.core.elements.TypedValue;
import com.shtick.utils.scratch3.runner.core.elements.Variable;

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
public class ProceduresDefinition implements OpcodeHat {
	private static final java.util.Map<String,DataType> argumentTypes;
	private HashMap<ScriptContext,HashMap<String,ProcedureDefinition>> listenersByContextObject = new HashMap<>();
	static {
		HashMap<String, DataType> protoArgumentTypes = new HashMap<>(1);
		protoArgumentTypes.put("custom_block", DataType.POINTER_META);
		argumentTypes = Collections.unmodifiableMap(protoArgumentTypes);
	}
	
	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch.runner.core.Opcode#getOpcode()
	 */
	@Override
	public String getOpcode() {
		return "procedures_definition";
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
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#registerListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map)
	 */
	@Override
	public void registerListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		Map<String, Object> typeArguments = (Map<String, Object>)arguments.get("custom_block");
		Mutation prototypeMutation = (Mutation)typeArguments.remove("mutation");
		
		boolean isAtomic = false;
		
		System.out.println(this.getClass().getCanonicalName());
		System.out.println(prototypeMutation);

		ScriptContext contextObject = script.getContext().getContextObject();
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject))
				listenersByContextObject.put(contextObject, new HashMap<>());
			listenersByContextObject.get(contextObject).put(
					prototypeMutation.getProccode(),
					new ProcedureDefinition(prototypeMutation.getProccode(),typeArguments.keySet().toArray(new String[typeArguments.size()]), script, isAtomic)
			);
		}
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.OpcodeHat#unregisterListeningScript(com.shtick.utils.scratch3.runner.core.elements.Script, java.util.Map)
	 */
	@Override
	public void unregisterListeningScript(Script script, Map<String, Object> arguments, Mutation mutation) {
		Mutation prototypeMutation = (Mutation)arguments.get("mutation");
		ScriptContext contextObject = script.getContext().getContextObject();
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject))
				return;
			HashMap<String,ProcedureDefinition> listeners = listenersByContextObject.get(contextObject);
			listeners.remove(prototypeMutation.getProccode());
			if(listeners.size()==0)
				listenersByContextObject.remove(contextObject);
		}
	}
	
	/**
	 * @param context
	 * @param procName 
	 * @param params 
	 * @return An OpcodeSubaction of type SUBSCRIPT giving the subscript to be run.
	 * 
	 */
	public OpcodeSubaction call(ScriptContext context, String procName, Object[] params) {
		ScriptContext contextObject = context.getContextObject();
		ProcedureDefinition procDef;
		synchronized(listenersByContextObject) {
			if(!listenersByContextObject.containsKey(contextObject)) {
				String objectString = ""+contextObject;
				if(contextObject != null)
					objectString = "\""+contextObject.getObjName()+"\" - "+((contextObject instanceof Target)?((Target)contextObject).isClone():"")+" - "+contextObject;
				throw new IllegalArgumentException("The provided context object has no procedures defined: "+objectString+"\nTried to call: "+procName);
			}
			procDef = listenersByContextObject.get(contextObject).get(procName);
		}
		if(procDef==null) {
			String objectString = "\""+contextObject.getObjName()+"\" - "+contextObject;
			throw new IllegalArgumentException("No script defined with the given name, "+procName+", within the context of "+objectString);
		}
		Object[] parameters = new Object[params.length];
		for(int i=0;i<parameters.length;i++)
			parameters[i] = params[i];
		
		return new OpcodeSubaction() {
			
			@Override
			public boolean shouldYield() {
				return false;
			}
			
			@Override
			public Type getType() {
				return Type.SUBSCRIPT;
			}
			
			@Override
			public Script getSubscript() {
				return procDef.script.clone(new ProcedureContext(context, procDef.getProcName(), procDef.getParamNames(), parameters));
			}

			@Override
			public boolean isSubscriptAtomic() {
				return procDef.isAtomic();
			}
		};
	}

	private class ProcedureDefinition{
		private String procName;
		private String[] paramNames;
		private Script script;
		private boolean isAtomic;
		
		/**
		 * @param procName 
		 * @param paramNames
		 * @param script
		 * @param isAtomic
		 */
		public ProcedureDefinition(String procName, String[] paramNames, Script script, boolean isAtomic) {
			super();
			this.procName = procName;
			this.paramNames = paramNames;
			this.script = script;
			this.isAtomic = isAtomic;
		}

		/**
		 * @return the procName
		 */
		public String getProcName() {
			return procName;
		}

		/**
		 * @return the paramNames
		 */
		public String[] getParamNames() {
			return paramNames;
		}

		/**
		 * @return the script
		 */
		public Script getScript() {
			return script;
		}

		/**
		 * @return the isAtomic
		 */
		public boolean isAtomic() {
			return isAtomic;
		}
	}


	/**
	 * @author sean.cox
	 *
	 */
	public static class ProcedureContext implements ScriptContext{
		private ScriptContext parentContext;
		private HashMap<String,Object> parameters;
		private String procName;

		/**
		 * @param parentContext
		 * @param procName 
		 * @param paramNames 
		 * @param paramValues Values. Not BlockTuples
		 */
		public ProcedureContext(ScriptContext parentContext, String procName, String[] paramNames, Object[] paramValues) {
			this.parentContext = parentContext;
			this.procName = procName;
			parameters = new HashMap<>(paramNames.length);
			for(int i=0;i<paramNames.length;i++)
				parameters.put(paramNames[i], paramValues[i]);
		}
		
		/**
		 * @return the procName
		 */
		public String getProcName() {
			return procName;
		}

		/**
		 * 
		 * @param name
		 * @return The value of the given parameter or null if it is not defined.
		 */
		public Object getParameterValueByName(String name) {
			return parameters.get(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextObject()
		 */
		@Override
		public ScriptContext getContextObject() {
			return parentContext.getContextObject();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getObjName()
		 */
		@Override
		public String getObjName() {
			return getContextObject().getObjName();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextListByName(java.lang.String)
		 */
		@Override
		public List getContextListByName(String name) {
			return parentContext.getContextListByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextVariableByName(java.lang.String)
		 */
		@Override
		public Variable getContextVariableByName(String name) {
			return parentContext.getContextVariableByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextBroadcastByName(java.lang.String)
		 */
		@Override
		public Broadcast getContextBroadcastByName(String name) {
			return parentContext.getContextBroadcastByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextVariableValueByName(java.lang.String)
		 */
		@Override
		public Object getContextVariableValueByName(String name) throws IllegalArgumentException {
			return parentContext.getContextVariableValueByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#setContextVariableValueByName(java.lang.String, java.lang.Object)
		 */
		@Override
		public void setContextVariableValueByName(String name, Object value) throws IllegalArgumentException {
			parentContext.setContextVariableValueByName(name, value);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getContextPropertyValueByName(java.lang.String)
		 */
		@Override
		public Object getContextPropertyValueByName(String name) throws IllegalArgumentException {
			return parentContext.getContextPropertyValueByName(name);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#playSoundByName(java.lang.String, boolean)
		 */
		@Override
		public SoundMonitor playSoundByName(String soundName) {
			return parentContext.playSoundByName(soundName);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#playSoundByIndex(int)
		 */
		@Override
		public SoundMonitor playSoundByIndex(int index) {
			return parentContext.playSoundByIndex(index);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#setVolume(int)
		 */
		@Override
		public void setVolume(double volume) {
			parentContext.setVolume(volume);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#getVolume()
		 */
		@Override
		public double getVolume() {
			return parentContext.getVolume();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#stopThreads()
		 */
		@Override
		public void stopScripts() {
			parentContext.stopScripts();
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#addVariableListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void addVariableListener(String var, ValueListener listener) {
			parentContext.addVariableListener(var, listener);
		}

		/* (non-Javadoc)
		 * @see com.shtick.utils.scratch.runner.core.elements.ScriptContext#removeVariableListener(java.lang.String, com.shtick.utils.scratch.runner.core.ValueListener)
		 */
		@Override
		public void removeVariableListener(String var, ValueListener listener) {
			parentContext.removeVariableListener(var, listener);
		}
	}
}
