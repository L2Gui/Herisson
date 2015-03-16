package controller;

public interface ICommand {
    boolean isUndoable();
	void execute();
	void undo();
}
