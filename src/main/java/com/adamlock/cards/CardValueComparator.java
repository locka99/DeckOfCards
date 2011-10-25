/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

import java.util.Comparator;

public class CardValueComparator {

	private static final Comparator<CardValue> comparator = new Comparator<CardValue>() {
		@Override
		public int compare(CardValue cv1, CardValue cv2) {
			return CardValue.compareValues(cv1, cv2);
		}
	};

	private final static Comparator<CardValue> reverseComparator = new Comparator<CardValue>() {
		@Override
		public int compare(CardValue cv1, CardValue cv2) {
			return CardValue.compareValues(cv2, cv1);
		}
	};

	public static Comparator<CardValue> getComparator() {
		return comparator;
	}

	public static Comparator<CardValue> getReverseComparator() {
		return reverseComparator;
	}
}
