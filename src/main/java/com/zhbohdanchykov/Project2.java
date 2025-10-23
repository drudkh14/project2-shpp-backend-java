package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Project2 {

    private static final String PROPERTIES_FILE = "project2.properties";

    private static final Logger logger = LoggerFactory.getLogger(Project2.class);

    public static void main(String[] args) {
        logger.info("Starting Main.");

        Properties properties;
        try {
            properties = new PropertiesLoader(PROPERTIES_FILE).loadProperties();
        } catch (IOException e) {
            logger.error(String.valueOf(e));
            return;
        }

        NumericType type;
        try {type = NumericType.fromString(System.getProperty("type", "integer"));
            logger.info("Got {} NumericType.", type);
        } catch (IllegalArgumentException e) {
            logger.error(String.valueOf(e));
            return;
        }

        MultiplicationConfig config;
        try {
            config = new MultiplicationConfigValidator(properties, type).validate();
        } catch (IllegalArgumentException e) {
            logger.error(String.valueOf(e));
            return;
        }

        new MultiplicationTable(config, type.getNumberStrategy()).printMultiplicationTable();

        logger.info("Finished Main.");
    }
}