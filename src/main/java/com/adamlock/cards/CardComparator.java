/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.Comparator;

/**
 * Compares one card to another
 * 
 * @author Adam
 */
public class CardComparator {
	static private final Comparator<Card> comparator = new Comparator<Card>() {
		@Override
		public int compare(Card c1, Card c2) {
			return Card.compareCards(c1, c2);
		}
	};

	private final static Comparator<Card> reverseComparator = new Comparator<Card>() {
		@Override
		public int compare(Card c1, Card c2) {
			return Card.compareCards(c2, c1);
		}
	};

	public static Comparator<Card> getComparator() {
		return comparator;
	}

	public static Comparator<Card> getReverseComparator() {
		return reverseComparator;
	}

}
