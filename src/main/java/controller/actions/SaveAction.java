package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class SaveAction extends MenuAction{

    public SaveAction() {
        this(null);
    }

    /**
     * Constructeur de Save Action
     */
    public SaveAction(Controller controller) {
        super(controller, "Enregistrer", "res/save.png", KeyEvent.VK_S, 6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Enregistrer ("+e.getSource().getClass().getName()+")");
    }
}
