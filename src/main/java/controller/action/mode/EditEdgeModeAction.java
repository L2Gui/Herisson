package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;


public class EditEdgeModeAction extends MenuAction{

    public EditEdgeModeAction() {
        this(null);
    }

    /**
     * Constructeur de Edit Edge
     */
    public EditEdgeModeAction(Controller controller) {
        super(controller, "Modifier trait", "res/edit_edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.EDGE_EDITION);
        System.out.println("*** mode edit edge ("+e.getSource().getClass().getName()+")");
    }
}
