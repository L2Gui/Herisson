package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class NewVertexAction extends MenuAction{
    /**
     * Constructeur de NewVertex Action
     */
    public NewVertexAction() {
        super("Nouveau noeud", "res/node3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("nouveau noeud ("+e.getSource().getClass().getName()+")");
    }
}
