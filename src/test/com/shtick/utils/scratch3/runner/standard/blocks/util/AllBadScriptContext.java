/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks.util;

import com.shtick.utils.scratch3.runner.core.SoundMonitor;
import com.shtick.utils.scratch3.runner.core.ValueListener;
import com.shtick.utils.scratch3.runner.core.elements.Broadcast;
import com.shtick.utils.scratch3.runner.core.elements.List;
import com.shtick.utils.scratch3.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch3.runner.core.elements.Variable;

/**
 * @author scox
 *
 */
public class AllBadScriptContext implements ScriptContext {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextObject()
	 */
	@Override
	public ScriptContext getContextObject() {
		throw new UnsupportedOperationException("Called getContextObject when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getObjName()
	 */
	@Override
	public String getObjName() {
		throw new UnsupportedOperationException("Called getObjName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextListByName(java.lang.String)
	 */
	@Override
	public List getContextListByName(String name) {
		throw new UnsupportedOperationException("Called getContextListByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextVariableValueByName(java.lang.String)
	 */
	@Override
	public Object getContextVariableValueByName(String name) {
		throw new UnsupportedOperationException("Called getContextVariableValueByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextVariableByName(java.lang.String)
	 */
	@Override
	public Variable getContextVariableByName(String name) {
		throw new UnsupportedOperationException("Called getContextVariableByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextBroadcastByName(java.lang.String)
	 */
	@Override
	public Broadcast getContextBroadcastByName(String name) {
		throw new UnsupportedOperationException("Called getContextBroadcastByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#setContextVariableValueByName(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setContextVariableValueByName(String name, Object value) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Called setContextVariableValueByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#addVariableListener(java.lang.String, com.shtick.utils.scratch3.runner.core.ValueListener)
	 */
	@Override
	public void addVariableListener(String var, ValueListener listener) {
		throw new UnsupportedOperationException("Called addVariableListener when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#removeVariableListener(java.lang.String, com.shtick.utils.scratch3.runner.core.ValueListener)
	 */
	@Override
	public void removeVariableListener(String var, ValueListener listener) {
		throw new UnsupportedOperationException("Called removeVariableListener when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getContextPropertyValueByName(java.lang.String)
	 */
	@Override
	public Object getContextPropertyValueByName(String name) throws IllegalArgumentException {
		throw new UnsupportedOperationException("Called getContextPropertyValueByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#playSoundByName(java.lang.String)
	 */
	@Override
	public SoundMonitor playSoundByName(String soundName) {
		throw new UnsupportedOperationException("Called playSoundByName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#playSoundByIndex(int)
	 */
	@Override
	public SoundMonitor playSoundByIndex(int index) {
		throw new UnsupportedOperationException("Called playSoundByIndex when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#setVolume(double)
	 */
	@Override
	public void setVolume(double volume) {
		throw new UnsupportedOperationException("Called setVolume when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#getVolume()
	 */
	@Override
	public double getVolume() {
		throw new UnsupportedOperationException("Called getVolume when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.ScriptContext#stopScripts()
	 */
	@Override
	public void stopScripts() {
		throw new UnsupportedOperationException("Called stopScripts when not expected.");
	}
}
