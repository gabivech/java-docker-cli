package org.textcli.commands;

import java.util.List;

import org.textcli.HistoryStore;

public class HistoryCommand implements Command {
    private static final int DEFAULT_LIMIT = 10;

    @Override
    public void execute(String[] args) throws CommandException {
        int limit = DEFAULT_LIMIT;
        if (args.length == 1) {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new CommandException("limit must be a number");
            }
        } else if (args.length > 1) {
            throw new CommandException("usage: history [limit]");
        }

        List<String> entries = HistoryStore.recent(limit);
        if (entries.isEmpty()) {
            System.out.println("No history yet.");
            return;
        }
        entries.forEach(System.out::println);
    }
}
