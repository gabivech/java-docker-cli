package org.textcli.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CountCommand implements Command {
    @Override
    public void execute(String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("usage: count <file>");
        }

        Path path = Path.of(args[0]);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new CommandException("cannot read file: " + path);
        }

        long lineCount = lines.size();
        long wordCount = lines.stream()
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                .filter(w -> !w.isEmpty())
                .count();
        long charCount = lines.stream().mapToLong(String::length).sum();

        System.out.printf("lines: %d%nwords: %d%nchars: %d%n", lineCount, wordCount, charCount);
    }
}
