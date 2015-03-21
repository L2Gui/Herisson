package controller.actions;

import controller.Controller;
import controller.MenuAction;
import view.VertexView;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CopyNowAction extends MenuAction {
    private VertexView vertexView;

    public CopyNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Copy Action
     */
    public CopyNowAction(Controller controller, VertexView vertexView) {
        super(controller, "Copier", "res/copy.png", KeyEvent.VK_C, 0);
        this.vertexView = vertexView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("COPY NOW !");
    }
}
