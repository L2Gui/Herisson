package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;


public class NewVertexAction extends MenuAction{

    public NewVertexAction() {
        this(null);
    }
    
    /**
     * Constructeur de NewVertex Action
     */
    public NewVertexAction(Controller controller) {
        super(controller, "Nouveau noeud", "res/node3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.VERTEX_CREATION);
        System.out.println("nouveau noeud ("+e.getSource().getClass().getName()+")");
    }
}
