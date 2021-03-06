package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class ZoomLessAction extends MenuAction{

    public ZoomLessAction() {
        this(null);
    }
    
    /**
     * Constructeur de Zoom - Action
     */
    public ZoomLessAction(Controller controller) {
        super(controller, "Zoom -", "res/dezoom.png", KeyEvent.VK_SUBTRACT, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.getController().getCanvas().setCameraZoom(1.5f);
    }
}
