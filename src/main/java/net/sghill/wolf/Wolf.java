package net.sghill.wolf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static ch.qos.logback.classic.Level.DEBUG;
import static ch.qos.logback.classic.Level.OFF;
import static java.io.File.separator;
import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static net.sghill.wolf.CommandLineOptions.*;

@Slf4j
public final class Wolf {

    public static void main(String[] args) {
        Options options = createOptions();

        CommandLineParser parser = new GnuParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            log.error("Could not parse input. Exiting...");
            showHelp(options);
            exit(1);
        }

        if(commandLine.hasOption(HELP.getShortCode())) {
            showHelp(options);
            exit(1);
        }

        if(commandLine.hasOption(VERBOSE.getShortCode())) {
            ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) Wolf.log;
            logger.setLevel(DEBUG);
        }

        if(commandLine.hasOption(QUIET.getShortCode())) {
            ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) Wolf.log;
            logger.setLevel(OFF);
        }

        if(!commandLine.hasOption(FILE.getShortCode())) {
            log.error("file not specified. Expense report xls file required.");
            showHelp(options);
            exit(1);
        }

        String filePath = format("%s%sexpense-wolf.properties", getProperty("user.home"), separator);
        String employeeId = commandLine.getOptionValue("e");
        if(!commandLine.hasOption(EMPLOYEE_ID.getShortCode())) {
            log.info("No employee id specified. Attemping to pull employee id from home directory.");
            try {
                Properties props = new Properties();
                props.load(new FileReader(filePath));
                employeeId = props.getProperty("employee.id");
            } catch (IOException e) {
                log.error("[{}] not found. Specify -e or --employee-id so we know who to look for!", filePath);
                showHelp(options);
                exit(1);
            }
        }

        if(commandLine.hasOption("s") && commandLine.hasOption("e")) {
            log.info("Saving employee id to [{}]", filePath);
            try {
                Properties props = new Properties();
                props.setProperty("employee.id", commandLine.getOptionValue("e"));
                props.store(new FileWriter(filePath), "");
            } catch (IOException e) {
                log.error("Could not save employee id to [{}]", filePath);
                exit(1);
            }
        }

        String filename = commandLine.getOptionValue(FILE.getShortCode());
        ExpenseReports allExpenseReports = new Reader(filename).getAllExpenseReports();
        if(commandLine.hasOption("a")) {
            System.out.println(allExpenseReports.describePaidReportsFor(valueOf(employeeId)));
        } else {
            System.out.println(allExpenseReports.describeMostRecentPaidReportsFor(valueOf(employeeId)));
        }

    }

    private static Options createOptions() {
        Options options = new Options();
        for(CommandLineOptions opt : CommandLineOptions.values()) {
            options.addOption(opt.getOption());
        }
        return options;
    }

    private static void showHelp(Options options) {
        new HelpFormatter().printHelp("java -jar wolf.jar [OPTIONS] --file [absolute path to xls]", options);
    }

}
