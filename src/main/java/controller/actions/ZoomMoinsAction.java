package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class ZoomMoinsAction extends MenuAction{
    /**
     * Constructeur de Zoom - Action
     */
    public ZoomMoinsAction() {
        super("Zoom -", "res/dezoom.png", KeyEvent.VK_SUBTRACT, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("dezoom ("+e.getSource().getClass().getName()+")");
    }
}
