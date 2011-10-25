/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Adam Lock
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Deck implements Cloneable {
	/**
	 * The deck is a linked list for easy slicing and dicing.
	 */
	protected LinkedList<Card> deck = new LinkedList<Card>();

	/**
	 * Drawn pile allows previously drawn cards to be inspected
	 */
	private LinkedList<Card> drawn = new LinkedList<Card>();

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
		deck.clear();
		deck.addAll(Arrays.asList(Card.values()));
		drawn.clear();
	}

	/**
	 * Resets the deck, ie puts all the drawn cards back in the deck. Note the
	 * deck may still be shuffled
	 */
	public void reset() {
		deck.addAll(drawn);
		drawn.clear();
	}

	public void shuffle() {
		final ArrayList<ShuffleInfo> shuffleList = new ArrayList<ShuffleInfo>(
				deck.size());

		// For every card in the deck, create a shuffle info consisting of a
		// random number and the card.
		for (Card c : deck) {
			shuffleList.add(new ShuffleInfo(c));
		}

		// Sort by the random number
		Collections.sort(shuffleList, shuffleComparator);

		// Now create the deck again in the new order
		deck.clear();
		for (ShuffleInfo si : shuffleList) {
			deck.add(si.getCard());
		}
	}

	/**
	 * Remove an array of cards from the deck
	 * 
	 * @param cards
	 */
	public void removeCard(Card[] cards) {
		if (cards == null) {
			throw new IllegalArgumentException("Must supply cards");
		}
		for (Iterator<Card> i = deck.iterator(); i.hasNext();) {
			final Card c = i.next();
			for (Card c2 : cards) {
				if (c == c2) {
					i.remove();
					drawn.add(c);
					continue;
				}
			}
		}
	}

	/**
	 * Remove a card from the deck
	 * 
	 * @param card
	 */
	public void removeCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Must supply a card");
		}
		for (Iterator<Card> i = deck.iterator(); i.hasNext();) {
			final Card c = i.next();
			if (c == card) {
				i.remove();
				drawn.add(c);
				return;
			}
		}
	}

	/**
	 * Test if the deck is empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}

	/**
	 * Return the number of cards in the deck.
	 * 
	 * @return cards remaining
	 */
	public int size() {
		return deck.size();
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
		return deal(numCards, true);
	}

	/**
	 * Deal cards from the deck and optionally put them back at the end of the
	 * deck
	 * 
	 * @param numCards
	 *            number of cards to deal
	 * @param removeCards
	 *            true to remove the cards, false to put them back in the dec
	 * @return array of dealt cards
	 * @throws EmptyDeckException
	 */
	public Card[] deal(int numCards, boolean removeCards)
			throws EmptyDeckException {
		Card[] result = new Card[numCards];

		if (numCards < 1)
			throw new IndexOutOfBoundsException();

		if (removeCards && deck.size() < numCards) {
			throw new EmptyDeckException();
		}

		for (int i = 0; i < numCards; ++i) {
			final Card c = deck.removeFirst();
			result[i] = c;
			if (!removeCards) {
				deck.add(c);
			} else {
				drawn.add(c);
			}
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
		return dealOne(true);
	}

	/**
	 * Deal one card from the deck and optionally put it back
	 * 
	 * @param removeIt
	 *            true to remove the card, false to put it to the bottom
	 * @return
	 * @throws EmptyDeckException
	 */
	public Card dealOne(boolean removeIt) throws EmptyDeckException {
		// Remove the first card from the deck
		if (deck.isEmpty()) {
			throw new EmptyDeckException();
		}

		final Card c = deck.removeFirst();
		if (!removeIt) {
			// And put it to the back of the deck.
			// This keeps the deck a consistent size but is obviously bad if
			// more cards are dealt than are in the pack.
			deck.add(c);
		} else {
			drawn.add(c);
		}
		return c;
	}

	/**
	 * Add an array of cards to the deck
	 * 
	 * @param cards
	 */
	public void replaceCard(Card[] cards) {
		if (cards == null) {
			throw new IllegalArgumentException("Must supply cards");
		}
		for (Card c : cards) {
			replaceCard(c);
		}
	}

	/**
	 * Replaces a card from the drawn pile back onto the end of the deck.
	 * 
	 * @param card
	 *            card to be added
	 */
	public void replaceCard(Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Must supply a card");
		}
		for (Iterator<Card> i = drawn.iterator(); i.hasNext();) {
			if (i.next() == card) {
				i.remove();
				deck.add(card);
				return;
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
		for (final Card c : deck) {
			sb.append(c.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		final Deck newDeck = new Deck();
		newDeck.deck = (LinkedList<Card>) deck.clone();
		newDeck.drawn = (LinkedList<Card>) drawn.clone();
		return newDeck;
	}
}
