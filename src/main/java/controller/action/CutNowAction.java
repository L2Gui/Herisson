package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.GraphElement;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CutNowAction extends MenuAction {
    private GraphElement element;

    public CutNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Cut Action
     */
    public CutNowAction(Controller controller, GraphElement element) {
        super(controller, "Couper", "res/cut.png", KeyEvent.VK_X, null);
        this.element = element;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        super.getController().getCanvas().setPasteBuffer(element);
        System.out.println(  super.getController().getCanvas().getPasteBuffer().toString());
    }
}
