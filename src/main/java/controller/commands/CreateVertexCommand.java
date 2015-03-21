package controller.commands;

import controller.ICommand;
import model.Graph;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertexCommand implements ICommand {
    private Vertex vertex;
    private Graph graph;

    public CreateVertexCommand() {
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
