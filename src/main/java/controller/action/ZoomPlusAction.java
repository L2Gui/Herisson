package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ZoomPlusAction extends MenuAction {

    public ZoomPlusAction() {
        this(null);
    }

    /**
     * Constructeur de Zoom + Action
     */
    public ZoomPlusAction(Controller controller) {
        super(controller, "Zoom +", "res/zoom.png", KeyEvent.VK_ADD, 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.getController().getCanvas().setCameraZoom(1 / 1.5f);
    }
}
