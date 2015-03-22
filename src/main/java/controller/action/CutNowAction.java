package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CutNowAction extends MenuAction {
    private Object object;

    public CutNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Cut Action
     */
    public CutNowAction(Controller controller, Object object) {
        super(controller, "Couper", "res/cut.png", KeyEvent.VK_X, null);
        this.object = object;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("COPY NOW !");
        super.getController().getCanvas().setPasteBuffer(object);
    }
}
