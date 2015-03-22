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
        System.out.println("BEG Undo cmd "+command.getClass().getName());
		command.undo();
        System.out.println("END Undo cmd " + command.getClass().getName());
		this.undoStack.push(command);
	}
	
	public void redo() {
		if (this.undoStack.empty()) {
			return;
		}
		
		ICommand command = this.undoStack.pop();
        System.out.println("BEG Redo cmd "+command.getClass().getName());
		command.execute(this.graph);
        System.out.println("END Redo cmd " + command.getClass().getName());
		this.commandStack.push(command);
	}
	
	public void executeCommand(ICommand command) {
        System.out.println("BEG Execute cmd "+command.getClass().getName());
        command.execute(this.graph);
        System.out.println("END Execute cmd " + command.getClass().getName());

        this.commandStack.push(command);

        if (!this.undoStack.empty()) {
            this.undoStack.clear();
        }
	}
}
