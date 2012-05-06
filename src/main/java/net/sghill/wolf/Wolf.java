package net.sghill.wolf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static java.io.File.separator;
import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static net.sghill.wolf.CommandLineOptions.*;

@Slf4j
public final class Wolf {
    private static CommandLine commandLine = null;
    private static final Options options = initializeOptions();
    private static final String filePath = format("%s%sexpense-wolf.properties", getProperty("user.home"), separator);

    public static void main(String[] args) {
        try {
            commandLine = new GnuParser().parse(options, args);
        } catch (ParseException e) {
            fatalError("Could not parse input. Exiting...");
        }

        if(option(HELP)) {
            showHelp();
        }

        if(noOption(FILE)) {
            fatalError("file not specified. Expense report xls file required.");
        }

        Long employeeId;
        if(noOption(EMPLOYEE_ID)) {
            log.info("No employee id specified. Pulling employee id from home directory.");
            employeeId = new PropertiesFileReader(filePath).getAsLong("employee.id");
            if(employeeId == null) {
                fatalError("[{}] not found. Specify -e or --employee-id so we know who to look for!", filePath);
            }
        } else {
            employeeId = valueOf(givenValueFor(EMPLOYEE_ID));
        }

        if(option(SAVE)) {
            if (noOption(EMPLOYEE_ID)) {
                fatalError("Save requested, but --employee-id missing");
            }
            try {
                Properties props = new Properties();
                props.setProperty("employee.id", givenValueFor(EMPLOYEE_ID));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                props.store(writer, "");
                writer.close();
            } catch (IOException e) {
                fatalError("Could not save employee id to [{}]", filePath);
            }
            log.info("Saved employee id to [{}]", filePath);
        }

        ExpenseReports allExpenseReports = new Reader(givenValueFor(FILE)).getAllExpenseReports();

        if(option(ALL_REPORTS)) {
            System.out.println(allExpenseReports.describePaidReportsFor(employeeId));
        } else {
            System.out.println(allExpenseReports.describeMostRecentPaidReportsFor(employeeId));
        }
    }

    private static String givenValueFor(CommandLineOptions option) {
        return commandLine.getOptionValue(option.getShortCode());
    }

    private static void fatalError(String message, Object... objects) {
        log.error(message, objects);
        showHelp();
    }

    private static boolean option(CommandLineOptions option) {
        return commandLine.hasOption(option.getShortCode());
    }

    private static boolean noOption(CommandLineOptions option) {
        return !option(option);
    }

    private static Options initializeOptions() {
        Options options = new Options();
        for(CommandLineOptions opt : CommandLineOptions.values()) {
            options.addOption(opt.get());
        }
        return options;
    }

    private static void showHelp() {
        new HelpFormatter().printHelp("java -jar wolf.jar [OPTIONS] --file [absolute path to xls]", options);
        exit(1);
    }

}
