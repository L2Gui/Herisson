package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;


public class NewEdgeAction extends MenuAction{

    public NewEdgeAction() {
        this(null);
    }

    /**
     * Constructeur de NewEdge Action
     */
    public NewEdgeAction(Controller controller) {
        super(controller, "Nouveau trait", "res/edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.EDGE_CREATION);
        System.out.println("nouveau trait ("+e.getSource().getClass().getName()+")");
    }
}
