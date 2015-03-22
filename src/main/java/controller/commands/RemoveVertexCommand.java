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
            if(edge.getSrcVertex()==this.vertex){
                edge.getDstVertex().getEdges().remove(edge);
            }else{
                edge.getSrcVertex().getEdges().remove(edge);
            }
            this.graph.getCommandHandler().executeCommand(new RemoveEdgeCommand(edge));

        }
        vertex.getEdges().clear();

        this.graph.removeVertex(this.vertex);
    }

    @Override
    public void undo() {
        this.graph.addVertex(this.vertex);
        for(Edge edge : this.vertex.getEdges())
        {
            //this.graph.addEdge(edge);
        }
    }
}
