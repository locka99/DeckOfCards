package com.adamlock.cards;

/**
 * Represents a deck of cards consisting of a unique or non unique set of Card.
 * It is possible that multi deck cards may also be derived from this type, in
 * which case particular behaviour about replacing cards, or removing cards
 * matching certain conditions may change to reflect the fact there are multiple
 * instances of the same card in the deck.
 * 
 * @author alock
 * @see Card
 * @see CardPattern
 */
public interface Deck {

	/**
	 * Resets the deck, ie puts all the drawn cards back in the deck. Note the
	 * deck may still be shuffled
	 */
	void reset();

	/**
	 * Randomly shuffles the undrawn cards.
	 */
	void shuffle();

	/**
	 * Test if all cards in the deck have been drawn.
	 * 
	 * @return true if empty, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Return the number of undrawn cards remaining in the deck.
	 * 
	 * @return cards remaining
	 */
	int size();

	/**
	 * Return the total number drawn and undrawn cards in the deck.
	 */
	int totalSize();

	/**
	 * Deal a number of cards from the top of the deck.
	 * 
	 * @param numCards
	 *            number of cards to deal
	 * @return array of drawn cards
	 * @throws EmptyDeckException
	 *             if the deck becomes empty before all the cards can be drawn.
	 */
	Card[] deal(int numCards) throws EmptyDeckException;

	/**
	 * Deal a number of cards from the deck that match the pattern.
	 * 
	 * @param pattern
	 *            the pattern the cards must correspond to
	 * @param numCards
	 *            number of cards, 1 or greater
	 * @return
	 * @throws EmptyDeckException
	 *             if the deck becomes empty before all the cards can be drawn.
	 */
	Card[] deal(CardPattern pattern, int numCards) throws EmptyDeckException;

	/**
	 * Deal a number of cards from the deck that match the pattern.
	 * 
	 * @param numCards
	 *            number of cards, 1 or greater
	 * @param inCards
	 *            an array to use / reuse to hold the result. If numCards is
	 *            larger than the array the method will fail.
	 * 
	 * @return cards dealt using the input array. Only numCards will be filled,
	 *         so remainder of array could contain junk
	 * @throws EmptyDeckException
	 *             if the deck becomes empty before all the cards can be drawn.
	 */
	Card[] deal(int numCards, Card[] inCards) throws EmptyDeckException;

	/**
	 * Deal a card which matches the specified pattern
	 * 
	 * @param pattern
	 * @return a card matching the pattern or null if no match was possible
	 */
	Card dealOne(CardPattern pattern) throws EmptyDeckException;

	/**
	 * Deal cards which match the specified patterns. Note the resulting array
	 * could contain nulls if no match is possible for the corresponding input.
	 * 
	 * When dealing cards, more exact card patterns may be given first chance to
	 * match over inexact patterns. So if the array held { Q?, Qc } then Queen
	 * of Clubs would be removed to satisfy Qc before attempting to draw
	 * something matching Q?.
	 * 
	 * @param patterns
	 *            patterns to use to deal cards
	 * @return drawn cards or null for cards which could not be drawn.
	 */
	Card[] deal(CardPattern patterns[]) throws EmptyDeckException,
			InvalidCardException;

	/**
	 * Deal one card from the deck.
	 * 
	 * @return drawn card
	 * @throws EmptyDeckException
	 */
	Card dealOne() throws EmptyDeckException;

	/**
	 * Tap a card randomly from anywhere in the undrawn cards and deal it.
	 * 
	 * @return randomly drawn card
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