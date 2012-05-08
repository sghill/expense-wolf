package net.sghill.wolf;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static net.sghill.wolf.Utilities.runtimeError;

@Slf4j
@AllArgsConstructor
public final class PropertiesFileWriter {
    private final String fileName;

    public void writeAndClose(Map<String, String> keysAndVales) {
        try {
            Properties properties = new Properties();
            for(String key : keysAndVales.keySet()) {
                properties.setProperty(key, keysAndVales.get(key));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            properties.store(writer, "");
            writer.close();
        } catch (IOException e) {
            throw runtimeError(e, "Could not save {} to [{}]", keysAndVales.keySet(), fileName);
        }
        log.info("Saved {} to [{}]", keysAndVales.keySet(), fileName);
    }
}
