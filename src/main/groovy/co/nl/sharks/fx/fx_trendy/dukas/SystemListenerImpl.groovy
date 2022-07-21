package co.nl.sharks.fx.fx_trendy.dukas

import com.dukascopy.api.system.IClient
import com.dukascopy.api.system.ISystemListener
import groovy.transform.CompileStatic
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Necessary class to run strategies
 */
@CompileStatic
class SystemListenerImpl implements ISystemListener {
    private static final Logger LOGGER = LogManager.getLogger(SystemListenerImpl.class)

    private final IClient client
    private final String url
    private final String username
    private final String password

    SystemListenerImpl(IClient client, String url, String username, String password) {
        this.client = client
        this.url = url
        this.username = username
        this.password = password
    }

    @Override
    void onStart(long processId) {
        LOGGER.info("Strategy started: " + processId)
    }

    @Override
    void onStop(long processId) {
        LOGGER.info("Strategy stopped: " + processId)
        if (client.getStartedStrategies().size() == 0) {
            System.exit(0)
        }
    }

    @Override
    void onConnect() {
        LOGGER.info("Connected to the Dukascopy service")
    }

    @Override
    void onDisconnect() {
        Runnable runnable = new Runnable() {
            @Override
            void run() {
                do {
                    try {
                        Thread.sleep(60 * 1000)
                    } catch (InterruptedException e) {
                    }
                    try {
                        if (client.isConnected()) {
                            break
                        }
                        client.connect(url, username, password)
                    } catch(UnknownHostException e) {
                        LOGGER.error("Failed to connect to the Dukascopy service. Retrying ...")
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e)
                    }
                } while (!client.isConnected())
            }
        }
        new Thread(runnable).start()
    }
}