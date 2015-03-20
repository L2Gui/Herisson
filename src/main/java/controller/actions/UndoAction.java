package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class UndoAction extends MenuAction{

    public UndoAction() {
        this(null);
    }

    /**
     * Constructeur de Undo Action
     */
    public UndoAction(Controller controller) {
        super(controller, "Annuler", "res/prev.png", KeyEvent.VK_Z, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("annuler ("+e.getSource().getClass().getName()+")");
    }
}
