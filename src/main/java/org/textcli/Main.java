package org.textcli;

import java.util.Arrays;
import java.util.Map;

import org.textcli.commands.Command;
import org.textcli.commands.CommandException;
import org.textcli.commands.CountCommand;
import org.textcli.commands.HistoryCommand;
import org.textcli.commands.ReplaceCommand;
import org.textcli.commands.SearchCommand;

public class Main {
    private static final Map<String, Command> COMMANDS = Map.of(
            "count", new CountCommand(),
            "search", new SearchCommand(),
            "replace", new ReplaceCommand(),
            "history", new HistoryCommand()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }

        String commandName = args[0];
        Command command = COMMANDS.get(commandName);
        if (command == null) {
            System.err.println("Unknown command: " + commandName);
            printUsage();
            System.exit(1);
            return;
        }

        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
        try {
            command.execute(commandArgs);
        } catch (CommandException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
            return;
        }

        if (!commandName.equals("history")) {
            HistoryStore.append(commandName, commandArgs);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: textcli <command> [args]");
        System.out.println("Commands:");
        System.out.println("  count <file>                    Count lines, words and characters");
        System.out.println("  search <pattern> <file>         Print lines matching a regex pattern");
        System.out.println("  replace <pattern> <repl> <file> Replace regex matches and print result");
        System.out.println("  history [limit]                 Show recently run commands");
    }
}
