package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;


public class NewEdgeModeAction extends MenuAction{

    public NewEdgeModeAction() {
        this(null);
    }

    /**
     * Constructeur de NewEdge Action
     */
    public NewEdgeModeAction(Controller controller) {
        super(controller, "Nouveau trait", "res/edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.EDGE_CREATION);
        System.out.println("*** mode new edge ("+e.getSource().getClass().getName()+")");
    }
}
