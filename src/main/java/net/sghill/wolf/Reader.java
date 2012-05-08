package net.sghill.wolf;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.slf4j.Slf4j;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jxl.Workbook.getWorkbook;
import static net.sghill.wolf.Utilities.runtimeError;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.javafunk.funk.Eagerly.all;
import static org.javafunk.funk.Lazily.map;
import static org.javafunk.funk.Literals.*;
import static org.javafunk.funk.Sets.union;

@Slf4j
public final class Reader {
    private final Workbook workbook;

    public Reader(String fileName) {
        try {
            InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (resource == null) {
                resource = new FileInputStream(fileName);
                log.info("File not found on classpath, attempting to locate on filesystem");
            }
            workbook = getWorkbook(resource);
        } catch (Exception e) {
            throw runtimeError(e, "File not found. Looked on classpath and filesystem for\n\t\t[{}]\n\n", fileName);
        }
        log.info("Located [{}]", fileName);
    }

    public List<String> getHeaders() {
        return listFrom(map(iterableFrom(workbook.getSheet(0).getRow(0)), toContents()));
    }

    public ExpenseReports getAllExpenseReports() {
        Sheet[] sheets = workbook.getSheets();
        log.debug("Found [{}] sheets in spreadsheet", sheets.length);
        return new ExpenseReports(union(map(iterableFrom(sheets), toExpenseReports())));
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
        int rows = workbook.getSheet(name).getRows();
        log.debug("Found [{}] rows in sheet [{}]", rows, name);
        for(int i = 1; i < rows; i++) {
            Collection<Cell> rowCells = collectionFrom(workbook.getSheet(name).getRow(i));
            if(!rowCells.isEmpty() && all(rowCells, containText())) {
                List<String> arguments = listFrom(map(rowCells, toContents()));
                reports.add(new ExpenseReport(arguments.get(0), arguments.get(1), arguments.get(2)));
            }
        }
        return reports;
    }

    private static Predicate<Cell> containText() {
        return new Predicate<Cell>() {
            @Override public boolean evaluate(Cell cell) {
                return isNotBlank(cell.getContents());
            }
        };
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
