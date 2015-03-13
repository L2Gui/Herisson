package controller.commands;

import controller.Command;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertex extends Command {
    private Vertex vertex;

    public CreateVertex() {
        this.vertex = new Vertex();
    }

    @Override
    public void execute() {
        super.getContext().getCurrentGraph().addVertex(this.vertex);
    }

    @Override
    public void undo() {
        super.getContext().getCurrentGraph().removeVertex(this.vertex);
    }
}
