package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class SaveAsAction extends MenuAction{
    /**
     * Constructeur de SaveAs Action
     */
    public SaveAsAction() {
        super("Enregistrer sous", null, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Enregistrer sous ("+e.getSource().getClass().getName()+")");
    }
}
