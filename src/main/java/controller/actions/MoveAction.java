package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class MoveAction extends MenuAction{
    public MoveAction() {
        this(null);
    }

    /**
     * Constructeur de Move Action
     */
    public MoveAction(Controller controller) {
        super(controller, "Mode déplacement", "res/move.png", KeyEvent.VK_D, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.getController().setState(ControllerState.MOVE);
    }
}
