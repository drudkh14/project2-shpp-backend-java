package com.zhbohdanchykov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

class PropertiesLoaderTest {

    @Test
    void testLoadProperties() throws Exception {
        PropertiesLoader propertiesLoader = new PropertiesLoader("test.properties");
        Properties properties = propertiesLoader.loadProperties();

        Assertions.assertNotNull(properties);
        Assertions.assertEquals(properties, propertiesLoader.loadProperties());
        Assertions.assertEquals("1", properties.getProperty("min"));
        Assertions.assertEquals("10", properties.getProperty("max"));
        Assertions.assertEquals("1", properties.getProperty("inc"));
    }

    @Test
    void testLoadPropertiesNoFile() {
        PropertiesLoader propertiesLoader = new PropertiesLoader("no_such_file.properties");
        Assertions.assertThrows(IOException.class, propertiesLoader::loadProperties);
    }
}