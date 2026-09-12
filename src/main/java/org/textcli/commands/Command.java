package org.textcli.commands;

public interface Command {
    void execute(String[] args) throws CommandException;
}
