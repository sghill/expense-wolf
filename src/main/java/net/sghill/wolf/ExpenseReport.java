package net.sghill.wolf;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;

import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static net.sghill.cli.Literals.runtimeError;
import static org.joda.time.format.DateTimeFormat.forPattern;

@Slf4j
@ToString
@EqualsAndHashCode
public final class ExpenseReport implements Comparable<ExpenseReport> {
    @Getter private final Long employeeId;
    @Getter private final LocalDate datePaid;
    @Getter private final String expenseId;

    public ExpenseReport(String employeeId, String datePaid, String expenseId) {
        this.employeeId = asLong(employeeId);
        this.datePaid = asLocalDate(datePaid);
        this.expenseId = expenseId;
        log.trace("Created {}", toString());
    }

    public String describe() {
        return format("\t\t\t\treport %s on %s\n", expenseId, datePaid.toString("MMM dd, yyyy"));
    }

    @Override
    public int compareTo(ExpenseReport o) {
        if(datePaid.isBefore(o.getDatePaid())) { return -1; }
        if (datePaid.isAfter(o.getDatePaid())) { return 1; }
        return expenseId.compareTo(o.getExpenseId());
    }

    private static LocalDate asLocalDate(String datePaid) {
        try {
            return forPattern("M/d/yy").parseLocalDate(datePaid);
        } catch (Exception e) {
            throw runtimeError(e, "Could not parse date paid [{}]", datePaid);
        }
    }

    private static Long asLong(String employeeId) {
        try {
            return valueOf(employeeId);
        } catch (NumberFormatException e) {
            throw runtimeError(e, "Could not parse employee id [{}].", employeeId);
        }
    }
}
