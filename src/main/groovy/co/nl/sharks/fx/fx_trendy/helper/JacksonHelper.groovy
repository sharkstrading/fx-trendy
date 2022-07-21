package co.nl.sharks.fx.fx_trendy.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import groovy.transform.CompileStatic

@CompileStatic
class JacksonHelper {
    static ObjectMapper objectMapperYaml() {
        new ObjectMapper(new YAMLFactory())
    }

    static ObjectMapper objectMapperJson() {
        new ObjectMapper()
                .findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
    }
}
