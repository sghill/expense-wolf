package net.sghill.wolf;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.javafunk.funk.Eagerly;
import org.javafunk.funk.functors.Predicate;
import org.joda.time.LocalDate;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.javafunk.funk.Eagerly.max;

@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class ExpenseReports {
    private final Set<ExpenseReport> expenseReports;

    public SortedSet<ExpenseReport> getPaidReportsFor(final long employeeId) {
        SortedSet<ExpenseReport> reportsPaidToEmployee = new TreeSet<ExpenseReport>(Eagerly.filter(expenseReports, paidTo(employeeId)));
        log.info("Found [{}] expense reports paid to employee [{}]", reportsPaidToEmployee.size(), employeeId);
        return reportsPaidToEmployee;
    }

    public Set<ExpenseReport> getMostRecentPaidReportsFor(final long employeeId) {
        final SortedSet<ExpenseReport> allPaidReports = getPaidReportsFor(employeeId);
        return new TreeSet<ExpenseReport>(Eagerly.filter(allPaidReports, by(max(allPaidReports).getDatePaid())));
    }

    private static Predicate<ExpenseReport> by(final LocalDate mostRecentDatePaid) {
        return new Predicate<ExpenseReport>() {
            @Override public boolean evaluate(ExpenseReport expenseReport) {
                return expenseReport.getDatePaid().equals(mostRecentDatePaid);
            }
        };
    }

    private static Predicate<ExpenseReport> paidTo(final long employeeId) {
        return new Predicate<ExpenseReport>() {
            @Override public boolean evaluate(ExpenseReport expenseReport) {
                return expenseReport.getEmployeeId().equals(employeeId);
            }
        };
    }

    public String describeMostRecentPaidReportsFor(long employeeId) {
        StringBuilder sb = new StringBuilder();
        for(ExpenseReport report : getMostRecentPaidReportsFor(employeeId)) {
            sb.append(report.describe());
        }
        return sb.toString();
    }

    public String describePaidReportsFor(long employeeId) {
        StringBuilder sb = new StringBuilder();
        for(ExpenseReport report : getPaidReportsFor(employeeId)) {
            sb.append(report.describe());
        }
        return sb.toString();
    }
}
