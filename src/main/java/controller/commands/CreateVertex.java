package controller.commands;

import controller.ICommand;
import model.Vertex;

/**
 * Created by Clement on 13/03/2015.
 */
public class CreateVertex implements ICommand {
    private Vertex vertex;

    @Override
    public void execute() {
        vertex = new Vertex();
    }

    @Override
    public void undo() {

    }
}
