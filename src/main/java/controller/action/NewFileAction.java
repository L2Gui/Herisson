package controller.action;

import controller.Controller;
import controller.MenuAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class NewFileAction extends MenuAction{

    public NewFileAction() {
        this(null);
    }

    /**
     * Constructeur de NewFile Action
     */
    public NewFileAction(Controller controller) {
        super(controller, "Nouveau Graphe", "res/file16.png", KeyEvent.VK_N, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Nouveau fichier ("+e.getSource().getClass().getName()+")");
    }
}
