/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

/**
 * @author Adam Lock
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class InvalidCardException extends Exception {
	/**
     * 
     */
	private static final long serialVersionUID = -2493771308441907080L;

	private final CardPattern cardPattern;
	
	public InvalidCardException(String error) {
		super(error);
		this.cardPattern = null;
	}
	
	public InvalidCardException(CardPattern cardPattern, String error) {
		super(error);
		this.cardPattern = cardPattern;
	}

	public CardPattern getCardPattern() {
		return cardPattern;
	}
}
