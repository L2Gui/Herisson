package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RedoAction extends MenuAction{
    /**
     * Constructeur de Redo Action
     */
    public RedoAction() {
        super("Restaurer", "res/next.png", KeyEvent.VK_Y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("restaurer ("+e.getSource().getClass().getName()+")");
    }
}
