package controller.command;

import controller.Controller;
import controller.ICommand;
import model.Graph;
import model.Vertex;
import org.lwjgl.LWJGLException;
import view.GraphCanvas;

/**
 * Created by Corentin on 22/03/2015.
 */
public class PasteCommand implements ICommand{

    private Vertex vertex;
    private Controller controller;

    public PasteCommand(Controller controller, Vertex vertex) {
        this.vertex = vertex;
        this.controller = controller;
    }

    @Override
    public void undo() {

    }

    @Override
    public void execute(Graph graph) {
        try {
            controller.getCanvas().makeCurrent();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        graph.addVertex(vertex);
        controller.getCanvas().getGraphView().addVertex(vertex);
        //this.controller.getCanvas().getController().getCurrentGraph().addVertex(vertex);
    }
}
