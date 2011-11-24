/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

/**
 * A card and a very large random number used for shuffling
 * 
 * @author Adam Lock
 */
class ShuffleInfo {
	/** 4 bytes = 32 bits of randomness */
	private final byte[] order = new byte[4];

	/** The card */
	private final int cardIndex;

	/**
	 * Random number generator used for shuffling the deck
	 */
	static final Random RANDOM = new Random();

	ShuffleInfo(int cardIndex) {
		this.cardIndex = cardIndex;
		RANDOM.next(order);
	}

	public byte[] getOrder() {
		return order;
	}

	public int getCardIndex() {
		return cardIndex;
	}
}
