package co.nl.sharks.fx.fx_trendy.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Connection {
    public String url
    public String username
    public String password
}
