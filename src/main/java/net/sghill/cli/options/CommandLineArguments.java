package net.sghill.cli.options;

import lombok.AllArgsConstructor;
import org.apache.commons.cli.CommandLine;

@AllArgsConstructor
public final class CommandLineArguments {
    private final CommandLine commandLine;

    public boolean include(CommandLineOptions option) {
        return commandLine.hasOption(option.getShortCode());
    }

    public boolean doNotInclude(CommandLineOptions option) {
        return !include(option);
    }

    public Argument extract(CommandLineOptions option) {
        return new Argument(commandLine.getOptionValue(option.getShortCode()));
    }
}
