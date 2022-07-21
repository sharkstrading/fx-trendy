package co.nl.sharks.fx.fx_trendy.history


import com.dukascopy.api.*
import groovy.transform.CompileStatic

import java.time.Instant

import static co.nl.sharks.fx.fx_trendy.helper.PreconditionsHelper.checkNotNullMulti

/**
 * The Dukas API is hella complicated, but we like things simple.
 * So give this class a try and see if you like it.
 */
@CompileStatic
class HistoryHelper {
    static List<IBar> getBars(IHistory history, Instant start, Instant end, Instrument instrument, Period period, Filter filter) {
        checkNotNullMulti([history, start, end, instrument, period, filter] as List<Object>, ["history", "start", "end", "instrument", "period", "filter"])

        final long startGatheringDataLong = startLong(history, period, start)
        final long currentTimeLong = endLong(history, period, end)
        final List<IBar> bars = history.getBars(instrument, period, co.nl.sharks.fx.fx_trendy.config.SettingsDukascopy.OFFER_SIDE, filter, startGatheringDataLong, currentTimeLong)

        bars
    }

    // provide the correct long timestamp for use with the Dukascopy API
    static long endLong(IHistory history, Period period, Instant end) {
        checkNotNullMulti([history, end, period] as List<Object>, ["history", "end", "period"])

        history.getPreviousBarStart(period, end.toEpochMilli())
    }

    // provide the correct long timestamp for use with the Dukascopy API
    static long startLong(IHistory history, Period period, Instant start) {
        checkNotNullMulti([history, period, start] as List<Object>, ["history", "period", "start"])

        final long shift = period.interval
        history.getPreviousBarStart(period, start.toEpochMilli()) + shift
    }
}
