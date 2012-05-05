package net.sghill.wolf;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;

import static java.lang.Long.valueOf;
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
        log.debug("Created {}", toString());
    }

    private static LocalDate asLocalDate(String datePaid) {
        try {
            return forPattern("M/d/yy").parseLocalDate(datePaid);
        } catch (Exception e) {
            log.error("Could not parse date paid [{}]", datePaid);
            throw new RuntimeException(e);
        }
    }

    private static Long asLong(String employeeId) {
        try {
            return valueOf(employeeId);
        } catch (NumberFormatException e) {
            log.error("Could not parse employee id [{}].", employeeId);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(ExpenseReport o) {
        if(datePaid.isBefore(o.getDatePaid())) { return -1; }
        if(datePaid.isAfter(o.getDatePaid())) { return 1; }
        return 0;
    }
}
