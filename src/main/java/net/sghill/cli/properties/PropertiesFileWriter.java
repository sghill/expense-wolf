package net.sghill.cli.properties;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static lombok.AccessLevel.PRIVATE;
import static net.sghill.cli.Literals.runtimeError;

@Slf4j
@AllArgsConstructor(access = PRIVATE)
public final class PropertiesFileWriter {
    private final String fileName;

    public void replaceContentsWith(Map<String, ?> keysAndVales) {
        try {
            Properties properties = new Properties();
            for(String key : keysAndVales.keySet()) {
                properties.setProperty(key, String.valueOf(keysAndVales.get(key)));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            properties.store(writer, "");
            writer.close();
        } catch (IOException e) {
            throw runtimeError(e, "Could not save {} to [{}]", keysAndVales.keySet(), fileName);
        }
        PropertiesFileWriter.log.info("Saved {} to [{}]", keysAndVales.keySet(), fileName);
    }

    public static PropertiesFileWriter writePropertiesTo(String fileName) {
        return new PropertiesFileWriter(fileName);
    }
}
