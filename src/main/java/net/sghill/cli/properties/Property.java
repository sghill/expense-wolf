package net.sghill.cli.properties;

import lombok.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.sghill.cli.Value;

@ToString
@EqualsAndHashCode
public final class Property {
    @Getter private final String name;
    @Delegate private final Value value;

    public Property(String name, String value) {
        this.name = name;
        this.value = new Value(value);
    }
}
