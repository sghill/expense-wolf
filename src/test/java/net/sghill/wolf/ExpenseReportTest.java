package net.sghill.wolf;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExpenseReportTest {

    @Test
    public void shouldDescribeInAHumanReadableWay() {
        ExpenseReport expenseReport = new ExpenseReport("10000", "12/11/2011", "123456");
        assertThat(expenseReport.describe(), containsString("report 123456 on Dec 11, 2011"));
    }

    @Test
    public void shouldOrderExpenseReportsByDate() {
        ExpenseReport first = new ExpenseReport("10000", "12/10/2011", "100000");
        ExpenseReport second = new ExpenseReport("10000", "12/11/2011", "123456");
        assertThat(first.compareTo(second), is(-1));
    }

    @Test
    public void shouldOrderExpenseReportsByDateThenReportNumber() {
        ExpenseReport first = new ExpenseReport("10000", "12/11/2011", "123455");
        ExpenseReport second = new ExpenseReport("10000", "12/11/2011", "123456");
        assertThat(first.compareTo(second), is(-1));
    }

    @Test
    public void shouldOrderExpenseReportsOnlyByDateThenReportNumber() {
        ExpenseReport first = new ExpenseReport("10000", "12/11/2011", "123456");
        ExpenseReport second = new ExpenseReport("10000", "12/11/2011", "123456");
        assertThat(first.compareTo(second), is(0));
    }
}
