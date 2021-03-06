package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.GraphElement;
import model.Vertex;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class CopyNowAction extends MenuAction {
    private GraphElement element;

    public CopyNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de Copy Action
     */
    public CopyNowAction(Controller controller, GraphElement element) {
        super(controller, "Copier", "res/copy.png", KeyEvent.VK_C, 0);
        this.element = element;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

       if (element instanceof Vertex) {
           Vertex tmp = (Vertex) element;
           Vertex clone = new Vertex(tmp);
           super.getController().getCanvas().setPasteBuffer(clone);
        }
        System.out.println( super.getController().getCanvas().getPasteBuffer().toString());
    }
}
