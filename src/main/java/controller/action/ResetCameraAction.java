package controller.action;

import controller.Controller;
import controller.MenuAction;
import org.lwjgl.util.vector.Vector3f;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ResetCameraAction extends MenuAction {

    public ResetCameraAction() {
        this(null);
    }

    /**
     * Constructeur de Zoom + Action
     */
    public ResetCameraAction(Controller controller) {
        super(controller, "Reset caméra", "res/camera2.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.getController().getCanvas().setCameraTarget(new Vector3f(0f, 0f, 10f));
        super.getController().getCanvas().setCameraDirection(new Vector3f(0f,0f,-1f));
    }
}
