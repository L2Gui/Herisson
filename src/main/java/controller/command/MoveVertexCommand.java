package controller.command;

import controller.ICommand;
import model.Graph;
import model.Vertex;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Corentin on 21/03/2015.
 */
public class MoveVertexCommand implements ICommand{

    private Vertex vertex;
    Vector3f startPos;
    Vector3f endPos;
    private Graph graph;

    public MoveVertexCommand(Vertex vertex, Vector3f startPos, Vector3f endPos) {
        this.vertex = vertex;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public void execute(Graph graph) {
        vertex.setPosition(endPos);
    }

    @Override
    public void undo() {
        vertex.setPosition(startPos);
    }
}
