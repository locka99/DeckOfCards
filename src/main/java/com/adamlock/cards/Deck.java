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
 * Represents a standard deck of 52 unique cards. Cards are undrawn or drawn,
 * and can be replaced / removed between drawn and undrawn piles.
 * 
 * @author Adam Lock
 */
public class Deck implements Cloneable {

	/** The deck is 52 indices onto the 52 possible card combinations. */
	private static final Card allCards[] = Card.values();

	/** Reverse lookup turns a card into an index */
	private static final HashMap<Card, Integer> reverseCardLookup;

	static {
		// Make the reverse card lookup
		reverseCardLookup = new HashMap<Card, Integer>();
		final Card[] allCards = Card.values();
		for (int i = 0; i < allCards.length; i++) {
			reverseCardLookup.put(allCards[i], i);
		}
	}

	/**
	 * Represents the entire deck of 52 cards
	 */
	private int deck[] = new int[allCards.length];
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

	/**
	 * Constructor
	 */
	public Deck() {
		try {
			createDeck();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor which optionally shuffles the deck
	 * 
	 * @param shuffle
	 */
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

	/**
	 * Get the indices for each of the requested cards
	 * 
	 * @param cards
	 * @return
	 */
	private int[] getCardIndices(Card[] cards) {
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

	public Card[] deal(CardPattern pattern, int numCards)
			throws EmptyDeckException {
		if (pattern.isRandom()) {
			return deal(numCards);
		}
		final Card[] cards = new Card[numCards];
		for (int i = 0; i < numCards; i++) {
			cards[i] = dealOne(pattern);
		}
		return cards;
	}

	/**
	 * Deal a card which matches the specified pattern
	 * 
	 * @param pattern
	 * @return a card matching the pattern or null if no match was possible
	 */
	public Card dealOne(CardPattern pattern) throws EmptyDeckException {
		if (pattern.isRandom()) {
			return dealOne();
		}
		for (int i = startOfDrawn - 1; i >= 0; i--) {
			final Card card = allCards[deck[i]];
			if (pattern.matches(card)) {
				removeCard(card);
				return card;
			}
		}
		return null;
	}

	/**
	 * Deal cards which match the specified patterns. Note the resulting array
	 * could contain nulls if no match is possible.
	 * 
	 * @param patterns
	 * @return
	 */
	public Card[] deal(CardPattern patterns[]) throws EmptyDeckException {
		final Card[] result = new Card[patterns.length];

		int patternIdx = 0;
		for (CardPattern pattern : patterns) {
			if (pattern != null) {
				for (int i = startOfDrawn - 1; i >= 0; i--) {
					final Card card = allCards[deck[i]];
					if (pattern.matches(card)) {
						removeCard(card);
						result[patternIdx] = card;
						break;
					}
				}
			}
			patternIdx++;
		}
		return result;
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
	 * Draw a card randomly from somewhere out of the undrawn deck.
	 * 
	 * @return
	 * @throws EmptyDeckException
	 */
	public Card dealRandom() throws EmptyDeckException {
		if (startOfDrawn == 0) {
			throw new EmptyDeckException();
		}
		final int randomIdx = ShuffleInfo.RANDOM.nextInt(startOfDrawn);
		final Card card = allCards[deck[randomIdx]];
		removeCardAt(randomIdx);
		return card;
	}

	/**
	 * Remove a card from the undrawn pile in the deck. Card is placed at end of
	 * drawn pile. If the card is undrawn it will not be moved.
	 * 
	 * @param card
	 * @return true if card was returned, false if it didn't need to be
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
		removeCardAt(foundIndex);
		return true;
	}

	/**
	 * Remove a card at the specified position from the undrawn to the drawn
	 * pile
	 * 
	 * @param position
	 *            position to remove card from.
	 */
	private void removeCardAt(int position) {
		final int cardIndex = deck[position];
		startOfDrawn--;
		for (int i = position; i < deck.length - 1; i++) {
			deck[i] = deck[i + 1];
		}
		deck[deck.length - 1] = cardIndex;
	}

	/**
	 * Remove an array of cards from the undrawn pile in the deck and put them
	 * at the end of the drawn pile.
	 * 
	 * @param cards
	 * @return the number of cards actually removed
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
	 * Replaces a card from the drawn pile back onto the end of the undrawn
	 * pile..
	 * 
	 * @param card
	 *            card to be added
	 * @return true if the card was returned to the undrawn pile, false if it
	 *         wasn't
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

	/**
	 * Replaces cards from the drawn pile back to the undrawn pile. The go at
	 * the end of the drawn pile. Cards which are not in the drawn pile will not
	 * be moved.
	 * 
	 * @param cards
	 * @return the number of cards returned to the undrawn pile.
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		final Deck newDeck = new Deck();
		newDeck.deck = deck.clone();
		newDeck.startOfDrawn = startOfDrawn;
		return newDeck;
	}
}
