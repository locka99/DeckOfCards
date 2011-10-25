/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

public enum CardSuit {
	/**
	 * Clubs
	 */
	CLUBS('c'),
	/**
	 * Spades
	 */
	SPADES('s'),
	/**
	 * Hearts
	 */
	HEARTS('h'),
	/**
	 * Diamonds
	 */
	DIAMONDS('d');

	private final static String SUITS = "cshd";
	
	private char suit;

	private CardSuit(char suit) {
		this.suit = suit;
	}

	public char getSuit() {
		return suit;
	}

	/**
	 * Class helper method tests if the specified suit is valid
	 * 
	 * @param suit
	 *            suit to test
	 * @return true if the parameter is a valid suit, false otherwise
	 */
	static public boolean isValidSuit(char suit) {
		return (SUITS.indexOf(Character.toLowerCase(suit)) != -1);
	}

	/**
	 * Return a card suit enum according to the char provided.
	 * 
	 * @param suit
	 *            a char as c, s, h or d.
	 * @return CardSuit enum value
	 * @throws InvalidCardException
	 *             when the char is not a valid suit
	 */
	static public CardSuit toCardSuit(char inSuit) throws InvalidCardException {
		char suit = inSuit;
		
		if (!isValidSuit(suit))
			throw new InvalidCardException("suit " + Character.toString(suit)
					+ " is invalid");

		suit = Character.toLowerCase(suit);
		for (CardSuit s : CardSuit.values()) {
			if (s.getSuit() == suit)
				return s;
		}

		// Should never be reached
		throw new InvalidCardException("Unreachable code");
	}

	/**
	 * Tests if the card is red.
	 * 
	 * @return true for red (hearts / diamonds), false otherwise
	 */
	public boolean isRed() {
		return (isHearts() || isDiamonds());
	}

	/**
	 * Tests if the card is black.
	 * 
	 * @return true for black (spaces / clubs), false otherwise
	 */
	public boolean isBlack() {
		return (isClubs() || isSpades());
	}

	/**
	 * Tests if the card suit is clubs.
	 * 
	 * @return true for clubs, false otherwise
	 */
	public boolean isClubs() {
		return (this == CardSuit.CLUBS);
	}

	/**
	 * Tests if the card suit is spades.
	 * 
	 * @return true for spades, false otherwise
	 */
	public boolean isSpades() {
		return (this == CardSuit.SPADES);
	}

	/**
	 * Tests if the card suit is diamonds.
	 * 
	 * @return true for diamonds, false otherwise
	 */
	public boolean isDiamonds() {
		return (this == CardSuit.DIAMONDS);
	}

	/**
	 * Tests if the card suit is hearts.
	 * 
	 * @return true for hearts, false otherwise
	 */
	public boolean isHearts() {
		return (this == CardSuit.HEARTS);
	}

	@Override
	public String toString() {
		if (this == CLUBS) {
			return "Clubs";
		}
		else if (this == SPADES) {
			return "Spades";
		}
		else if (this == HEARTS) {
			return "Hearts";
		}
		else if (this == DIAMONDS) {
			return "Diamonds";
		}
		else {
			return "";
		}
	}
}
