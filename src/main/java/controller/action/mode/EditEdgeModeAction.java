package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class EditEdgeModeAction extends MenuAction{

    public EditEdgeModeAction() {
        this(null);
    }

    /**
     * Constructeur de Edit Edge
     */
    public EditEdgeModeAction(Controller controller) {
        super(controller, "Modifier trait", "res/edit_edge3.png", KeyEvent.VK_5, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.EDGE_EDITION);
        System.out.println("*** mode edit edge ("+e.getSource().getClass().getName()+")");
    }
}
