package controller.commands;

import controller.ICommand;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Created by Corentin on 21/03/2015.
 */
public class RemoveVertexCommand implements ICommand{
    private Vertex vertex;
    private Graph graph;

    public RemoveVertexCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        this.vertex.setGraph(graph);
        for(Edge edge : vertex.getEdges())
        {
            this.graph.removeEdge(edge);
        }
        this.graph.removeVertex(this.vertex);
    }

    @Override
    public void undo() {
        this.graph.addVertex(this.vertex);
    }
}
