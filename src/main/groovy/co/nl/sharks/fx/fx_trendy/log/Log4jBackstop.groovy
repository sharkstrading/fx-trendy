package co.nl.sharks.fx.fx_trendy.log

import groovy.transform.CompileStatic
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Utility class for Log4J which makes it possible to have uncaught exceptions,
 * which normally would be logged to stderr, in the logfile, too.
 */
@CompileStatic
class Log4jBackstop implements Thread.UncaughtExceptionHandler {
    private static Logger LOGGER = LogManager.getLogger(Log4jBackstop.class)

    void uncaughtException(Thread t, Throwable ex) {
        LOGGER.error("Uncaught exception in thread: " + t.getName(), ex)
    }
}