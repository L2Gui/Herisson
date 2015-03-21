package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;


public class EditEdgeAction extends MenuAction{

    public EditEdgeAction() {
        this(null);
    }

    /**
     * Constructeur de Edit Edge
     */
    public EditEdgeAction(Controller controller) {
        super(controller, "Modifier trait", "res/edit_edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.EDGE_EDITION);
        System.out.println("modifier trait ("+e.getSource().getClass().getName()+")");
    }
}
