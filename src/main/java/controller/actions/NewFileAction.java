package controller.actions;

import controller.MenuAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class NewFileAction extends MenuAction{
    /**
     * Constructeur de NewFile Action
     */
    public NewFileAction() {
        super("Nouveau Graphe", "res/file16.png", KeyEvent.VK_N, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Nouveau fichier ("+e.getSource().getClass().getName()+")");
    }
}
