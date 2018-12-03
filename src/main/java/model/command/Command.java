package model.command;

public interface Command {
    void execute();
    void undo();
}
