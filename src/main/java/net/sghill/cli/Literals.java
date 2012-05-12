package net.sghill.cli;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class Literals {
    public static RuntimeException runtimeError(Exception e, String formatStringMessage, Object... objects) {
        log.error(formatStringMessage, objects);
        return new RuntimeException(e);
    }

    public static RuntimeException runtimeError(String formatStringMessage, Object... objects) {
        return runtimeError(null, formatStringMessage, objects);
    }
}
