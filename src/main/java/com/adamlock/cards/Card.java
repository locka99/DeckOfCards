/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

/**
 * Immutable class represents a card with a suit and a value.
 * 
 * @author Adam Lock
 */
public enum Card implements Comparable<Card> {

	TWO_HEARTS(CardValue.TWO, CardSuit.HEARTS), THREE_HEARTS(CardValue.THREE,
			CardSuit.HEARTS), FOUR_HEARTS(CardValue.FOUR, CardSuit.HEARTS), FIVE_HEARTS(
			CardValue.FIVE, CardSuit.HEARTS), SIX_HEARTS(CardValue.SIX,
			CardSuit.HEARTS), SEVEN_HEARTS(CardValue.SEVEN, CardSuit.HEARTS), EIGHT_HEARTS(
			CardValue.EIGHT, CardSuit.HEARTS), NINE_HEARTS(CardValue.NINE,
			CardSuit.HEARTS), TEN_HEARTS(CardValue.TEN, CardSuit.HEARTS), JACK_HEARTS(
			CardValue.JACK, CardSuit.HEARTS), QUEEN_HEARTS(CardValue.QUEEN,
			CardSuit.HEARTS), KING_HEARTS(CardValue.KING, CardSuit.HEARTS), ACE_HEARTS(
			CardValue.ACE, CardSuit.HEARTS),

	TWO_DIAMONDS(CardValue.TWO, CardSuit.DIAMONDS), THREE_DIAMONDS(
			CardValue.THREE, CardSuit.DIAMONDS), FOUR_DIAMONDS(CardValue.FOUR,
			CardSuit.DIAMONDS), FIVE_DIAMONDS(CardValue.FIVE, CardSuit.DIAMONDS), SIX_DIAMONDS(
			CardValue.SIX, CardSuit.DIAMONDS), SEVEN_DIAMONDS(CardValue.SEVEN,
			CardSuit.DIAMONDS), EIGHT_DIAMONDS(CardValue.EIGHT,
			CardSuit.DIAMONDS), NINE_DIAMONDS(CardValue.NINE, CardSuit.DIAMONDS), TEN_DIAMONDS(
			CardValue.TEN, CardSuit.DIAMONDS), JACK_DIAMONDS(CardValue.JACK,
			CardSuit.DIAMONDS), QUEEN_DIAMONDS(CardValue.QUEEN,
			CardSuit.DIAMONDS), KING_DIAMONDS(CardValue.KING, CardSuit.DIAMONDS), ACE_DIAMONDS(
			CardValue.ACE, CardSuit.DIAMONDS),

	TWO_SPADES(CardValue.TWO, CardSuit.SPADES), THREE_SPADES(CardValue.THREE,
			CardSuit.SPADES), FOUR_SPADES(CardValue.FOUR, CardSuit.SPADES), FIVE_SPADES(
			CardValue.FIVE, CardSuit.SPADES), SIX_SPADES(CardValue.SIX,
			CardSuit.SPADES), SEVEN_SPADES(CardValue.SEVEN, CardSuit.SPADES), EIGHT_SPADES(
			CardValue.EIGHT, CardSuit.SPADES), NINE_SPADES(CardValue.NINE,
			CardSuit.SPADES), TEN_SPADES(CardValue.TEN, CardSuit.SPADES), JACK_SPADES(
			CardValue.JACK, CardSuit.SPADES), QUEEN_SPADES(CardValue.QUEEN,
			CardSuit.SPADES), KING_SPADES(CardValue.KING, CardSuit.SPADES), ACE_SPADES(
			CardValue.ACE, CardSuit.SPADES),

	TWO_CLUBS(CardValue.TWO, CardSuit.CLUBS), THREE_CLUBS(CardValue.THREE,
			CardSuit.CLUBS), FOUR_CLUBS(CardValue.FOUR, CardSuit.CLUBS), FIVE_CLUBS(
			CardValue.FIVE, CardSuit.CLUBS), SIX_CLUBS(CardValue.SIX,
			CardSuit.CLUBS), SEVEN_CLUBS(CardValue.SEVEN, CardSuit.CLUBS), EIGHT_CLUBS(
			CardValue.EIGHT, CardSuit.CLUBS), NINE_CLUBS(CardValue.NINE,
			CardSuit.CLUBS), TEN_CLUBS(CardValue.TEN, CardSuit.CLUBS), JACK_CLUBS(
			CardValue.JACK, CardSuit.CLUBS), QUEEN_CLUBS(CardValue.QUEEN,
			CardSuit.CLUBS), KING_CLUBS(CardValue.KING, CardSuit.CLUBS), ACE_CLUBS(
			CardValue.ACE, CardSuit.CLUBS);

