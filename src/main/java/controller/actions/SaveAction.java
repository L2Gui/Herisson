package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class SaveAction extends MenuAction{
    /**
     * Constructeur de Save Action
     */
    public SaveAction() {
        super("Enregistrer", null, KeyEvent.VK_S, 6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Enregistrer ("+e.getSource().getClass().getName()+")");
    }
}
