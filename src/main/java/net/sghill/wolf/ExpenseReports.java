package net.sghill.wolf;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.javafunk.funk.functors.Predicate;
import org.joda.time.LocalDate;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableSortedSet;
import static org.javafunk.funk.Eagerly.filter;
import static org.javafunk.funk.Eagerly.max;

@Slf4j
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class ExpenseReports {
    private final Set<ExpenseReport> expenseReports;

    public SortedSet<ExpenseReport> getPaidReportsFor(long employeeId) {
        SortedSet<ExpenseReport> reportsPaidToEmployee = new TreeSet<ExpenseReport>(filter(expenseReports, paidTo(employeeId)));
        log.info("Found [{}] expense reports paid to employee [{}]", reportsPaidToEmployee.size(), employeeId);
        return unmodifiableSortedSet(reportsPaidToEmployee);
    }

    public SortedSet<ExpenseReport> getMostRecentPaidReportsFor(long employeeId) {
        final SortedSet<ExpenseReport> allPaidReports = getPaidReportsFor(employeeId);
        if(allPaidReports.isEmpty()) {
            return allPaidReports;
        }
        TreeSet<ExpenseReport> recentExpenseReports = new TreeSet<ExpenseReport>(filter(allPaidReports, by(max(allPaidReports).getDatePaid())));
        log.info("Found [{}] recent expense reports paid to employee [{}]", recentExpenseReports.size(), employeeId);
        return unmodifiableSortedSet(recentExpenseReports);
    }

    public String describeMostRecentPaidReportsFor(long employeeId) {
        return describeTheSituationFor(getMostRecentPaidReportsFor(employeeId));
    }

    public String describePaidReportsFor(long employeeId) {
        return describeTheSituationFor(getPaidReportsFor(employeeId));
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

    private String describeTheSituationFor(SortedSet<ExpenseReport> reports) {
        return reports.isEmpty() ? describeNoReports() : describe(reports);
    }

    private static String describe(SortedSet<ExpenseReport> paidReports) {
        StringBuilder sb = new StringBuilder();
        for(ExpenseReport report : paidReports) {
            sb.append(report.describe());
        }
        return sb.toString();
    }

    private static String describeNoReports() {
        return "\t\t\t\tNo reports found\n";
    }
}
