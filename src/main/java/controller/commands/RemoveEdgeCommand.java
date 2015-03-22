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
    private boolean removedFromARemovedVertex;

    public RemoveEdgeCommand(Edge edge) {
        this.edge = edge;
        this.removedFromARemovedVertex = false;
    }
    public RemoveEdgeCommand(Edge edge, boolean removedFromARemovedVertex) {
        this.edge = edge;
        this.removedFromARemovedVertex = removedFromARemovedVertex;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.edge.setGraph(graph);
        if(!removedFromARemovedVertex){
            this.edge.getDstVertex().getEdges().remove(this.edge);
            this.edge.getSrcVertex().getEdges().remove(this.edge);
        }
        this.graph.removeEdge(this.edge);
    }

    @Override
    public void undo() {
        this.graph.addEdge(this.edge);
        this.edge.getDstVertex().getEdges().add(this.edge);
        this.edge.getSrcVertex().getEdges().add(this.edge);
    }
}
