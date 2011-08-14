/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An immutable class that represents a card with a suit and a value. Cards are
 * immutable and the constructor is private to force users to use getCard to get
 * a card with a particular suit and value. Therefore there are exactly 52
 * instances of Card which can be compared by reference or by .equals.
 * 
 * @author Adam Lock
 */
public class Card implements Comparable<Card> {
	static private final Card heartsCards[];

	static private final Card diamondsCards[];

	static private final Card clubsCards[];

	static private final Card spadesCards[];

	static private final List<Card> allCards;

	static {
		final int valueCount = CardValue.values().length;

		heartsCards = new Card[valueCount];
		diamondsCards = new Card[valueCount];
		clubsCards = new Card[valueCount];
		spadesCards = new Card[valueCount];
		allCards = new ArrayList<Card>();

		try {
			int i = 0;
			for (CardValue v : CardValue.values()) {
				heartsCards[i] = new Card(v, CardSuit.HEARTS);
				diamondsCards[i] = new Card(v, CardSuit.DIAMONDS);
				clubsCards[i] = new Card(v, CardSuit.CLUBS);
				spadesCards[i] = new Card(v, CardSuit.SPADES);
				i++;
			}
			allCards.addAll(Arrays.asList(heartsCards));
			allCards.addAll(Arrays.asList(diamondsCards));
			allCards.addAll(Arrays.asList(clubsCards));
			allCards.addAll(Arrays.asList(spadesCards));
		} catch (Exception e) {

		}
	}

	public static List<Card> getAllCards() {
		return allCards;
	}

	public static final Card getCard(CardValue value, CardSuit suit)
			throws InvalidCardException {
		final Card cards[];
		switch (suit) {
		case HEARTS:
			cards = heartsCards;
			break;
		case DIAMONDS:
			cards = diamondsCards;
			break;
		case CLUBS:
			cards = clubsCards;
			break;
		case SPADES:
			cards = spadesCards;
			break;
		default:
			throw new InvalidCardException("Invalid suit");
		}

		for (Card c : cards) {
			if (c.getValue() == value) {
				return c;
			}
		}

		throw new InvalidCardException("Invalid value");
	}

	private CardSuit suit;

	private CardValue value;

	/**
	 * Constructor creates a card from the specified value / suit.
	 * 
	 * @param value
	 *            specified value
	 * @param suit
	 *            specified suit
	 * @throws InvalidCardException
	 *             if the value / suit is invalid
	 */
	private Card(CardValue value, CardSuit suit) throws InvalidCardException {
		this.value = value;
		this.suit = suit;
	}

	/**
	 * Constructor creates a card from the specified value / suit.
	 * 
	 * @param valueSuit
	 *            value / suit, although suit / value is acceptable
	 * @throws Exception
	 *             if the value / suit is invalid
	 */
	public Card(String valueSuit) throws Exception {
		final char c1 = valueSuit.charAt(0);
		final char c2 = valueSuit.charAt(1);

		// Try values, suits and then suits, values
		try {
			this.suit = CardSuit.toCardSuit(c1);
			this.value = CardValue.toCardValue(c2);
		} catch (InvalidCardException e) {
			// Try the other way around
			this.suit = CardSuit.toCardSuit(c2);
			this.value = CardValue.toCardValue(c1);
		}
	}

	public static Card fromString(String valueSuit) {
		try {
			return new Card(valueSuit);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Constructor creates a card from the supplied card
	 */
	public Card(Card card) {
		suit = card.getSuit();
		value = card.getValue();
	}

	/**
	 * @return Returns the suit.
	 */
	public CardSuit getSuit() {
		return suit;
	}

	/**
	 * Compare the value of this card to another
	 * 
	 * @param other
	 * @return 0 if values are the same, 1 if this card has a higher value,
	 *         otherwise -1
	 */
	public int compareValue(Card other) {
		return value.compare(other.getValue());
	}

	public boolean isGreaterValueByOne(Card other) {
		return value.isGreaterValueByOne(other.getValue());
	}

	/**
	 * @return Returns the card's value.
	 */
	public CardValue getValue() {
		return value;
	}

	/**
	 * Return the value as a string
	 * 
	 * @param plural
	 *            return the plural form of a value.
	 * @return
	 */
	public String getValueAsString(boolean usePlural) {
		return value.toString(usePlural);
	}

	/**
	 * Return the value of this card as a string
	 * 
	 * @return value as string
	 */
	public String getValueAsString() {
		return getValueAsString(false);
	}

	/**
	 * Return the suit of this card a string
	 * 
	 * @return suit as string
	 */
	public String getSuitAsString() {
		return suit.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toLongString() {
		return getValueAsString() + " of " + getSuitAsString();
	}

	@Override
	public String toString() {
		return toString(value, suit);
	}

	public static String toString(CardValue value, CardSuit suit) {
		return Character.toString(value.getValueChar())
				+ Character.toString(suit.getSuit());
	}

	/**
	 * Test if the card is the same suit as the specified card
	 * 
	 * @param card
	 *            card to compare to
	 * @return
	 */
	public boolean isSameSuit(Card card) {
		return (card.suit == suit);
	}

	/**
	 * Test if the card is the same suit as the specified suit.
	 * 
	 * @param suit
	 *            suit to compare to
	 * @return
	 */
	public boolean isSameSuit(CardSuit suit) {
		return (suit == this.suit);
	}

	public boolean isSameValue(char value) {
		return (value == this.value.getValueChar());
	}

	public boolean isSameValue(CardValue value) {
		return (value == this.value);
	}

	public boolean isValueOneHigher(char value) {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Card other = (Card) obj;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/**
	 * Compare this card to another to determine which is higher. For comparison
	 * purposes we use the value and the suit, to ensure consistent ordering
	 * 
	 * @param other
	 *            card
	 * @return -1 if less than the other card, 0 if equal and 1 otherwise
	 */
	@Override
	public int compareTo(Card c) {
		int valueComp = value.compare(c.value);
		if (valueComp != 0)
			return valueComp;
		int suitComp = suit.compareTo(c.suit);
		if (suitComp != 0)
			return suitComp;
		return 0;
	}
}
