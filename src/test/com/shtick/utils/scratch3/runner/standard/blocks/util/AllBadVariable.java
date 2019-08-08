/**
 * 
 */
package com.shtick.utils.scratch3.runner.standard.blocks.util;

import com.shtick.utils.scratch3.runner.core.elements.Variable;

/**
 * @author scox
 *
 */
public class AllBadVariable implements Variable {

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.Variable#getName()
	 */
	@Override
	public String getName() {
		throw new UnsupportedOperationException("Called getName when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.Variable#getValue()
	 */
	@Override
	public Object getValue() {
		throw new UnsupportedOperationException("Called getValue when not expected.");
	}

	/* (non-Javadoc)
	 * @see com.shtick.utils.scratch3.runner.core.elements.Variable#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		throw new UnsupportedOperationException("Called setValue when not expected.");
	}

}
