package controller.command;

import controller.ICommand;
import model.Graph;
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

    public MoveVertexCommand(GraphView graphView, VertexView vertexView, Vector3f startPos, Vector3f endPos) {
        this.graphView = graphView;
        this.vertexView = vertexView;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public void execute(Graph graph) {
        graphView.addTranslatingVertex(vertexView, this.endPos);
    }

    @Override
    public void undo() {
        graphView.addTranslatingVertex(vertexView, this.startPos);
    }
}
