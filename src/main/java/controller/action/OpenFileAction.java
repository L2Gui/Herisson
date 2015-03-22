package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class OpenFileAction extends MenuAction{

    public OpenFileAction() {
        this(null);
    }
    
    /**
     * Constructeur de OpenFile Action
     */
    public OpenFileAction(Controller controller) {
        super(controller, "Ouvrir", null, KeyEvent.VK_O, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Ouvrir fichier ("+e.getSource().getClass().getName()+")");
    }
}
