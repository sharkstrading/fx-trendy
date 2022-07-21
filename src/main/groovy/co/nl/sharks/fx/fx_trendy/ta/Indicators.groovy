package co.nl.sharks.fx.fx_trendy.ta

import co.nl.sharks.fx.fx_trendy.config.Settings
import co.nl.sharks.fx.fx_trendy.history.HistoryHelper
import com.dukascopy.api.Filter
import com.dukascopy.api.IHistory
import com.dukascopy.api.Instrument
import com.dukascopy.api.Period
import groovy.transform.CompileStatic

import java.time.*
import java.time.temporal.ChronoUnit

import static co.nl.sharks.fx.fx_trendy.helper.PreconditionsHelper.checkNotNullMulti

/**
 * Custom Technical Analysis indicators.
 */
@CompileStatic
class Indicators {
    static HistoricalPerformance historicalPerformance(final Instrument instrument, final IHistory history, final Instant currentTime = Instant.now()) {
        checkNotNullMulti([instrument, history, currentTime] as List<Object>, ["instrument", "history", "currentTime"])

        final LocalDateTime localDateTime = LocalDateTime.ofInstant(currentTime, ZoneId.of(Settings.TIMEZONE))
        final int year = localDateTime.year
        final def startOfYear = LocalDate.of(year, 1, 1)

        final def bars = HistoryHelper.getBars(history, currentTime.minus(700, ChronoUnit.DAYS), currentTime.truncatedTo(ChronoUnit.DAYS), instrument, Period.DAILY, Filter.NO_FILTER)

        if (bars.size() < 365)
            return null

        final def startOfYearBar = bars.find { it.time == startOfYear.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli() }

        if (startOfYearBar == null)
            return null

        final def mostRecentBar = bars.last()

        final def oneWeek = mostRecentBar.close <=> bars[-7].close
        final def oneMonth = mostRecentBar.close <=> bars[-30].close
        final def threeMonths = mostRecentBar.close <=> bars[-90].close
        final def sixMonths = mostRecentBar.close <=> bars[-180].close
        final def ytd = mostRecentBar.close <=> startOfYearBar.close
        final def oneYear = mostRecentBar.close <=> bars[-365].close

        new HistoricalPerformance(oneWeek, oneMonth, threeMonths, sixMonths, ytd, oneYear, instrument.toString())
    }
}
