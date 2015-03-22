package controller.actions;

import controller.Controller;
import controller.MenuAction;
import controller.commands.RemoveVertexCommand;
import model.Vertex;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RemoveNowAction extends MenuAction {
    private Vertex vertex;

    public RemoveNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Remove now Action
     */
    public RemoveNowAction(Controller controller, Vertex vertex) {
        super(controller, "Supprimer", "res/cancel.png", KeyEvent.VK_R, 4);
        this.vertex = vertex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().getCurrentGraph().getCommandHandler().executeCommand(new RemoveVertexCommand(this.vertex));
    }
}
