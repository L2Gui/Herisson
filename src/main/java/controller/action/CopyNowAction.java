package controller.action;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CopyNowAction extends MenuAction {
    private Object object;

    public CopyNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Copy Action
     */
    public CopyNowAction(Controller controller, Object object) {
        super(controller, "Copier", "res/copy.png", KeyEvent.VK_C, 0);
        this.object = object;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("COPY NOW !");
        super.getController().getCanvas().setPasteBuffer(object);
    }
}
