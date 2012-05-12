package net.sghill.cli;

import org.javafunk.funk.monads.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.monads.Option.some;
import static org.junit.Assert.assertThat;

public class PropertyTest {

    @Test
    public void shouldExposeKey() {
        Property property = new Property("some.prop", null);
        assertThat(property.getKey(), is("some.prop"));
    }

    @Test
    public void asLong_shouldReturnNoneIfValueNotALong() {
        for(String value : iterableWith(null, "", "asdf", "35.53")) {
            Property property = new Property("some.prop", value);
            assertThat(property.asLong(), is(Option.<Long>none()));
        }
    }

    @Test
    public void asLong_shouldReturnSomeLongIfParseable() {
        Property property = new Property("some.prop", "90000");
        assertThat(property.asLong(), is(some(90000L)));
    }

    @Test
    public void asInteger_shouldReturnNoneIfValueNotAnInteger() {
        for(String value : iterableWith(null, "", "asdf", "35.53")) {
            Property property = new Property("some.prop", value);
            assertThat(property.asInteger(), is(Option.<Integer>none()));
        }
    }

    @Test
    public void asInteger_shouldReturnSomeIntegerIfParseable() {
        Property property = new Property("some.prop", "12");
        assertThat(property.asInteger(), is(some(12)));
    }

    @Test
    public void asString_shouldReturnNoneIfValueNullOrEmpty() {
        for(String value : iterableWith(null, "")) {
            Property property = new Property("some.prop", value);
            assertThat(property.asString(), is(Option.<String>none()));
        }
    }

    @Test
    public void asString_shouldReturnSomeStringIfContainsSomething() {
        Property property = new Property("some.prop", "localhost");
        assertThat(property.asString(), is(some("localhost")));
    }


}
