/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

/**
 * An exception thrown when an invalid card is requested.
 * 
 * @author Adam Lock
 */
public class InvalidCardException extends Exception {
	/**
     * 
     */
	private static final long serialVersionUID = -2493771308441907080L;

	public InvalidCardException(String error) {
		super(error);
	}
}
