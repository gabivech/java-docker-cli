package org.textcli.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReplaceCommand implements Command {
    @Override
    public void execute(String[] args) throws CommandException {
        if (args.length != 3) {
            throw new CommandException("usage: replace <pattern> <replacement> <file>");
        }

        Path path = Path.of(args[2]);
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            throw new CommandException("cannot read file: " + path);
        }

        String result;
        try {
            result = content.replaceAll(args[0], args[1]);
        } catch (IllegalArgumentException e) {
            throw new CommandException("invalid pattern or replacement: " + e.getMessage());
        }

        System.out.print(result);
    }
}
