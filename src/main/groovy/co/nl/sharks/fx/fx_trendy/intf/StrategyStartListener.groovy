package co.nl.sharks.fx.fx_trendy.intf

import com.dukascopy.api.IStrategy
import groovy.transform.CompileStatic

@CompileStatic
interface StrategyStartListener {
    void onStrategyStart(final IStrategy strategy)
}