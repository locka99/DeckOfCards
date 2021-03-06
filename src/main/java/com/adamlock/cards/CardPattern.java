package com.adamlock.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A card pattern is something which represents one or more cards. Like a wild
 * card.
 * 
 * @author Adam
 * 
 */
public class CardPattern {
	public enum Type {
		/** Any random card at all */
		RANDOM,
		/** Any suit with a particular value */
		ANY_SUIT,
		/** Any value with a particular suit */
		ANY_VALUE,
		/** Part of a set */
		CONTAINED_BY_SET,
		/** An exact card */
		EXACT
	}

	/**
	 * Make a pattern from a card, i.e. pattern matches just that card
	 * 
	 * @param card
	 * @return a new pattern
	 */
	public static CardPattern fromCard(Card card) {
		return new CardPattern(card);
	}

	/**
	 * Make a pattern from a suit and value, same as a card basically.
	 * 
	 * @param value
	 * @param suit
	 * @return
	 */
	public static CardPattern fromCard(CardValue value, CardSuit suit) {
		return new CardPattern(value, suit);
	}

	/**
	 * Make a pattern that matches any value card in a suit
	 * 
	 * @param suit
	 * @return
	 */
	public static CardPattern fromSuit(CardSuit suit) {
		return new CardPattern(suit);
	}

	/**
	 * Make a pattern than matches any suited card with a value.
	 * 
	 * @param value
	 * @return
	 */
	public static CardPattern fromValue(CardValue value) {
		return new CardPattern(value);
	}

	public static CardPattern[] fromCards(Card[] cards) {

		CardPattern cardPatterns[] = new CardPattern[cards.length];
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] != null) {
				cardPatterns[i] = fromCard(cards[i]);
			}
		}
		return cardPatterns;
	}

	/**
	 * Make a pattern is one or more other patterns
	 * 
	 * @param cards
	 * @return
	 */
	public static CardPattern setPatternfromCards(Card[] cards) {
		CardPattern cardPatterns[] = new CardPattern[cards.length];
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] != null) {
				cardPatterns[i] = fromCard(cards[i]);
			}
		}
		final Set<CardPattern> set = new HashSet<CardPattern>();
		set.addAll(Arrays.asList(cardPatterns));
		return new CardPattern(set);
	}

	/** A pattern that matches anything */
	public static CardPattern RANDOM = new CardPattern();

	private final Type type;

	private final CardValue value;

	private final CardSuit suit;

	private final Set<CardPattern> cardPatterns;

	private CardPattern() {
		this(CardValue.RANDOM, CardSuit.RANDOM);
	}

	private CardPattern(Card card) {
		this(card != null ? card.getValue() : null,
				card != null ? card.getSuit() : null);
	}

	private CardPattern(CardValue value, CardSuit suit) {
		if (value == CardValue.RANDOM && suit == CardSuit.RANDOM) {
			this.type = Type.RANDOM;
		} else if (value == CardValue.RANDOM && suit != null) {
			this.type = Type.ANY_VALUE;
		} else if (value != null && suit == CardSuit.RANDOM) {
			this.type = Type.ANY_SUIT;
		} else if (value != null && suit != null) {
			this.type = Type.EXACT;
		} else {
			throw new IllegalArgumentException(
					"Suit or value were null and should not be");
		}
		this.suit = suit;
		this.value = value;
		this.cardPatterns = null;
	}

	private CardPattern(CardSuit suit) {
		this(CardValue.RANDOM, suit);
	}

	private CardPattern(CardValue value) {
		this(value, CardSuit.RANDOM);
	}

	private CardPattern(Set<CardPattern> cardPatterns) {
		this(Type.CONTAINED_BY_SET, null, null, cardPatterns);
	}

	private CardPattern(Type type, CardValue value, CardSuit suit,
			Set<CardPattern> cardPatterns) {
		this.type = type;
		this.suit = suit;
		this.value = value;
		this.cardPatterns = cardPatterns;
	}

	public Type getType() {
		return type;
	}

	public CardValue getValue() {
		return value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public Set<CardPattern> getCardPatterns() {
		return cardPatterns;
	}

	/**
	 * Test if a card matches this pattern.
	 * 
	 * @param card
	 * @return
	 */
	public boolean matches(Card card) {
		if (card == null) {
			return false;
		}
		switch (type) {
		case ANY_SUIT:
			return card.getValue() == value;
		case ANY_VALUE:
			return card.getSuit() == suit;
		case RANDOM:
			return true;
		case CONTAINED_BY_SET:
			for (CardPattern pattern : cardPatterns) {
				if (pattern.matches(card)) {
					return true;
				}
			}
			return false;
		case EXACT:
			return card.getValue() == value && card.getSuit() == suit;
		}
		return false;
	}

	public boolean isExact() {
		return type == Type.EXACT;
	}

	public boolean isAnySuit() {
		return type == Type.ANY_SUIT;
	}

	public boolean isAnyValue() {
		return type == Type.ANY_VALUE;
	}

	public boolean isContainedBySet() {
		return type == Type.CONTAINED_BY_SET;
	}

	public boolean isRandom() {
		return type == Type.RANDOM;
	}

	public Card getCard() {
		if (suit != null && suit != CardSuit.RANDOM && value != null
				&& value != CardValue.RANDOM) {
			try {
				return Card.getCard(value, suit);
			} catch (InvalidCardException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean isValid() {
		switch (type) {
		case EXACT:
			return (suit != null && value != null);
		case ANY_SUIT:
			return (value != null);
		case ANY_VALUE:
			return (suit != null);
		case CONTAINED_BY_SET:
			return (cardPatterns != null & cardPatterns.size() > 0);
		}
		return true;
	}

	@Override
	public String toString() {
		switch (type) {
		case EXACT:
			try {
				return Card.getCard(value, suit).toLongString();
			} catch (InvalidCardException e) {
				e.printStackTrace();
				break;
			}
		case ANY_SUIT:
			return value.toString() + " of ???";
		case ANY_VALUE:
			return "??? of " + suit.toString();
		case CONTAINED_BY_SET:
			return "<multiple>";
		case RANDOM:
			return "??? of ???";
		}
		return "";
	}

}
