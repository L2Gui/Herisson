package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class SelectionModeAction extends MenuAction{
    public SelectionModeAction() {
        this(null);
    }

    /**
     * Constructeur de Selection Action
     */
    public SelectionModeAction(Controller controller) {
        super(controller, "Mode Sélection", "res/selection.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("*** mode selection");
        super.getController().setState(ControllerState.SELECTION);
    }
}
