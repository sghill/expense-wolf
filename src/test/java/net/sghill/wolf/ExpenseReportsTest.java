package net.sghill.wolf;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.javafunk.funk.matchers.Matchers.hasOnlyItemsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ExpenseReportsTest {
    private ExpenseReports expenseReports;

    @Before
    public void setUp() {
        expenseReports = new Reader("sample.xls").getAllExpenseReports();
    }

    @Test
    public void shouldGetAllExpenseIdsForEmployee() {
        assertThat(expenseReports.getPaidReportsFor(5000L), hasOnlyItemsInAnyOrder(
                new ExpenseReport("5000", "9/23/2011", "1239"),
                new ExpenseReport("5000", "10/7/2011", "3995"),
                new ExpenseReport("5000", "10/21/2011", "5684")));
    }

    @Test
    public void shouldGetMostRecentExpenseIdsForEmployee() {
        assertThat(expenseReports.getMostRecentPaidReportsFor(7000L), hasOnlyItemsInAnyOrder(
                new ExpenseReport("7000", "10/21/2011", "1200"),
                new ExpenseReport("7000", "10/21/2011", "8600")));
    }

    @Test
    public void shouldDescribeMostRecentExpenseIdsForEmployee() {
        assertThat(expenseReports.describeMostRecentPaidReportsFor(7000L), containsString("report 1200 on Oct 21, 2011"));
        assertThat(expenseReports.describeMostRecentPaidReportsFor(7000L), containsString("report 8600 on Oct 21, 2011"));
    }

    @Test
    public void shouldDescribeAllExpenseIdsForEmployee() {
        assertThat(expenseReports.describePaidReportsFor(5000L), containsString("report 1239 on Sep 23, 2011"));
        assertThat(expenseReports.describePaidReportsFor(5000L), containsString("report 3995 on Oct 07, 2011"));
        assertThat(expenseReports.describePaidReportsFor(5000L), containsString("report 5684 on Oct 21, 2011"));
    }
}
