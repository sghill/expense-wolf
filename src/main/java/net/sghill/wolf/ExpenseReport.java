package net.sghill.wolf;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

@ToString
@EqualsAndHashCode
public final class ExpenseReport implements Comparable<ExpenseReport> {
    @Getter private final Long employeeId;
    @Getter private final LocalDate datePaid;
    @Getter private final String expenseId;

    public ExpenseReport(String employeeId, String datePaid, String expenseId) {
        this.employeeId = Long.valueOf(employeeId);
        this.datePaid = DateTimeFormat.forPattern("M/d/yyyy").parseLocalDate(datePaid);
        this.expenseId = expenseId;
    }

    @Override
    public int compareTo(ExpenseReport o) {
        if(datePaid.isBefore(o.getDatePaid())) { return -1; }
        if(datePaid.isAfter(o.getDatePaid())) { return 1; }
        return 0;
    }
}
