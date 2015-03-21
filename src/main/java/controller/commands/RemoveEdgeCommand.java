package controller.commands;

import controller.ICommand;
import model.Edge;
import model.Graph;

/**
 * Created by Corentin on 21/03/2015.
 */
public class RemoveEdgeCommand implements ICommand {
    private Edge edge;
    private Graph graph;

    public RemoveEdgeCommand(Edge edge) {
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.edge.setGraph(graph);
        this.graph.removeEdge(this.edge);
    }

    @Override
    public void undo() {
        if (edge.getSrcVertex().isDeleted() == false && edge.getDstVertex().isDeleted() == false)
            this.graph.addEdge(this.edge);
    }
}
