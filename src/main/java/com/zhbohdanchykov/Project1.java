package com.zhbohdanchykov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Project1 {

    private static final String PROPERTIES_FILE = "project1.properties";

    private static final Logger logger = LoggerFactory.getLogger(Project1.class);

    public static void main(String[] args) {
        Properties properties;
        try {
            properties = new PropertiesLoader(PROPERTIES_FILE).loadProperties();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        }

        MultiplicationTable table = switch (System.getProperty("type", "int")) {
            case "float" -> new MultiplicationTable(Float.class, properties);
            case "double" -> new MultiplicationTable(Double.class, properties);
            case "short" -> new MultiplicationTable(Short.class, properties);
            case "byte" -> new MultiplicationTable(Byte.class, properties);
            case "long" -> new MultiplicationTable(Long.class, properties);
            case "int" -> new MultiplicationTable(Integer.class, properties);
            default -> throw new IllegalStateException("Unexpected type: " + System.getProperty("type"));
        };

        MultiplicationTablePrinter printer = new MultiplicationTablePrinter(table);
        printer.print();
    }
}