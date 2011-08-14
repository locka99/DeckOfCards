/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

/**
 * An exception thrown when a deck is empty and no more cards can be dealt from
 * it.
 * 
 * @author Adam Lock
 */
public class EmptyDeckException extends Exception {
	/**
     * 
     */
	private static final long serialVersionUID = -3197098236716206458L;

	public EmptyDeckException() {
	}
}
