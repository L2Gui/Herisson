package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class UndoAction extends MenuAction{
    /**
     * Constructeur de Undo Action
     */
    public UndoAction() {
        super("Annuler", "res/prev.png", KeyEvent.VK_Z, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("annuler ("+e.getSource().getClass().getName()+")");
    }
}
