package controller;

import controller.CommandContext;
import controller.ICommand;

/**
 * Created by Clement on 13/03/2015.
 */
public abstract class Command implements ICommand {
    private CommandContext context;

    public void setContext(CommandContext context) {
        this.context = context;
    }

    public CommandContext getContext() {
        return this.context;
    }
}
