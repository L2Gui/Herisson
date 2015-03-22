package controller.action.mode;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RemoveModeAction extends MenuAction{

    public RemoveModeAction() {
        this(null);
    }

    /**
     * Constructeur de Remove Action
     */
    public RemoveModeAction(Controller controller) {
        super(controller, "Supprimer", "res/cancel.png", KeyEvent.VK_7, 4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.DELETION);
        System.out.println("*** mode remove ("+e.getSource().getClass().getName()+")");
    }
}
