package controller.commands;

import controller.ICommand;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateEdgeCommand implements ICommand {
    private Edge edge;
    private Graph graph;

    public CreateEdgeCommand(Edge edge) {
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.graph.addEdge(this.edge);
        this.edge.getSrcVertex().addEdge(this.edge);
        this.edge.getDstVertex().addEdge(this.edge);
    }

    @Override
    public void undo() {
        this.graph.removeEdge(this.edge);
        this.edge.getSrcVertex().removeEdge(this.edge);
        this.edge.getDstVertex().removeEdge(this.edge);
    }
}
