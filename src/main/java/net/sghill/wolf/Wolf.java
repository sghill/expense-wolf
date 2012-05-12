package net.sghill.wolf;

import lombok.extern.slf4j.Slf4j;
import net.sghill.cli.options.CommandLineArguments;
import org.apache.commons.cli.*;

import static java.io.File.separator;
import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static net.sghill.cli.Literals.runtimeError;
import static net.sghill.cli.properties.PropertiesFileReader.readPropertiesFrom;
import static net.sghill.cli.properties.PropertiesFileWriter.writePropertiesTo;
import static net.sghill.wolf.CommandLineOptions.*;
import static org.javafunk.funk.Literals.mapWith;

@Slf4j
public final class Wolf {
    private static final Options options = initializeOptions();
    private static final String filePath = format("%s%sexpense-wolf.properties", getProperty("user.home"), separator);

    public static void main(String[] rawArguments) {
        CommandLine commandLine = null;
        try {
            commandLine = new GnuParser().parse(options, rawArguments);
        } catch (ParseException e) {
            throw runtimeError("Could not parse input. Exiting...");
        }
        CommandLineArguments args = new CommandLineArguments(commandLine);

        if(args.include(HELP)) {
            showHelpAndExit();
        }

        if(args.doNotInclude(FILE)) {
            throw runtimeError("file not specified. Expense report xls file required.");
        }

        Long employeeId;
        if(args.doNotInclude(EMPLOYEE_ID)) {
            log.info("No employee id specified. Pulling employee id from home directory.");
            employeeId = readPropertiesFrom(filePath).get("employee.id").asLong()
                    .getOrThrow(runtimeError("employee.id not found in [{}]. Specify -e or --employee-id so we know who to look for!", filePath));
        } else {
            employeeId = args.extract(EMPLOYEE_ID).asLong().get();
        }

        if(args.include(SAVE)) {
            if (args.doNotInclude(EMPLOYEE_ID)) {
                throw runtimeError("Save requested, but --employee-id missing");
            }
            writePropertiesTo(filePath).replaceContentsWith(mapWith("employee.id", employeeId));
        }

        ExpenseReports allExpenseReports = new Reader(args.extract(FILE).asString().get()).getAllExpenseReports();

        if(args.include(ALL_REPORTS)) {
            System.out.println(allExpenseReports.describePaidReportsFor(employeeId));
        } else {
            System.out.println(allExpenseReports.describeMostRecentPaidReportsFor(employeeId));
        }
    }

    private static Options initializeOptions() {
        Options options = new Options();
        for(CommandLineOptions opt : CommandLineOptions.values()) {
            options.addOption(opt.get());
        }
        return options;
    }

    private static void showHelpAndExit() {
        new HelpFormatter().printHelp("java -jar wolf.jar [OPTIONS] --file [absolute path to xls]", options);
        exit(1);
    }

}
