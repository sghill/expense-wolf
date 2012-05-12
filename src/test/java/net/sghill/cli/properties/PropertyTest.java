package net.sghill.cli.properties;

import net.sghill.cli.properties.Property;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.javafunk.funk.Literals.iterableWith;
import static org.junit.Assert.assertThat;

public class PropertyTest {

    @Test
    public void shouldExposeKey() {
        Property property = new Property("some.prop", null);
        assertThat(property.getName(), is("some.prop"));
    }
}
