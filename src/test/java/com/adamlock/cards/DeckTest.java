/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.adamlock.cards.Card;
import com.adamlock.cards.DeckImpl;
import com.adamlock.cards.EmptyDeckException;
import junit.framework.TestCase;

/**
 * Tests for Deck class
 */
public class DeckTest extends TestCase {
	
	private DeckImpl deck;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		deck = new DeckImpl();
	}

	private void validateDeck(DeckImpl d) {
		Set<Card> found = new HashSet<Card>();

		int cardsInDeck = d.size();
		Card c;
		try {
			c = d.dealOne();
			while (c != null) {
				TestCase.assertFalse(found.contains(c));
				found.add(c);
				c = d.dealOne();
			}
		} catch (EmptyDeckException e) {
			// Drop through
		}
		TestCase.assertEquals(cardsInDeck, found.size());
	}

	/**
	 * Basic sanity test
	 */
	public void testBasicDeck() {
		for (int i = 0; i < 2; i++) {
			TestCase.assertEquals(52, deck.size());
			try {
				Card[] cards = deck.deal(52);
				deck.internalValidate();
				deck.replaceCard(cards);
				deck.internalValidate();
			} catch (EmptyDeckException e) {
				TestCase.fail("deck is empty when it shouldn't be!");
			}
		}
	}

	/**
	 * Class under test for Card dealOne(boolean)
	 */
	public void testDealOneboolean() {
		try {
			ArrayList<Card> v = new ArrayList<Card>();
			for (int i = 0; i < 52; i++) {
				Card c = deck.dealOne();
				v.add(c);
			}
		} catch (EmptyDeckException e) {
			TestCase.fail("deck is empty when it shouldn't be!");
		}
		if (!deck.isEmpty()) {
			TestCase.fail("deck should be empty but it isn't!");
		}
	}

	public void testDeal() {
		int cards = 0;
		try {
			while (true) {
				deck.dealOne();
				cards++;
			}
		} catch (EmptyDeckException e) {
			if (cards != 52)
				TestCase.fail("deck only dealt " + Integer.toString(cards)
						+ " cards");
		}
	}

	public void testRemoveSingle() {
		deck.shuffle();
		TestCase.assertEquals(deck.removeCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();
		TestCase.assertEquals(deck.removeCard(Card.FOUR_HEARTS), false);
		deck.internalValidate();
		TestCase.assertEquals(deck.replaceCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();
		TestCase.assertEquals(deck.replaceCard(Card.FOUR_HEARTS), false);
		deck.internalValidate();
		TestCase.assertEquals(deck.removeCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();
		TestCase.assertEquals(deck.removeCard(Card.FOUR_HEARTS), false);
		TestCase.assertEquals(deck.replaceCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();

		validateDeck(deck);
	}

	public void testShuffleReplace() {
		// Test remove / replace function even after deck is shuffled between
		// deal & replace
		for (int i = 0; i < 10000; i++) {
			deck.shuffle();
			try {
				Card cards[] = deck.deal(5);
				TestCase.assertEquals(deck.size(), 47);
				deck.shuffle();
				TestCase.assertEquals(deck.removeCard(cards), 0);
				TestCase.assertEquals(deck.replaceCard(cards), 5);
				deck.internalValidate();
			} catch (EmptyDeckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void testRemoveMultiple() {
		deck.shuffle();
		TestCase.assertEquals(
				deck.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				2);
		deck.internalValidate();
		TestCase.assertEquals(deck.replaceCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();
		TestCase.assertEquals(
				deck.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				1);
		deck.internalValidate();
		TestCase.assertEquals(
				deck.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				0);
		deck.internalValidate();
		TestCase.assertEquals(deck.replaceCard(new Card[] { Card.FOUR_HEARTS,
				Card.THREE_CLUBS }), 2);
		deck.internalValidate();
		TestCase.assertEquals(deck.removeCard(Card.FOUR_HEARTS), true);
		deck.internalValidate();
		TestCase.assertEquals(deck.replaceCard(new Card[] { Card.FOUR_HEARTS,
				Card.THREE_CLUBS }), 1);
		deck.internalValidate();
		TestCase.assertEquals(
				deck.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				2);
		deck.internalValidate();

		validateDeck(deck);
	}

	public void testShuffle() {
		deck.shuffle();
		try {
			Card[] cards = deck.deal(52);
			Card[] cardsSorted = Card.values();
			int unsortedCount = 0;
			for (int i = 0; i < cards.length; i++) {
				if (cardsSorted[cards.length - 1 - i] == cards[i]) {
					unsortedCount++;
				} else {
					unsortedCount = 0;
				}
				if (unsortedCount >= 10) {
					TestCase.fail("Deck is probably not shuffled properly");
				}
			}
			deck.replaceCard(cards);
		} catch (EmptyDeckException e) {
			TestCase.fail("Something went wrong");
		}

		validateDeck(deck);
	}

	public void testSpeed() {
		long startTime = System.currentTimeMillis();
		int gamesSimulated = 0;
		while (System.currentTimeMillis() - startTime < 3000) {
			DeckImpl d = new DeckImpl();
			d.shuffle();
			try {
				d.replaceCard(d.deal(20));
			} catch (EmptyDeckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gamesSimulated++;
			validateDeck(d);
		}

		System.out.println("Deck test time elapsed, games played = "
				+ gamesSimulated);
	}

	public void testDealMany() {
		deck.shuffle();
		TestCase.assertEquals(deck.size(), 52);
		Set<Card> dealtCards = new HashSet<Card>();

		try {
			for (int i = 0; i < 5; i++) {
				Card[] cards = deck.deal(10);
				for (Card c : cards) {
					TestCase.assertNotNull(c);
					TestCase.assertFalse(dealtCards.contains(c));
					dealtCards.add(c);
				}
			}
		} catch (EmptyDeckException e) {
			TestCase.fail("Deck should not be empty #1");
		}

		try {
			deck.deal(3);
			TestCase.fail("Deck should not be able to deal 3 cards");
		} catch (EmptyDeckException e) {

		}

		try {
			deck.deal(2);
		} catch (EmptyDeckException e) {
			TestCase.fail("Deck should not be empty #2");
		}

		try {
			deck.dealOne();
			TestCase.fail("Deck should not be able to deal a card");
		} catch (EmptyDeckException e) {

		}
	}
}
