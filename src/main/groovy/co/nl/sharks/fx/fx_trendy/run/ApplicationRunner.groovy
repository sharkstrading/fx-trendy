package co.nl.sharks.fx.fx_trendy.run

import co.nl.sharks.fx.fx_trendy.config.ConfigFileReader
import co.nl.sharks.fx.fx_trendy.config.SettingsDukascopy
import co.nl.sharks.fx.fx_trendy.core.Side
import co.nl.sharks.fx.fx_trendy.dukas.StrategyRunner
import co.nl.sharks.fx.fx_trendy.strategy.OpportunityScannerStrategy
import com.dukascopy.api.Instrument
import groovy.transform.CompileStatic
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.time.Instant
import java.time.LocalDate

@CompileStatic
class ApplicationRunner {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationRunner.class)

    void run(final String[] args) {
        final def config = ConfigFileReader.readConfigFromFileYAML(co.nl.sharks.fx.fx_trendy.config.Settings.FILE_APPLICATION_CONFIG_YAML, co.nl.sharks.fx.fx_trendy.config.ApplicationConfig.class)

        if(config == null) {
            LOGGER.error("${ConfigFileReader.class.simpleName} Need config to continue. Did you populate the file ${co.nl.sharks.fx.fx_trendy.config.Settings.FILE_APPLICATION_CONFIG_YAML}?")

            System.exit(1)
        }


        LOGGER.info("----------------------------------------------------------------------")
        LOGGER.info("os-jforex application starting up ...")
        LOGGER.info("----------------------------------------------------------------------")
        LOGGER.info "Current time: " + Instant.now()
        LOGGER.info("----------------------------------------------------------------------")

        runApplication(args, config)
    }

    private void runApplication(final String[] args, final co.nl.sharks.fx.fx_trendy.config.ApplicationConfig config) {
        final StrategyRunner strategyRunner = new StrategyRunner()

        final def period = SettingsDukascopy.DATA_FEED_PERIOD
        // gather instruments
        final Set<Instrument> instruments = config.instruments
                .collect({ Instrument.valueOf(it) })
                .toSet()

        LOGGER.info("-------------------------------------------------------")
        LOGGER.info("Instruments:")
        instruments.each { LOGGER.info("  ${it}") }
        LOGGER.info("-------------------------------------------------------")

        final List<co.nl.sharks.fx.fx_trendy.ta.HistoricalPerformance> historicalPerformanceList = []

        instruments.each { Instrument instrument ->
            final def scanner = new OpportunityScannerStrategy(config, instrument, period, historicalPerformanceList)

            strategyRunner.run(
                    config.connection.url,
                    config.connection.username,
                    config.connection.password,
                    [instrument].toSet(),
                    scanner
            )
        }

        // filters
        // TODO from config
        final List<Side> sides = [Side.SELL]

        final def historicalPerformanceToUse = historicalPerformanceList
            .findAll { it.dominantSide() != null && sides.contains(it.dominantSide()) }
            .toSorted { it.toString() }
            .reverse()

        if(!historicalPerformanceList.isEmpty()) {
            LOGGER.info("${LocalDate.now()} Today's Trendy Forex instruments:")
            LOGGER.info("Sides: ${sides}")
            LOGGER.info("Detected ${historicalPerformanceList.size()} instruments")
            LOGGER.info("-------------------------------------------------------")
            historicalPerformanceToUse.each { LOGGER.info it }
        }

        System.exit(0)
    }
}
