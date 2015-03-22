package controller.actions;

import controller.Controller;
import controller.MenuAction;
import org.lwjgl.LWJGLException;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class RedoAction extends MenuAction{

    public RedoAction() {
        this(null);
    }

    /**
     * Constructeur de Redo Action
     */
    public RedoAction(Controller controller) {
        super(controller, "Restaurer", "res/next.png", KeyEvent.VK_Y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.getController().getCanvas().makeCurrent();
        } catch (LWJGLException e1) {
            e1.printStackTrace();
        }
        this.getController().getCurrentGraph().getCommandHandler().redo();
    }
}
