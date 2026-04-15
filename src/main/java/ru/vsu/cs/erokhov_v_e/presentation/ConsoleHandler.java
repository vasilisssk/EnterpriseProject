package ru.vsu.cs.erokhov_v_e.presentation;

import ru.vsu.cs.erokhov_v_e.presentation.command.CommandResult;

import java.util.Scanner;

public class ConsoleHandler {

    private final CommandDispatcher dispatcher;

    public ConsoleHandler(CommandDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Система управления предприятием. Введите help");
        CommandResult result;

        do {
            System.out.print("> ");
            String input = scanner.nextLine();

            result = dispatcher.dispatch(input);
        } while (result != CommandResult.EXIT);

        scanner.close();
    }
}
