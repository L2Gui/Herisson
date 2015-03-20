package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CopyAction extends MenuAction{
    /**
     * Constructeur de Copy Action
     */
    public CopyAction() {
        super("Copier", "res/copy.png", KeyEvent.VK_C, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Copier ("+e.getSource().getClass().getName()+")");
    }
}
