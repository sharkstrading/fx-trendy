package co.nl.sharks.fx.fx_trendy.intf

import com.dukascopy.api.IStrategy
import groovy.transform.CompileStatic

@CompileStatic
interface StrategyStopListener {
    void onStrategyStop(final IStrategy strategy)
}