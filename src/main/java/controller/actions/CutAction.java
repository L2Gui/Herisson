package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CutAction extends MenuAction{
    /**
     * Constructeur de Cut Action
     */
    public CutAction(Controller controller) {
        super(controller, "Couper", "res/cut.png", KeyEvent.VK_X, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Couper ("+e.getSource().getClass().getName()+")");
    }
}
