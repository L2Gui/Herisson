package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class MoveAction extends MenuAction{
    /**
     * Constructeur de Move Action
     */
    public MoveAction() {
        super("Mode déplacement", "res/move.png", KeyEvent.VK_D, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("passage en mode deplacement ("+e.getSource().getClass().getName()+")");
    }
}
