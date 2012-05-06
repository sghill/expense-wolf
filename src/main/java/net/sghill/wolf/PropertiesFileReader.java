package net.sghill.wolf;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import static java.lang.Long.valueOf;

@Slf4j
public final class PropertiesFileReader {
    private final Properties properties;

    public PropertiesFileReader(String fileName) {
        properties = new Properties();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            properties.load(reader);
            reader.close();
        } catch (Exception e) {
            throw runtimeError(e, "[{}] not found", fileName);
        }
    }

    public Long getAsLong(String key) {
        String property = properties.getProperty(key);
        try {
            return valueOf(property);
        } catch (NumberFormatException e) {
            throw runtimeError(e, "[{}] could not be parsed as a Long", property);
        }
    }

    private static RuntimeException runtimeError(Exception e, String formatStringMessage, Object... objects) {
        PropertiesFileReader.log.error(formatStringMessage, objects);
        return new RuntimeException(e);
    }
}
