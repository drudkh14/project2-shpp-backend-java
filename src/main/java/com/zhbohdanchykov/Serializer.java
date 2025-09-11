package com.zhbohdanchykov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class Serializer represents an object that serializes given object to JSON or XML
 *
 * @param <T> Type of object
 */
public class Serializer<T> {

    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

    private T object;

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    /**
     * Constructs a serializer with given object
     *
     * @param object Given object
     */
    public Serializer(T object) {
        this.object = object;
        logger.debug("Created Serializer {} for given Object {}", this, object);
    }

    /**
     * Sets object to serialize
     *
     * @param object Object to serialize
     */
    public void setObject(T object) {
        this.object = object;
    }

    /**
     * Returns object in JSON format
     *
     * @return Object in JSON format
     * @throws JsonProcessingException to throw if there is a problem with serializing
     */
    public String serializeToJson() throws JsonProcessingException {
        logger.debug("Serializing {} to JSON with ObjectMapper", object);
        return jsonMapper.writeValueAsString(object);
    }

    /**
     * Returns object in XML format
     *
     * @return Object in XML format
     * @throws JsonProcessingException to throw if there is a problem with serializing
     */
    public String serializeToXml() throws JsonProcessingException {
        logger.debug("Serializing {} to XML with XmlMapper", this);
        return xmlMapper.writeValueAsString(object);
    }
}
