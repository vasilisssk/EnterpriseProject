package ru.vsu.cs.erokhov_v_e.presentation.command;

import java.util.Map;

public class HelpCommand implements Command {

    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public CommandResult execute(String[] args) {
        System.out.println("Доступные команды:");
        commands.forEach((name, command) ->
                System.out.println("  " + command.description()));
        return CommandResult.CONTINUE;
    }

    @Override
    public String description() {
        return "help — показать список команд";
    }
}
