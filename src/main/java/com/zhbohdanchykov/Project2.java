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
            logger.error(e.getMessage());
            return;
        }

        Class<? extends Number> clazz;
        try {
            clazz = new ClassGetter(System.getProperty("type", "int")).getNumericClass();
            logger.info("Got {} from ClassGetter.", clazz);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return;
        }

        MultiplicationTable table;
        try {
            table = new MultiplicationTable(clazz, properties);
            table.printMultiplicationTable();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }

        logger.info("Finished Main.");
    }
}