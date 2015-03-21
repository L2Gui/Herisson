package controller.actions;

import controller.Controller;
import controller.MenuAction;
import controller.State;

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
        this.getController().setState(State.EDGE_EDITION);
        System.out.println("modifier trait ("+e.getSource().getClass().getName()+")");
    }
}
