package controller.actions;

import controller.Controller;
import controller.MenuAction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CopyAction extends MenuAction {

    public CopyAction() {
        this(null);
    }

    /**
     * Constructeur de Copy Action
     */
    public CopyAction(Controller controller) {
        super(controller, "Copier", "res/copy.png", KeyEvent.VK_C, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
