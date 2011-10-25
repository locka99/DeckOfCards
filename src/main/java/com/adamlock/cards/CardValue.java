/**
 * Copyright 2011. Adam Lock <locka99@gmail.com>
 *
 * Available as open source under the terms of LGPLv3
 */
package com.adamlock.cards;

public enum CardValue {
	TWO('2', "Two"), THREE('3', "Three"), FOUR('4', "Four"), FIVE('5', "Five"), SIX(
			'6', "Six", "Sixes"), SEVEN('7', "Seven"), EIGHT('8', "Eight"), NINE('9',
			"Nine"), TEN('T', "Ten"), JACK('J', "Jack"), QUEEN('Q', "Queen"), KING(
			'K', "King"), ACE('A', "Ace");

	private char value;

	private String name;
	
	private String pluralName;

	private int ordinal;

	private static final CardValue[] REVERSE_VALUES;

	static {
		final CardValue[] cardValues = values();
		REVERSE_VALUES = new CardValue[cardValues.length];
		int i = 0;
		for (CardValue value : cardValues) {
			REVERSE_VALUES[cardValues.length - 1 - i] = value;
			i++;
		}
	}

	final private static String VALUES = "23456789TJQKA";
	
	private CardValue(char value, String name, String pluralName) {
		this.value = value;
		this.name = name;
		this.pluralName = pluralName;
		this.ordinal = VALUES.indexOf(value);
	}
	
	private CardValue(char value, String name) {
		this(value, name, name + "s");
	}

	public char getValueChar() {
		return value;
	}

	/**
	 * Get the ordinal of the value, i.e. it's relative value
	 * 
	 * @return
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * Return the values in reverse order
	 * 
	 * @return
	 */
	static public CardValue[] reverseValues() {
		return REVERSE_VALUES;
	}

	/**
	 * Class helper method tests if the specified value is valid
	 * 
	 * @param value
	 *            value to test
	 * @return true if the parameter is a valid value, false otherwise
	 */
	static public boolean isValidValue(char value) {
		// Ace is special cased and can be 1 or A
		final char v = value == '1' ? 'A' : Character.toUpperCase(value);
		return (VALUES.indexOf(v) != -1);
	}

	/**
	 * Turns a char to a CardValue enum
	 * 
	 * @param value
	 * @return
	 * @throws InvalidCardException
	 */
	static public CardValue toCardValue(char inValue) throws InvalidCardException {
		char value = Character.toUpperCase(inValue);

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
	 * Checks if this value is greater by one than the other value
	 * 
	 * @param other
	 * @return
	 */
	public boolean isGreaterValueByOne(CardValue other) {
		final int iThis = VALUES.lastIndexOf(getValueChar());
		final int iOther = VALUES.lastIndexOf(other.getValueChar());
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
		return usePlural ? pluralName : name;
	}

	@Override
	public String toString() {
		return toString(false);
	}

	public static int compareValues(CardValue value1, CardValue value2) {
		final int ordinalThis = value1.getOrdinal();
		final int ordinalOther = value2.getOrdinal();
		if (ordinalThis == ordinalOther) {
			return 0;
		} else if (ordinalThis > ordinalOther) {
			return 1;
		} else {
			return -1;
		}
	}
}
