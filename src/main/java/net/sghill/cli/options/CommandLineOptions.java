package net.sghill.cli.options;

import org.apache.commons.cli.Option;

public interface CommandLineOptions {
    Option get();
    String getShortCode();
}
