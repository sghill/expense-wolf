package net.sghill.wolf;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.javafunk.funk.functors.Mapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jxl.Workbook.getWorkbook;
import static org.javafunk.funk.Lazily.map;
import static org.javafunk.funk.Literals.iterableFrom;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Sets.union;

public final class Reader {
    private final Workbook workbook;

    public Reader(String classPathFileName) {
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(classPathFileName);
            workbook = resourceAsStream == null ? getWorkbook(new FileInputStream(classPathFileName)) : getWorkbook(resourceAsStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getHeaders() {
        return listFrom(map(iterableFrom(workbook.getSheet(0).getRow(0)), toContents()));
    }

    public ExpenseReports getAllExpenseReports() {
        return new ExpenseReports(union(map(iterableFrom(workbook.getSheets()), toExpenseReports())));
    }

    private Mapper<Sheet, Set<ExpenseReport>> toExpenseReports() {
        return new Mapper<Sheet, Set<ExpenseReport>>() {
            @Override
            public Set<ExpenseReport> map(Sheet sheet) {
                return getAllExpenseReportsOnSheet(sheet.getName());
            }
        };
    }

    private Set<ExpenseReport> getAllExpenseReportsOnSheet(String name) {
        Set<ExpenseReport> reports = new HashSet<ExpenseReport>();
        for(int i = 1; i < workbook.getSheet(name).getRows(); i++) {
            Cell[] row = workbook.getSheet(name).getRow(i);
            if(row.length != 0) {
                reports.add(new ExpenseReport(row[0].getContents(), row[1].getContents(), row[2].getContents()));
            }
        }
        return reports;
    }

    private static Mapper<Cell, String> toContents() {
        return new Mapper<Cell, String>() {
            @Override
            public String map(Cell cell) {
                return cell.getContents();
            }
        };
    }
}
