package controller.commands;

import controller.Command;
import controller.CommandContext;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertex extends Command {
    private Vertex vertex;

    @Override
    public void execute() {
        CommandContext context = super.getContext();


        vertex = new Vertex();
    }

    @Override
    public void undo() {

    }
}
