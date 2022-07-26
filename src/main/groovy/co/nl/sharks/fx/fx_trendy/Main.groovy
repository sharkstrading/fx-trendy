package co.nl.sharks.fx.fx_trendy

import co.nl.sharks.fx.fx_trendy.log.Log4jBackstop
import co.nl.sharks.fx.fx_trendy.run.ApplicationRunner
import groovy.transform.CompileStatic

@CompileStatic
class Main {
    static void main(String[] args) {
        /**
         * Utility class for Log4J which makes it possible to have uncaught exceptions,
         * which normally would be logged to stderr, in the logfile, too.
         */
        Thread.setDefaultUncaughtExceptionHandler(new Log4jBackstop())

        new ApplicationRunner().run(args)
    }
}
