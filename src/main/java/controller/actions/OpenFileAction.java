package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class OpenFileAction extends MenuAction{
    /**
     * Constructeur de OpenFile Action
     */
    public OpenFileAction() {
        super("Ouvrir", null, KeyEvent.VK_O, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Ouvrir fichier ("+e.getSource().getClass().getName()+")");
    }
}
