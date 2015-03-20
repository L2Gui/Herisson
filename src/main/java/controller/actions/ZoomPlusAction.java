package controller.actions;

import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class ZoomPlusAction extends MenuAction{
    /**
     * Constructeur de Zoom + Action
     */
    public ZoomPlusAction() {
        super("Zoom +", "res/zoom.png", KeyEvent.VK_ADD, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("zoom+ ("+e.getSource().getClass().getName()+")");
    }
}
