package controller.action;

import controller.Controller;
import controller.MenuAction;
import org.lwjgl.LWJGLException;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class UndoAction extends MenuAction{

    public UndoAction() {
        this(null);
    }

    /**
     * Constructeur de Undo Action
     */
    public UndoAction(Controller controller) {
        super(controller, "Annuler", "res/prev.png", KeyEvent.VK_Z, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.getController().getCanvas().makeCurrent();
        } catch (LWJGLException e1) {
            e1.printStackTrace();
        }
        this.getController().getCurrentGraph().getCommandHandler().undo();
    }
}
