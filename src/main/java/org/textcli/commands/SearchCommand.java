package org.textcli.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SearchCommand implements Command {
    @Override
    public void execute(String[] args) throws CommandException {
        if (args.length != 2) {
            throw new CommandException("usage: search <pattern> <file>");
        }

        Pattern regex;
        try {
            regex = Pattern.compile(args[0]);
        } catch (PatternSyntaxException e) {
            throw new CommandException("invalid pattern: " + e.getMessage());
        }

        Path path = Path.of(args[1]);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new CommandException("cannot read file: " + path);
        }

        for (int i = 0; i < lines.size(); i++) {
            if (regex.matcher(lines.get(i)).find()) {
                System.out.printf("%d: %s%n", i + 1, lines.get(i));
            }
        }
    }
}