	static private final Card[] HEARTS_CARDS = { TWO_HEARTS, THREE_HEARTS,
			FOUR_HEARTS, FIVE_HEARTS, SIX_HEARTS, SEVEN_HEARTS, EIGHT_HEARTS,
			NINE_HEARTS, TEN_HEARTS, JACK_HEARTS, QUEEN_HEARTS, KING_HEARTS,
			ACE_HEARTS };

	static private final Card[] DIAMONDS_CARDS = { TWO_DIAMONDS,
			THREE_DIAMONDS, FOUR_DIAMONDS, FIVE_DIAMONDS, SIX_DIAMONDS,
			SEVEN_DIAMONDS, EIGHT_DIAMONDS, NINE_DIAMONDS, TEN_DIAMONDS,
			JACK_DIAMONDS, QUEEN_DIAMONDS, KING_DIAMONDS, ACE_DIAMONDS };

	static private final Card[] CLUBS_CARDS = { TWO_CLUBS, THREE_CLUBS,
			FOUR_CLUBS, FIVE_CLUBS, SIX_CLUBS, SEVEN_CLUBS, EIGHT_CLUBS,
			NINE_CLUBS, TEN_CLUBS, JACK_CLUBS, QUEEN_CLUBS, KING_CLUBS,
			ACE_CLUBS };

	static private final Card[] SPADES_CARDS = { TWO_SPADES, THREE_SPADES,
			FOUR_SPADES, FIVE_SPADES, SIX_SPADES, SEVEN_SPADES, EIGHT_SPADES,
			NINE_SPADES, TEN_SPADES, JACK_SPADES, QUEEN_SPADES, KING_SPADES,
			ACE_SPADES };

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
	private Card(CardValue value, CardSuit suit) {
		this.value = value;
		this.suit = suit;
	}

	/**
	 * Get a card with the specified value and suit.
	 * 
	 * @param value
	 * @param suit
	 * @return
	 * @throws InvalidCardException
	 */
	public static final Card getCard(CardValue value, CardSuit suit)
			throws InvalidCardException {
		final Card cards[];
		switch (suit) {
		case HEARTS:
			cards = HEARTS_CARDS;
			break;
		case DIAMONDS:
			cards = DIAMONDS_CARDS;
			break;
		case CLUBS:
			cards = CLUBS_CARDS;
			break;
		case SPADES:
			cards = SPADES_CARDS;
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

	/**
	 * Parse a string and obtain the equivalent card
	 * 
	 * @param valueSuit
	 * @return
	 * @throws InvalidCardException
	 */
	public static Card fromString(String valueSuit) throws InvalidCardException {
		final char c1 = valueSuit.charAt(0);
		final char c2 = valueSuit.charAt(1);

		CardSuit suit;
		CardValue value;
		try {
			suit = CardSuit.toCardSuit(c1);
			value = CardValue.toCardValue(c2);
		} catch (InvalidCardException e) {
			// Try the other way around
			suit = CardSuit.toCardSuit(c2);
			value = CardValue.toCardValue(c1);
		}
		return getCard(value, suit);
	}

	/**
	 * @return Returns the suit.
	 */
	public CardSuit getSuit() {
		return suit;
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

	public static int compareCards(Card card1, Card card2) {
		int valueComp = CardValue.compareValues(card1.getValue(), card2.getValue());
		if (valueComp != 0)
			return valueComp;
		int suitComp = card2.getSuit().compareTo(card2.getSuit());
		if (suitComp != 0)
			return suitComp;
		return 0;
	}
	
	public static int compareValues(Card card1, Card card2) {
		return CardValue.compareValues(card1.getValue(), card2.getValue());
	}
}
