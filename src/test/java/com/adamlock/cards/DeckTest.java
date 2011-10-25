/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Adam Lock
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DeckTest extends TestCase {
	Deck deck = new Deck();

	/*
	 * Class under test for Card dealOne(boolean)
	 */
	public void testDealOneboolean() {
		if (deck.size() != 52)
			Assert.fail("deck should have 52 cards and it doesn't!");
		try {
			ArrayList<Card> v = new ArrayList<Card>();
			for (int i = 0; i < 52; i++) {
				Card c = deck.dealOne(true);
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
				d.dealOne(true);
				cards++;
			}
		} catch (EmptyDeckException e) {
			if (cards != 52)
				Assert.fail("deck only dealt " + Integer.toString(cards)
						+ " cards");
		}
	}
}
