package net.sghill.wolf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.cli.Option;

@AllArgsConstructor
public enum CommandLineOptions {
    EMPLOYEE_ID("e", "employee-id", true, "employee id number. can be saved with -s"),
    FILE("f", "file", true, "absolute path to expense report xls"),
    ALL_REPORTS("a", "all", false, "get every expense report you've submitted"),
    SAVE("s", "save-id", false, "saves a file in home directory with employee id"),
    HELP("h", "help", false, "print this message");

    @Getter
    private final String shortCode;
    private final String longCode;
    private final boolean hasArgument;
    private final String description;

    public Option get() {
        return new Option(shortCode, longCode, hasArgument, description);
    }
}
