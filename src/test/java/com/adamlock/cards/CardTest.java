package com.adamlock.cards;

import junit.framework.TestCase;

public class CardTest extends TestCase {
	public void testFromString() {
		try {
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("1c"));
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("Ac"));
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("ac"));
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("aC"));
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("1C"));
			TestCase.assertEquals(Card.ACE_CLUBS, Card.fromString("cA"));
			
			TestCase.assertEquals(Card.QUEEN_DIAMONDS, Card.fromString("Qd"));
			TestCase.assertEquals(Card.QUEEN_DIAMONDS, Card.fromString("dQ"));

			TestCase.assertEquals(Card.TWO_SPADES, Card.fromString("2s"));

		} catch (InvalidCardException e) {
			TestCase.fail("Nothing should have failed here");
		}

	
		try {
			Card.fromString("1c");
			Card.fromString("qq");
			Card.fromString(null);
			Card.fromString("");
			Card.fromString(" 1c");
			Card.fromString("1");
			Card.fromString("!");
			Card.fromString("1q!");
			TestCase.fail("Nothing should have worked here");
		} catch (InvalidCardException e) {
		}

	}
}
