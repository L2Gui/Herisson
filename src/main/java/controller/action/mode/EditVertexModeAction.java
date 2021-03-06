package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class EditVertexModeAction extends MenuAction{

    public EditVertexModeAction() {
        this(null);
    }
    
    /**
     * Constructeur de EditVertex Action
     */
    public EditVertexModeAction(Controller controller) {
        super(controller, "Modifier Noeud", "res/edit_node3.png", KeyEvent.VK_6, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.VERTEX_EDITION);
        System.out.println("*** mode edit vertex ("+e.getSource().getClass().getName()+")");
    }
}
