package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;
import org.lwjgl.util.vector.Vector3f;
import view.GraphView;
import view.VertexView;

/**
 * Created by Corentin on 21/03/2015.
 */
public class MoveVertexCommand implements ICommand{
    private GraphView graphView;
    private VertexView vertexView;
    private Vector3f startPos;
    private Vector3f endPos;
    private Graph graph;

    public MoveVertexCommand(GraphView graphView, VertexView vertexView, Vector3f startPos, Vector3f endPos) {
        this.graphView = graphView;
        this.vertexView = vertexView;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public void execute(Graph graph) {
        this.vertexView.getModel().setPosition(endPos);
    }

    @Override
    public void undo() {
        graphView.addTranslatingVertices(vertexView, this.startPos);
    }
}
