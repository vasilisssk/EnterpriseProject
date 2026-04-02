package ru.vsu.cs.erokhov_v_e.presentation.command;

public interface Command {
    CommandResult execute(String[] args);
    String getName();
    String description();
}
