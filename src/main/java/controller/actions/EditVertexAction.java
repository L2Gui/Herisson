package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class EditVertexAction extends MenuAction{
    /**
     * Constructeur de EditVertex Action
     */
    public EditVertexAction(Controller controller) {
        super(controller, "Modifier Noeud", "res/edit_node3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("edit vertex ("+e.getSource().getClass().getName()+")");
    }
}
