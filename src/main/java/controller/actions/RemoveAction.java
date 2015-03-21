package controller.actions;

import controller.Controller;
import controller.ControllerState;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RemoveAction extends MenuAction{

    public RemoveAction() {
        this(null);
    }

    /**
     * Constructeur de Remove Action
     */
    public RemoveAction(Controller controller) {
        super(controller, "Supprimer", "res/cancel.png", KeyEvent.VK_R, 4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.getController().setState(ControllerState.DELETION);
        System.out.println("remove ("+e.getSource().getClass().getName()+")");
    }
}