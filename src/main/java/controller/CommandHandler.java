package controller;

import java.util.Stack;

public class CommandHandler {
	private Stack<ICommand> commandStack;
	private Stack<ICommand> undoStack;
    private CommandContext context;
	
	public CommandHandler(CommandContext context) {
		this.commandStack = new Stack<ICommand>();
		this.undoStack = new Stack<ICommand>();
        this.context = context;
	}
	
	public void undo() {
		if (this.commandStack.empty()) {
			return;
		}
		
		ICommand command = this.commandStack.pop();
		command.undo();
		this.undoStack.push(command);
	}
	
	public void redo() {
		if (this.undoStack.empty()) {
			return;
		}
		
		ICommand command = this.undoStack.pop();
		command.execute();
		this.commandStack.push(command);
	}
	
	public void executeCommand(Command command) {
        command.setContext(context);
		command.execute();

        if (command.isUndoable()) {
            this.commandStack.push(command);

            if (!this.undoStack.empty()) {
                this.undoStack.clear();
            }
        }
	}
}
