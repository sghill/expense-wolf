package net.sghill.cli.options;

import lombok.Delegate;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sghill.cli.Value;

@ToString
@EqualsAndHashCode
public final class Argument {
    @Delegate private final Value value;

    public Argument(String value) {
        this.value = new Value(value);
    }
}
