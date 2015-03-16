package controller.commands;

import controller.ICommand;
import model.Graph;
import model.Vertex;

import java.awt.event.ActionEvent;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertex implements ICommand {
    private Vertex vertex;
    private Graph graph;

    public CreateVertex() {
        this.vertex = new Vertex();
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.graph.addVertex(this.vertex);
    }

    @Override
    public void undo() {
        this.graph.removeVertex(this.vertex);
    }
}
