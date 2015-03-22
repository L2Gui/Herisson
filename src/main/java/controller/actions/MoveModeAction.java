package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class MoveModeAction extends MenuAction{
    public MoveModeAction() {
        this(null);
    }

    /**
     * Constructeur de Move Action
     */
    public MoveModeAction(Controller controller) {
        super(controller, "Mode déplacement", "res/move.png", KeyEvent.VK_D, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("*** mode move");
        super.getController().setState(ControllerState.MOVE);
    }
}
