package controller.commands;

import controller.ICommand;
import model.Edge;
import model.Graph;
import model.Vertex;

import java.awt.event.ActionEvent;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateEdge implements ICommand {
    private Edge edge;
    private Vertex src;
    private Vertex dst;
    private Graph graph;

    public CreateEdge(Vertex src, Vertex dst) {
        this.edge = new Edge();
        this.edge.setSrcVertex(src);
        this.edge.setDstVertex(dst);
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.graph.addEdge(this.edge);
        this.src.addEdge(this.edge);
        this.dst.addEdge(this.edge);
    }

    @Override
    public void undo() {
        this.graph.removeEdge(this.edge);
        this.src.removeEdge(this.edge);
        this.dst.removeEdge(this.edge);
    }
}
