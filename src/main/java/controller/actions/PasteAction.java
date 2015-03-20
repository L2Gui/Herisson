package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class PasteAction extends MenuAction{
    /**
     * Constructeur de Paste Action
     */
    public PasteAction(Controller controller) {
        super(controller, "Coller", "res/paste.png", KeyEvent.VK_V, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Coller ("+e.getSource().getClass().getName()+")");
    }
}
