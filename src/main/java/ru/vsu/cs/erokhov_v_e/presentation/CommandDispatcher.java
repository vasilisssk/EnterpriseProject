package ru.vsu.cs.erokhov_v_e.presentation;

import ru.vsu.cs.erokhov_v_e.presentation.command.Command;
import ru.vsu.cs.erokhov_v_e.presentation.command.CommandResult;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandDispatcher {

    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void register(Command command) {
        if (commands.containsKey(command.getName())) {
            throw new IllegalArgumentException(
                    "Команда с именем '" + command.getName() + "' уже зарегистрирована"
            );
        }
        commands.put(command.getName(), command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public CommandResult dispatch(String input) {
        if (input == null || input.isBlank()) return CommandResult.CONTINUE;

        String[] parts = input.trim().split(" ", 2);
        String commandName = parts[0].toLowerCase();
        String[] args = parts.length > 1
                ? parts[1].split(",")
                : new String[]{};

        Command command = commands.get(commandName);

        if (command != null) {
            return command.execute(args);
        } else {
            System.out.println("Неизвестная команда. Введите help.");
        }
        return CommandResult.CONTINUE;
    }
}
