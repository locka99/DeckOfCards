/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import junit.framework.TestCase;

public class PatternTest extends TestCase {

	public void testMatchExact() {
		final CardPattern p = CardPattern.fromCard(Card.EIGHT_SPADES);

		TestCase.assertNotNull(p);
		TestCase.assertFalse(p.matches(null));

		final Deck d = new Deck();
		while (true) {
			Card c;
			try {
				c = d.dealOne();
			} catch (EmptyDeckException e) {
				break;
			}
			TestCase.assertEquals(p.matches(c), c == Card.EIGHT_SPADES);
		}
	}

	public void testDealOne() {
		Deck d = new Deck();
		d.shuffle();

		final CardPattern p = CardPattern.fromCard(Card.EIGHT_SPADES);

		try {
			TestCase.assertEquals(d.dealOne(p), Card.EIGHT_SPADES);
			TestCase.assertEquals(d.size(), 51);
			TestCase.assertNull(d.dealOne(p));
			TestCase.assertEquals(d.size(), 51);
		} catch (EmptyDeckException e) {
			TestCase.fail();
		}
	}

	public void testDealMany() {
		Deck d = new Deck();
		d.shuffle();

		final CardPattern p = CardPattern.fromSuit(CardSuit.HEARTS);

		try {
			final Card cards[] = d.deal(p, 13);
			for (Card c : cards) {
				TestCase.assertNotNull(c);
				TestCase.assertEquals(c.getSuit(), CardSuit.HEARTS);
			}
		} catch (EmptyDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testMatchAny() {
		final CardPattern p = CardPattern.RANDOM;

		TestCase.assertNotNull(p);
		TestCase.assertFalse(p.matches(null));

		final Deck d = new Deck();
		while (true) {
			Card c;
			try {
				c = d.dealOne();
			} catch (EmptyDeckException e) {
				break;
			}
			TestCase.assertTrue(p.matches(c));
		}
	}

	public void testMatchSuit() {
		final CardPattern p = CardPattern.fromSuit(CardSuit.HEARTS);

		TestCase.assertNotNull(p);
		TestCase.assertFalse(p.matches(null));

		final Deck d = new Deck();
		while (true) {
			Card c;
			try {
				c = d.dealOne();
			} catch (EmptyDeckException e) {
				break;
			}
			TestCase.assertEquals(p.matches(c), c.getSuit() == CardSuit.HEARTS);
		}
	}

	public void testMatchValue() {
		final CardPattern p = CardPattern.fromValue(CardValue.JACK);

		TestCase.assertNotNull(p);
		TestCase.assertFalse(p.matches(null));

		final Deck d = new Deck();
		while (true) {
			Card c;
			try {
				c = d.dealOne();
			} catch (EmptyDeckException e) {
				break;
			}
			TestCase.assertEquals(p.matches(c), c.getValue() == CardValue.JACK);
		}
	}

	public void testMatchSet() {

		Card inCards[] = { Card.EIGHT_CLUBS, Card.JACK_HEARTS, Card.TEN_CLUBS };
		final CardPattern p = CardPattern.setPatternfromCards(inCards);

		TestCase.assertNotNull(p);
		TestCase.assertFalse(p.matches(null));

		final Deck d = new Deck();
		outer: while (true) {
			Card c;
			try {
				c = d.dealOne();
			} catch (EmptyDeckException e) {
				break;
			}
			for (Card inCard : inCards) {
				if (c == inCard) {
					TestCase.assertTrue(p.matches(c));
					break outer;
				}
			}
			TestCase.assertFalse(p.matches(c));
		}

	}
}
