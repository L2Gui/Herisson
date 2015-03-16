package controller;

import model.Graph;

import java.util.Stack;

public class CommandHandler {
	private Stack<ICommand> commandStack;
	private Stack<ICommand> undoStack;
    private Graph graph;
	
	public CommandHandler(Graph graph) {
		this.commandStack = new Stack<ICommand>();
		this.undoStack = new Stack<ICommand>();
        this.graph = graph;
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
		command.execute(this.graph);
		this.commandStack.push(command);
	}
	
	public void executeCommand(ICommand command) {
		command.execute(this.graph);

        this.commandStack.push(command);

        if (!this.undoStack.empty()) {
            this.undoStack.clear();
        }
	}
}
