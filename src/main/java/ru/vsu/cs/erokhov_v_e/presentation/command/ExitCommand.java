package ru.vsu.cs.erokhov_v_e.presentation.command;

public class ExitCommand implements Command {

    @Override
    public CommandResult execute(String[] args) {
        return CommandResult.EXIT;
    }

    @Override
    public String description() {
        return "exit — выйти из программы";
    }
}
