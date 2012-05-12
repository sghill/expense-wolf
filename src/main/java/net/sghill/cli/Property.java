package net.sghill.cli;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.javafunk.funk.monads.Option;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.some;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Property {
    @Getter private final String key;
    private final String value;

    public Option<Long> asLong() {
        try {
            return some(Long.valueOf(value));
        } catch (NumberFormatException e) {
            return none();
        }
    }

    public Option<Integer> asInteger() {
        try {
            return some(Integer.valueOf(value));
        } catch (NumberFormatException e) {
            return none();
        }
    }

    public Option<String> asString() {
        return isNotBlank(value) ? some(value) : Option.<String>none();
    }
}
