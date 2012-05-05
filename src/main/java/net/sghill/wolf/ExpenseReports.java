package net.sghill.wolf;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.javafunk.funk.functors.Predicate;
import org.joda.time.LocalDate;

import java.util.Set;

import static org.javafunk.funk.Eagerly.max;
import static org.javafunk.funk.Lazily.filter;
import static org.javafunk.funk.Literals.setFrom;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class ExpenseReports {
    private final Set<ExpenseReport> expenseReports;

    public Set<ExpenseReport> getPaidReportsFor(final long employeeId) {
        return setFrom(filter(expenseReports, paidTo(employeeId)));
    }

    public Set<ExpenseReport> getMostRecentPaidReportsFor(final long employeeId) {
        final Set<ExpenseReport> allPaidReports = getPaidReportsFor(employeeId);
        return setFrom(filter(allPaidReports, by(max(allPaidReports).getDatePaid())));
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
}
