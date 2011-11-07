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
import com.adamlock.cards.Deck;
import com.adamlock.cards.EmptyDeckException;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests for Deck class
 */
public class DeckTest extends TestCase {

	private void validateDeck(Deck d) {
		Set<Card> found = new HashSet<Card>();

		int cardsInDeck = d.size();
		Card c;
		try {
			c = d.dealOne();
			while (c != null) {
				Assert.assertFalse(found.contains(c));
				found.add(c);
				c = d.dealOne();
			}
		} catch (EmptyDeckException e) {
			// Drop through
		}
		Assert.assertEquals(cardsInDeck, found.size());
	}

	/*
	 * Class under test for Card dealOne(boolean)
	 */
	public void testDealOneboolean() {
		Deck deck = new Deck();
		if (deck.size() != 52)
			Assert.fail("deck should have 52 cards and it doesn't!");
		try {
			ArrayList<Card> v = new ArrayList<Card>();
			for (int i = 0; i < 52; i++) {
				Card c = deck.dealOne();
				v.add(c);
			}
		} catch (EmptyDeckException e) {
			Assert.fail("deck is empty when it shouldn't be!");
		}
		if (!deck.isEmpty()) {
			Assert.fail("deck should be empty but it isn't!");
		}
	}

	public void testDeal() {
		int cards = 0;
		Deck d = new Deck();
		try {
			while (true) {
				d.dealOne();
				cards++;
			}
		} catch (EmptyDeckException e) {
			if (cards != 52)
				Assert.fail("deck only dealt " + Integer.toString(cards)
						+ " cards");
		}
	}

	public void testRemoveSingle() {
		Deck d = new Deck();
		d.shuffle();
		Assert.assertEquals(d.removeCard(Card.FOUR_HEARTS), true);
		d.internalValidate();
		Assert.assertEquals(d.removeCard(Card.FOUR_HEARTS), false);
		d.internalValidate();
		Assert.assertEquals(d.replaceCard(Card.FOUR_HEARTS), true);
		d.internalValidate();
		Assert.assertEquals(d.replaceCard(Card.FOUR_HEARTS), false);
		d.internalValidate();
		Assert.assertEquals(d.removeCard(Card.FOUR_HEARTS), true);
		d.internalValidate();
		Assert.assertEquals(d.removeCard(Card.FOUR_HEARTS), false);

		Assert.assertEquals(d.replaceCard(Card.FOUR_HEARTS), true);
		d.internalValidate();

		validateDeck(d);
	}
	
	public void testShuffleReplace() {
		Deck d = new Deck();
		// Test remove / replace function even after deck is shuffled between deal & replace
		for (int i = 0; i < 10000; i++) {
			d.shuffle();
			try {
				Card cards[] = d.deal(5);
				Assert.assertEquals(d.size(), 47);
				d.shuffle();
				Assert.assertEquals(d.removeCard(cards), 0);
				Assert.assertEquals(d.replaceCard(cards), 5);
				d.internalValidate();
			} catch (EmptyDeckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void testRemoveMultiple() {
		Deck d = new Deck();
		d.shuffle();
		Assert.assertEquals(
				d.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				2);
		d.internalValidate();
		Assert.assertEquals(d.replaceCard(Card.FOUR_HEARTS), true);
		d.internalValidate();
		Assert.assertEquals(
				d.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				1);
		d.internalValidate();
		Assert.assertEquals(
				d.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				0);
		d.internalValidate();
		Assert.assertEquals(
				d.replaceCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				2);
		d.internalValidate();
		Assert.assertEquals(d.removeCard(Card.FOUR_HEARTS), true);
		d.internalValidate();
		Assert.assertEquals(
				d.replaceCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				1);
		d.internalValidate();
		Assert.assertEquals(
				d.removeCard(new Card[] { Card.FOUR_HEARTS, Card.THREE_CLUBS }),
				2);
		d.internalValidate();

		validateDeck(d);
	}

	public void testShuffle() {
		Deck d = new Deck();
		d.shuffle();
		try {
			Card[] cards = d.deal(52);
			Card[] cardsSorted = Card.values();
			int unsortedCount = 0;
			for (int i = 0; i < cards.length; i++) {
				if (cardsSorted[cards.length - 1 - i] == cards[i]) {
					unsortedCount++;
				} else {
					unsortedCount = 0;
				}
				if (unsortedCount >= 10) {
					Assert.fail("Deck is probably not shuffled properly");
				}
			}
			d.replaceCard(cards);
		} catch (EmptyDeckException e) {
			Assert.fail("Something went wrong");
		}

		validateDeck(d);
	}

	public void testSpeed() {
		long startTime = System.currentTimeMillis();
		int gamesSimulated = 0;
		while (System.currentTimeMillis() - startTime < 3000) {
			Deck d = new Deck();
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
		Deck d = new Deck();

		d.shuffle();

		Assert.assertEquals(d.size(), 52);
		Set<Card> dealtCards = new HashSet<Card>();

		try {
			for (int i = 0; i < 5; i++) {
				Card[] cards = d.deal(10);
				for (Card c : cards) {
					Assert.assertNotNull(c);
					Assert.assertFalse(dealtCards.contains(c));
					dealtCards.add(c);
				}
			}
		} catch (EmptyDeckException e) {
			Assert.fail("Deck should not be empty #1");
		}

		try {
			d.deal(3);
			Assert.fail("Deck should not be able to deal 3 cards");
		} catch (EmptyDeckException e) {

		}

		try {
			d.deal(2);
		} catch (EmptyDeckException e) {
			Assert.fail("Deck should not be empty #2");
		}

		try {
			d.dealOne();
			Assert.fail("Deck should not be able to deal a card");
		} catch (EmptyDeckException e) {

		}
	}
}
