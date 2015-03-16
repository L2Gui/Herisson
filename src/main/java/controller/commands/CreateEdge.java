package controller.commands;

import controller.Command;
import model.Edge;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateEdge extends Command {
    private Edge edge;
    private Vertex src;
    private Vertex dst;

    public CreateEdge(Vertex src, Vertex dst) {
        this.edge = new Edge();
        this.edge.setSrcVertex(src);
        this.edge.setDstVertex(dst);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public void execute() {
        super.getContext().getCurrentGraph().addEdge(this.edge);
        src.addEdge(this.edge);
        dst.addEdge(this.edge);
    }

    @Override
    public void undo() {
        super.getContext().getCurrentGraph().removeEdge(this.edge);
        src.removeEdge(this.edge);
        dst.removeEdge(this.edge);
    }
}
