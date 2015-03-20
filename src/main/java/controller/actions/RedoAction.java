package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RedoAction extends MenuAction{

    public RedoAction() {
        this(null);
    }

    /**
     * Constructeur de Redo Action
     */
    public RedoAction(Controller controller) {
        super(controller, "Restaurer", "res/next.png", KeyEvent.VK_Y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("restaurer ("+e.getSource().getClass().getName()+")");
    }
}
