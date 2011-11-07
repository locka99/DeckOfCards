package com.adamlock.cards;

import java.util.Set;

/**
 * A card pattern represents one or more cards. Like a wild card or similar
 * 
 * @author Adam
 * 
 */
public class CardPattern {
	enum Type {
		/** Any random card at all */
		RANDOM,
		/** Any suit with a particular value */
		ANYSUIT,
		/** Any value with a particular suit */
		ANYVALUE,
		/** Part of a set */
		CONTAINED_BY_SET
	}

	private final Type type;

	private final CardValue value;

	private final CardSuit suit;

	private final Set<Card> cards;

	public CardPattern() {
		this(Type.RANDOM, null, null, null);
	}

	public CardPattern(CardSuit suit) {
		this(Type.ANYSUIT, suit, null, null);
	}

	public CardPattern(CardValue value) {
		this(Type.ANYVALUE, null, value, null);
	}

	public CardPattern(Type type, Set<Card> cards) {
		this(Type.CONTAINED_BY_SET, null, null, cards);
	}

	private CardPattern(Type type, CardSuit suit, CardValue value,
			Set<Card> cards) {
		this.type = type;
		this.suit = suit;
		this.value = value;
		this.cards = cards;
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

	public Set<Card> getCards() {
		return cards;
	}
	
	public boolean cardMatchesPattern(Card card) {
		switch (type) {
		case ANYSUIT:
			return card.getValue() == value;
		case ANYVALUE:
			return card.getSuit() == suit;
		case RANDOM:
			return true;
		case CONTAINED_BY_SET:
			return cards.contains(card);
		}
		return false;
	}
}
