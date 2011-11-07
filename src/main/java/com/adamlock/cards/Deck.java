/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Lock
 */
public class Deck implements Cloneable {

	private static final HashMap<Card, Integer> reverseCardLookup;

	static {
		reverseCardLookup = new HashMap<Card, Integer>();
		final Card[] allCards = Card.values();
		for (int i = 0; i < allCards.length; i++) {
			reverseCardLookup.put(allCards[i], i);
		}
	}

	/** The deck is 52 indices onto the 52 possible card combinations. */
	private final Card allCards[] = Card.values();

	private int deck[] = new int[52];
	private int startOfDrawn = deck.length;

	private static final Comparator<ShuffleInfo> shuffleComparator = new Comparator<ShuffleInfo>() {
		@Override
		public int compare(ShuffleInfo o1, ShuffleInfo o2) {
			// Walk the array of bytes until one is deemed to be
			// larger than the other
			final int length = o1.getOrder().length;
			final byte[] b1 = o1.getOrder();
			final byte[] b2 = o2.getOrder();
			for (int i = 0; i < length; i++) {
				if (b1[i] < b2[i])
					return -1;
				else if (b1[i] > b2[i])
					return 1;
			}
			return 0;
		}
	};

	public Deck() {
		try {
			createDeck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Deck(boolean shuffle) {
		this();
		if (shuffle) {
			shuffle();
		}
	}

	/**
	 * Create a fresh sorted deck of 52 cards.
	 */
	private void createDeck() {
		for (int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}
		startOfDrawn = deck.length;
	}

	/**
	 * Resets the deck, ie puts all the drawn cards back in the deck. Note the
	 * deck may still be shuffled
	 */
	public void reset() {
		// Put drawn marker to end
		startOfDrawn = deck.length;
	}

	/**
	 * Randomly shuffles the undrawn cards
	 */
	public void shuffle() {

		final int undrawnSize = startOfDrawn;

		final ArrayList<ShuffleInfo> shuffleList = new ArrayList<ShuffleInfo>(
				undrawnSize);

		// For every card in the deck, create a shuffle info consisting of a
		// random number and the card.
		for (int i = 0; i < undrawnSize; i++) {
			shuffleList.add(new ShuffleInfo(deck[i]));
		}

		// Sort by the random number
		Collections.sort(shuffleList, shuffleComparator);

		// Now create the deck again in the new order
		for (int i = 0; i < undrawnSize; i++) {
			deck[i] = shuffleList.get(i).getCardIndex();
		}
	}

	int[] getCardIndices(Card[] cards) {
		int cardIndices[] = new int[cards.length];
		for (int i = 0; i < cards.length; i++) {
			cardIndices[i] = reverseCardLookup.get(cards[i]);
		}
		return cardIndices;
	}

	/**
	 * Test if the deck is empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return startOfDrawn == 0;
	}

	/**
	 * Return the number of cards in the deck.
	 * 
	 * @return cards remaining
	 */
	public int size() {
		return startOfDrawn;
	}

	/**
	 * Deal cards from the deck
	 * 
	 * @param numCards
	 *            number of cards to deal
	 * @return array of dealt cards
	 * @throws EmptyDeckException
	 */
	public Card[] deal(int numCards) throws EmptyDeckException {
		final Card[] result = new Card[numCards];
		if (numCards < 1) {
			throw new IndexOutOfBoundsException();
		}

		if (startOfDrawn < numCards) {
			throw new EmptyDeckException();
		}
		for (int i = 0; i < numCards; ++i) {
			result[i] = allCards[deck[startOfDrawn - i - 1]];
		}
		startOfDrawn -= numCards;

		return result;
	}

	/**
	 * Validate all the cards
	 */
	void internalValidate() {
		final Set<Card> found = new HashSet<Card>();
		for (int i = 0; i < deck.length; i++) {
			Card c = allCards[deck[i]];
			if (!found.add(allCards[deck[i]])) {
				System.out.println("Duplicate of card " + c + " in the deck!");
				throw new RuntimeException("Duplicate card");
			}
		}
	}

	/**
	 * Deal one card from the deck
	 * 
	 * @return dealt card
	 * @throws EmptyDeckException
	 */
	public Card dealOne() throws EmptyDeckException {
		// Remove the first card from the deck
		if (startOfDrawn == 0) {
			throw new EmptyDeckException();
		}
		final Card c = allCards[deck[startOfDrawn - 1]];
		startOfDrawn--;
		return c;
	}

	/**
	 * Remove an array of cards from the deck
	 * 
	 * @param cards
	 */
	public int removeCard(Card[] cards) {
		if (cards == null) {
			throw new IllegalArgumentException("Must supply cards");
		}

		// Produce indices for input cards
		int cardIndices[] = getCardIndices(cards);

		// Build a new deck
		int newDeck[] = new int[deck.length];
		int newDeckIdx = 0;
		int cardsRemoved[] = new int[cards.length];
		int cardsRemovedCount = 0;
		outer: for (int i = 0; i < startOfDrawn; i++) {
			for (int card = 0; card < cardIndices.length; card++) {
				if (deck[i] == cardIndices[card]) {
					// Card was found
					cardsRemoved[cardsRemovedCount++] = cardIndices[card];
					continue outer;
				}
			}
			newDeck[newDeckIdx++] = deck[i];
		}
		if (cardsRemovedCount == 0) {
			return 0;
		}

		final int newStartOfDrawn = newDeckIdx;
		for (int i = 0; i < cardsRemovedCount; i++) {
			newDeck[newStartOfDrawn + i] = cardsRemoved[i];
		}
		for (int i = 0; i < newDeck.length - startOfDrawn; i++) {
			newDeck[newStartOfDrawn + cardsRemovedCount + i] = deck[startOfDrawn
					+ i];
		}
		deck = newDeck;
		startOfDrawn = newStartOfDrawn;

		return cardsRemovedCount;
	}

	/**
	 * Remove a card from the deck
	 * 
	 * @param card
	 */
	public boolean removeCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Must supply a card");
		}

		final int cardIndex = reverseCardLookup.get(card);

		// Look for the card in the undrawn pile
		int foundIndex = -1;
		for (int i = 0; i < startOfDrawn; i++) {
			if (deck[i] == cardIndex) {
				foundIndex = i;
				break;
			}
		}
		if (foundIndex == -1) {
			return false;
		}

		// Move everything left over by 1 and put card on end
		startOfDrawn--;
		for (int i = foundIndex; i < deck.length - 1; i++) {
			deck[i] = deck[i + 1];
		}
		deck[deck.length - 1] = cardIndex;

		return true;
	}

