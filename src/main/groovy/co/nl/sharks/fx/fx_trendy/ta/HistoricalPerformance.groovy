package co.nl.sharks.fx.fx_trendy.ta

import co.nl.sharks.fx.fx_trendy.core.Side
import groovy.transform.CompileStatic

/**
 * Clone of TradingView's Performance tab (bottom right)
 */
@CompileStatic
class HistoricalPerformance {
    static final int MIN_STRENGTH = 0
    static final int MAX_STRENGTH = 6

    final int oneWeek, oneMonth, threeMonths, sixMonths, yearToDate, oneYear
    final String symbol

    HistoricalPerformance(int oneWeek, int oneMonth, int threeMonths, int sixMonths, int yearToDate, int oneYear, String symbol) {
        this.symbol = symbol
        this.oneWeek = oneWeek
        this.oneMonth = oneMonth
        this.threeMonths = threeMonths
        this.sixMonths = sixMonths
        this.yearToDate = yearToDate
        this.oneYear = oneYear
    }

    Side dominantSide() {
        def numUp = numUp()
        def numDown = numDown()

        if(numUp == numDown)
            return null

        numUp > numDown ? Side.BUY : Side.SELL
    }

    Integer sideStrength(final Side side) {
        if(side == null)
            return 0

        final def list = collect().findAll { it == side.toMultiplier() }

        list == null ? null : list.size()
    }

    int numUp() {
        collect().findAll { it == 1 }.size()
    }

    int numDown() {
        collect().findAll { it == -1 }.size()
    }

    Collection<Integer> collect() {
        [ oneWeek, oneMonth, threeMonths, sixMonths, yearToDate, oneYear ]
    }

    Integer dominantSideStrength() {
        final Side side = dominantSide() ?: Side.BUY

        sideStrength(side)
    }

    @Override
    String toString() {
        "${dominantSideStrength()}/${MAX_STRENGTH} ${symbol} ${dominantSide()}"
    }
}
