package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertexCommand implements ICommand {
    private Vertex vertex;
    private Graph graph;

    public CreateVertexCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.vertex.setGraph(graph);
        this.graph.addVertex(this.vertex);
    }

    @Override
    public void undo() {
        this.graph.removeVertex(this.vertex);
    }
}
