package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class NewVertexModeAction extends MenuAction{

    public NewVertexModeAction() {
        this(null);
    }
    
    /**
     * Constructeur de NewVertex Action
     */
    public NewVertexModeAction(Controller controller) {
        super(controller, "Nouveau noeud", "res/node3.png", KeyEvent.VK_4, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.VERTEX_CREATION);
        System.out.println("*** mode new vertex ("+e.getSource().getClass().getName()+")");
    }
}
