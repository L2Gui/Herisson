package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;


public class NewEdgeAction extends MenuAction{
    /**
     * Constructeur de NewEdge Action
     */
    public NewEdgeAction() {
        super("Nouveau trait", "res/edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("nouveau trait ("+e.getSource().getClass().getName()+")");
    }
}
