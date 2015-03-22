package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;

/**
 * Created by Corentin on 22/03/2015.
 */
public class PasteCommand implements ICommand{

    private Vertex vertex;

    public PasteCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void undo() {

    }

    @Override
    public void execute(Graph graph) {
        /*vertex.setPosition(getLookAtPosition(x, y));
        this.getController().getCurrentGraph().getVertices().add(new Vertex(vertex));
        this.graphView.addVertex(new Vertex(vertex));*/
    }
}
