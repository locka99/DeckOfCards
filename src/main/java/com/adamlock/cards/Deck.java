package com.adamlock.cards;

public interface Deck {

	/**
	 * Resets the deck, ie puts all the drawn cards back in the deck. Note the
	 * deck may still be shuffled
	 */
	void reset();

	/**
	 * Randomly shuffles the undrawn cards
	 */
	void shuffle();

	/**
	 * Test if the deck is empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Return the number of cards in the deck.
	 * 
	 * @return cards remaining
	 */
	int size();
	
	/**
	 * Return the number of cards in the undealt deck.
	 */
	int undealtSize();

	/**
	 * Deal cards from the deck
	 * 
	 * @param numCards
	 *            number of cards to deal
	 * @return array of dealt cards
	 * @throws EmptyDeckException
	 */
	Card[] deal(int numCards) throws EmptyDeckException;

	/**
	 * Deal cards from the deck that match the pattern.
	 * 
	 * @param pattern
	 *            the pattern the cards must correspond to
	 * @param numCards
	 *            number of cards, 1 or greater
	 * @return
	 * @throws EmptyDeckException
	 */
	Card[] deal(CardPattern pattern, int numCards) throws EmptyDeckException;

	/**
	 * Deal a card which matches the specified pattern
	 * 
	 * @param pattern
	 * @return a card matching the pattern or null if no match was possible
	 */
	Card dealOne(CardPattern pattern) throws EmptyDeckException;

	/**
	 * Deal cards which match the specified patterns. Note the resulting array
	 * could contain nulls if no match is possible.
	 * 
	 * @param patterns
	 * @return
	 */
	Card[] deal(CardPattern patterns[]) throws EmptyDeckException,
			InvalidCardException;

	/**
	 * Deal one card from the deck
	 * 
	 * @return dealt card
	 * @throws EmptyDeckException
	 */
	Card dealOne() throws EmptyDeckException;

	/**
	 * Draw a card randomly from somewhere out of the undrawn deck.
	 * 
	 * @return
	 * @throws EmptyDeckException
	 */
	Card dealRandom() throws EmptyDeckException;

	/**
	 * Remove a card from the undrawn pile in the deck. Card is placed at end of
	 * drawn pile. If the card is undrawn it will not be moved.
	 * 
	 * @param card
	 * @return true if card was returned, false if it didn't need to be
	 */
	boolean removeCard(Card card);

	/**
	 * Remove an array of cards from the undrawn pile in the deck and put them
	 * at the end of the drawn pile.
	 * 
	 * @param cards
	 * @return the number of cards actually removed
	 */
	int removeCard(Card[] cards);

	/**
	 * Replaces a card from the drawn pile back onto the end of the undrawn
	 * pile..
	 * 
	 * @param card
	 *            card to be added
	 * @return true if the card was returned to the undrawn pile, false if it
	 *         wasn't
	 */
	boolean replaceCard(Card card);

	/**
	 * Replaces cards from the drawn pile back to the undrawn pile. The go at
	 * the end of the drawn pile. Cards which are not in the drawn pile will not
	 * be moved.
	 * 
	 * @param cards
	 * @return the number of cards returned to the undrawn pile.
	 */
	int replaceCard(Card[] cards);

}