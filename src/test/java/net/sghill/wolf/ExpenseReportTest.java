package net.sghill.wolf;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ExpenseReportTest {

    @Test
    public void shouldDescribeInAHumanReadableWay() {
        ExpenseReport expenseReport = new ExpenseReport("10000", "12/11/2011", "123456");
        assertThat(expenseReport.describe(), containsString("report 123456 on December 11, 2011"));
    }
}
