package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.command.RemoveEdgeCommand;
import controller.command.RemoveVertexCommand;
import model.Edge;
import model.GraphElement;
import model.Vertex;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RemoveNowAction extends MenuAction {
    private GraphElement element;

    public RemoveNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Remove now Action
     */
    public RemoveNowAction(Controller controller, GraphElement element) {
        super(controller, "Supprimer", "res/cancel.png", KeyEvent.VK_R, 4);
        this.element = element;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( element instanceof Vertex) {
            this.getController().getCurrentGraph().getCommandHandler().executeCommand(new RemoveVertexCommand((Vertex)this.element));
        }
        if( element instanceof Edge) {
            this.getController().getCurrentGraph().getCommandHandler().executeCommand(new RemoveEdgeCommand((Edge)this.element));
        }
    }
}
