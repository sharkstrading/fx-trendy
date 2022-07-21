package co.nl.sharks.fx.fx_trendy.dukas

import com.dukascopy.api.IStrategy
import com.dukascopy.api.Instrument
import com.dukascopy.api.system.ClientFactory
import com.dukascopy.api.system.IClient
import groovy.transform.CompileStatic

/**
 * Run a Dukascopy SDK strategy in a production environment (as opposed to in a backtest)
 */
@CompileStatic
class StrategyRunner {
    void run(final String url,
             final String username,
             final String password,
             final Set<Instrument> instruments,
             final IStrategy strategy) {
        final IClient client = ClientFactory.getDefaultInstance()

        client.setSystemListener(new SystemListenerImpl(client, url, username, password))
        client.connect(url, username, password)

        // wait for a bit for the client to connect
        while (!client.isConnected()) {
            Thread.sleep(500)
        }

        // set instruments
        client.setSubscribedInstruments instruments

        // start strategy
        client.startStrategy(strategy)
    }
}
