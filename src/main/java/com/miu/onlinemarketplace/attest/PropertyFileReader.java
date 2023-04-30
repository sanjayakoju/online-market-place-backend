package com.miu.onlinemarketplace.attest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

public class PropertyFileReader {
    private static Logger logger = LoggerFactory.getLogger(PropertyFileReader.class);

    private PropertyFileReader() {}

    public static String getValue(String location, String key) {
        String value = null;

        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);) {

            Properties properties = new Properties();
            properties.load(input);
            value = properties.getProperty(key);

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            logger.debug(sw.toString());
        }
        return value;
    }

}