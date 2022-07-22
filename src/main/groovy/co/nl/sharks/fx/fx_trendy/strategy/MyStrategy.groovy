package co.nl.sharks.fx.fx_trendy.strategy

import co.nl.sharks.fx.fx_trendy.config.ApplicationConfig
import co.nl.sharks.fx.fx_trendy.ta.HistoricalPerformance
import co.nl.sharks.fx.fx_trendy.ta.Indicators
import com.dukascopy.api.*
import groovy.transform.CompileStatic
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

@CompileStatic
class MyStrategy implements IStrategy {
    private static final Logger LOGGER = LogManager.getLogger(MyStrategy.class)

    private IContext context

    private final ApplicationConfig applicationConfig
    private final Period period
    private final Instrument instrument
    private final int numBarsMinimum
    private final int numBarsDesired
    private final TemporalUnit barsTimePeriod
    private final List<HistoricalPerformance> historicalPerformanceList

    private volatile ITick currentTick
    private volatile Instant currentTickTime

    MyStrategy(final ApplicationConfig applicationConfig, final Instrument instrument, final Period period, List<HistoricalPerformance> historicalPerformanceList, final int numBarsMinimum = 336, final int numBarsDesired = 350) {
        this.barsTimePeriod = ChronoUnit.HOURS
        this.historicalPerformanceList = historicalPerformanceList
        this.numBarsMinimum = numBarsMinimum
        this.numBarsDesired = numBarsDesired
        this.applicationConfig = applicationConfig
        this.instrument = instrument
        this.period = period
    }

    @Override
    void onStart(IContext context) {
        this.context = context

        LOGGER.info("${this.class.simpleName} starting up ...")

        final def historicPerformance = Indicators.historicalPerformance(instrument, context.history)

        historicalPerformanceList.add historicPerformance
    }

    @Override
    void onTick(Instrument instrument, ITick tick) {
        if (this.instrument != instrument)
            return

        currentTick = tick
        currentTickTime = Instant.ofEpochMilli(tick.time)
    }

    @Override
    void onBar(Instrument instrument, Period period, IBar askBar, IBar bidBar) {

    }

    @Override
    void onMessage(IMessage message) {

    }

    @Override
    void onAccount(IAccount account) {

    }

    @Override
    void onStop() throws JFException {

    }
}
