package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class ZoomMoinsAction extends MenuAction{

    public ZoomMoinsAction() {
        this(null);
    }
    
    /**
     * Constructeur de Zoom - Action
     */
    public ZoomMoinsAction(Controller controller) {
        super(controller, "Zoom -", "res/dezoom.png", KeyEvent.VK_SUBTRACT, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("dezoom ("+e.getSource().getClass().getName()+")");
    }
}
