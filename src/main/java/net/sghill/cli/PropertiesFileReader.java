package net.sghill.cli;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import static net.sghill.cli.Literals.runtimeError;

@Slf4j
public final class PropertiesFileReader {
    private final Properties properties = new Properties();

    private PropertiesFileReader(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            properties.load(reader);
            reader.close();
        } catch (Exception e) {
            throw runtimeError(e, "[{}] not found", fileName);
        }
    }

    public static PropertiesFileReader readPropertiesFrom(String fileName) {
        return new PropertiesFileReader(fileName);
    }

    public Property get(String key) {
        return new Property(key, properties.getProperty(key));
    }
}
