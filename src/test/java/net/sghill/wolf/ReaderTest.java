package net.sghill.wolf;

import org.junit.Before;
import org.junit.Test;

import static org.javafunk.funk.matchers.IterableMatchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertThat;

public class ReaderTest {
    private Reader reader;

    @Before
    public void setUp() {
        reader = new Reader("sample.xls");
    }

    @Test
    public void shouldBeAbleToGetHeaders() {
        assertThat(reader.getHeaders(), hasOnlyItemsInOrder("Employee ID", "Date Paid", "Expense ID"));
    }
}
