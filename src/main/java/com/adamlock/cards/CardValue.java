/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

public enum CardValue {
	TWO('2', "Two", 0x0001), THREE('3', "Three", 0x0002), FOUR('4', "Four",
			0x0004), FIVE('5', "Five", 0x0008), SIX('6', "Six", 0x0010), SEVEN(
			'7', "Seven", 0x0020), EIGHT('8', "Eight", 0x0040), NINE('9',
			"Nine", 0x0080), TEN('T', "Ten", 0x0100), JACK('J', "Jack", 0x0200), QUEEN(
			'Q', "Queen", 0x0400), KING('K', "King", 0x1000), ACE('A', "Ace",
			0x2000);

	private char value;

	private String name;

	private int bit;

	private final static String values = "23456789TJQKA";

	private CardValue(char value, String name, int bit) {
		this.value = value;
		this.name = name;
		this.bit = bit;
	}

	public char getValueChar() {
		return value;
	}

	/**
	 * Return the value as a bit in an int
	 * 
	 * @return
	 */
	public int getValueBit() {
		return bit;
	}

	/**
	 * Return the values in reverse order
	 * 
	 * @return
	 */
	static public CardValue[] reverseValues() {
		final CardValue[] cardValues = CardValue.values();
		final CardValue[] reversedCardValues = new CardValue[cardValues.length];

		int i = 0;
		for (CardValue value : cardValues) {
			reversedCardValues[cardValues.length - 1 - i] = value;
			i++;
		}

		return reversedCardValues;
	}

	/**
	 * Class helper method tests if the specified value is valid
	 * 
	 * @param value
	 *            value to test
	 * @return true if the parameter is a valid value, false otherwise
	 */
	static public boolean isValidValue(char value) {
		value = Character.toUpperCase(value);
		// Ace is special cased and can be 1 or A
		if (value == '1')
			value = 'A';
		return (values.indexOf(Character.toUpperCase(value)) != -1);
	}

	/**
	 * Turns a char to a CardValue enum
	 * 
	 * @param value
	 * @return
	 * @throws InvalidCardException
	 */
	static public CardValue toCardValue(char value) throws InvalidCardException {
		value = Character.toUpperCase(value);

		// Ace is special cased and can be 1 or A
		if (value == '1') {
			value = 'A';
		}
		if (!isValidValue(value)) {
			throw new InvalidCardException("value " + Character.toString(value)
					+ " is invalid");
		}

		for (CardValue v : CardValue.values()) {
			if (v.getValueChar() == value)
				return v;
		}

		// Should never be reached
		throw new InvalidCardException("Unreachable code");
	}

	/**
	 * Compare this value to another
	 * 
	 * @param other
	 * @return
	 */
	public int compare(CardValue other) {
		final int iThis = values.lastIndexOf(getValueChar());
		final int iOther = values.lastIndexOf(other.getValueChar());
		if (iThis == iOther) {
			return 0;
		} else if (iThis > iOther) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Checks if this value is greater by one than the other value
	 * 
	 * @param other
	 * @return
	 */
	public boolean isGreaterValueByOne(CardValue other) {
		final int iThis = values.lastIndexOf(getValueChar());
		final int iOther = values.lastIndexOf(other.getValueChar());
		return (iThis == iOther + 1);
	}

	/**
	 * Returns the value as a string
	 * 
	 * @param usePlural
	 *            flag to return plural version of the value, e.g. sevens
	 *            instead of seven
	 * @return
	 */
	public String toString(boolean usePlural) {
		String valueStr = name;
		if (usePlural) {
			valueStr = valueStr + ((this == SIX) ? "es" : "s");
		}
		return valueStr;
	}

	@Override
	public String toString() {
		return toString(false);
	}
}
