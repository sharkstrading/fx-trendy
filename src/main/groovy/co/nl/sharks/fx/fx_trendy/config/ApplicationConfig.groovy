package co.nl.sharks.fx.fx_trendy.config

import co.nl.sharks.fx.fx_trendy.core.Side
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@JsonIgnoreProperties(ignoreUnknown = true)
@CompileStatic
class ApplicationConfig {
    public Connection connection
    public List<String> instruments
    public List<Side> sides
}
