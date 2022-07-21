package co.nl.sharks.fx.fx_trendy.config

import com.dukascopy.api.OfferSide
import com.dukascopy.api.Period
import groovy.transform.CompileStatic

/**
 * Settings that use the Dukascopy API cannot be exported to other projects easily.
 *
 * Keep them separate from the "vanilla" settings to prevent problems.
 */
@CompileStatic
class SettingsDukascopy {
    // data feed settings
    public static final Period DATA_FEED_PERIOD = Period.ONE_HOUR

    // offer side
    public static final OfferSide OFFER_SIDE = OfferSide.BID
}