	/**
	 * Add an array of cards to the deck
	 * 
	 * @param cards
	 */
	public int replaceCard(Card[] cards) {
		if (cards == null) {
			throw new IllegalArgumentException("Must supply cards");
		}
		if (startOfDrawn == deck.length) {
			return 0;
		}

		final int cardIndices[] = getCardIndices(cards);

		// Build a new deck
		final int newDeck[] = new int[deck.length];
		int newDeckIdx = deck.length - 1;
		final int cardsReplaced[] = new int[cards.length];
		int cardsReplacedCount = 0;

		outer: for (int i = deck.length - 1; i >= startOfDrawn; i--) {
			for (int card = 0; card < cardIndices.length; card++) {
				if (deck[i] == cardIndices[card]) {
					// Card was found
					cardsReplaced[cardsReplacedCount++] = cardIndices[card];
					continue outer;
				}
			}
			newDeck[newDeckIdx--] = deck[i];
		}
		if (cardsReplacedCount == 0) {
			return 0;
		}

		final int newStartOfDrawn = startOfDrawn + cardsReplacedCount;
		for (int i = 0; i < cardsReplacedCount; i++) {
			newDeck[i] = cardsReplaced[i];
		}
		for (int i = cardsReplacedCount; i < newStartOfDrawn; i++) {
			newDeck[i] = deck[i - cardsReplacedCount];
		}
		deck = newDeck;
		startOfDrawn = newStartOfDrawn;
		return cardsReplacedCount;
	}

	/**
	 * Replaces a card from the drawn pile back onto the end of the deck.
	 * 
	 * @param card
	 *            card to be added
	 */
	public boolean replaceCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Must supply a card");
		}
		if (startOfDrawn == deck.length) {
			return false;
		}

		// Look for a card in the drawn pile
		final int cardIndex = reverseCardLookup.get(card);
		int foundIndex = -1;
		for (int i = startOfDrawn; i < deck.length; i++) {
			if (deck[i] == cardIndex) {
				foundIndex = i;
				break;
			}
		}
		if (foundIndex == -1) {
			return false;
		}

		// We found it so move everything to the right by one so it can be put
		// at 0
		startOfDrawn++;
		for (int i = foundIndex; i > 0; i--) {
			deck[i] = deck[i - 1];
		}
		deck[0] = cardIndex;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < startOfDrawn; i++) {
			sb.append(allCards[deck[i]].toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		final Deck newDeck = new Deck();
		newDeck.deck = deck.clone();
		newDeck.startOfDrawn = startOfDrawn;
		return newDeck;
	}
}
