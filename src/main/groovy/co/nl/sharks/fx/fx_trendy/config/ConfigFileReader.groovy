package co.nl.sharks.fx.fx_trendy.config

import co.nl.sharks.fx.fx_trendy.helper.JacksonHelper
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import static co.nl.sharks.fx.fx_trendy.helper.PreconditionsHelper.checkNotNullMulti

/**
 * This class doesn't have to be used by the end user, use ConfigReader instead
 * which may use this class.
 */
@CompileStatic
class ConfigFileReader {
    private static final Logger LOGGER = LogManager.getLogger(ConfigFileReader.class)

    static <T> T readConfigFromFileYAML(final String filename, final Class<T> klazz) {
        checkNotNullMulti([filename, klazz] as List<Object>, ["filename", "class"])

        final ObjectMapper mapper = JacksonHelper.objectMapperYaml()
        final T config
        try {
            config = mapper.readValue(new File(filename), klazz)
            return config
        } catch (Exception e) {
            LOGGER.error("While reading YAML config file: ", e)
        }

        return null
    }
}