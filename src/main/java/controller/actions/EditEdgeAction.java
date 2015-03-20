package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;


public class EditEdgeAction extends MenuAction{
    /**
     * Constructeur de Edit Edge
     */
    public EditEdgeAction() {
        super("Modifier trait", "res/edit_edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("modifier trait ("+e.getSource().getClass().getName()+")");
    }
}
