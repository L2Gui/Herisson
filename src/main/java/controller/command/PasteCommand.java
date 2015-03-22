package controller.command;

import controller.Controller;
import controller.ICommand;
import model.Edge;
import model.Graph;
import model.Vertex;
import org.lwjgl.LWJGLException;
import view.GraphCanvas;

/**
 * Created by Corentin on 22/03/2015.
 */
public class PasteCommand implements ICommand{

    private Graph graph;
    private Vertex vertex;
    private Controller controller;

    public PasteCommand(Controller controller, Vertex vertex) {
        this.vertex = vertex;
        this.controller = controller;
    }

    @Override
    public void execute(Graph graph) {
        this.graph = graph;
        try {
            controller.getCanvas().makeCurrent();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        graph.addVertex(vertex);
    }

    @Override
    public void undo() {
        if(!vertex.getEdges().isEmpty()) {
            for(Edge edge : vertex.getEdges())
            {
                if(edge.getSrcVertex()==this.vertex){
                    edge.getDstVertex().getEdges().remove(edge);
                }else{
                    edge.getSrcVertex().getEdges().remove(edge);
                }
            }
            this.graph.getCommandHandler().executeCommand(new RemoveEdgesCommand(vertex.getEdges()));
        }

        vertex.getEdges().clear();

        this.graph.removeVertex(this.vertex);
    }
}
