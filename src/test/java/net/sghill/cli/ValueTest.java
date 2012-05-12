package net.sghill.cli;

import org.javafunk.funk.monads.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.some;
import static org.junit.Assert.assertThat;

public class ValueTest {

    @Test
    public void asLong_shouldReturnNoneIfValueNotALong() {
        for(String value : iterableWith(null, "", "asdf", "35.53")) {
            Value val = new Value(value);
            assertThat(val.asLong(), is(Option.<Long>none()));
        }
    }

    @Test
    public void asLong_shouldReturnSomeLongIfParseable() {
        Value val = new Value("90000");
        assertThat(val.asLong(), is(some(90000L)));
    }

    @Test
    public void asInteger_shouldReturnNoneIfValueNotAnInteger() {
        for(String value : iterableWith(null, "", "asdf", "35.53")) {
            Value val = new Value(value);
            assertThat(val.asInteger(), is(Option.<Integer>none()));
        }
    }

    @Test
    public void asInteger_shouldReturnSomeIntegerIfParseable() {
        Value val = new Value("12");
        assertThat(val.asInteger(), is(some(12)));
    }

    @Test
    public void asString_shouldReturnNoneIfValueNullOrEmpty() {
        for(String value : iterableWith(null, "")) {
            Value val = new Value(value);
            assertThat(val.asString(), is(Option.<String>none()));
        }
    }

    @Test
    public void asString_shouldReturnSomeStringIfContainsSomething() {
        Value val = new Value("localhost");
        assertThat(val.asString(), is(some("localhost")));
    }
}
