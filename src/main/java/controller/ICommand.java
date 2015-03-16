package controller;

import model.Graph;

public interface ICommand {
	void undo();
    void execute(Graph graph);
}
