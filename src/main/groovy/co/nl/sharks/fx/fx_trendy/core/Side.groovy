package co.nl.sharks.fx.fx_trendy.core

import groovy.transform.CompileStatic

@CompileStatic
/**
 * Represents a BUY or SELL side in a market.
 */
enum Side {
    BUY,
    SELL

    /**
     * Returns the inverted side, useful for opposite orders.
     *
     * @return The inverted side.
     */
    Side invert() {
        return this == BUY ? SELL : BUY
    }

    /**
     * Returns the side as a multiplier, using +1 for Buy and -1 for Sell. Useful for calculating distances.
     *
     * @return
     */
    int toMultiplier() {
        return this == BUY ? 1 : -1
    }

    /**
     * Get the sign of the exposure and convert it to a side. Useful for close orders.
     *
     * Assumes Sell exposure is negative, and Buy exposure is positive.
     *
     * @param exposure The exposure to use.
     * @return The side.
     */
    static Side exposureToSide(final int exposure) {
        return Math.signum(exposure) == 1 ? BUY : SELL
    }

    String toEmojiString() {
        BUY == this ? '🔼' : '🔽'
    }

    static List<Side> all() {
        [BUY, SELL]
    }
}
