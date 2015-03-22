package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ZoomPlusNowAction extends MenuAction {

    private int x;
    private int y;

    public ZoomPlusNowAction() {
        this(null, 0, 0);
    }

    /**
     * Constructeur de Zoom + Action
     */
    public ZoomPlusNowAction(Controller controller, int x, int y) {
        super(controller, "Zoom +", "res/zoom.png", KeyEvent.VK_ADD, 5);
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.getController().getCanvas().setCameraZoom(1 / 1.5f);
        super.getController().getCanvas().setCameraFocus(x, y);
    }
}
