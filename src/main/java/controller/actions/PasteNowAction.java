package controller.actions;

import controller.Controller;
import controller.MenuAction;
import model.Vertex;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;


public class PasteNowAction extends MenuAction{
    Collection<Object> objects;
    public PasteNowAction() {
        this(null, 0, 0);
    }

    /**
     * Constructeur de Paste now Action
     */
    public PasteNowAction(Controller controller, int x, int y) {
        super(controller, "Coller", "res/paste.png", KeyEvent.VK_V, null);
        //this.objects = objects;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Coller NOW ("+e.getSource().getClass().getName()+")");
        /*
        //pour le cast:
        for( Object o : objects) {
            try {
                Vertex v = (Vertex) o;
            } catch (ClassCastException e) {
                //do nothing
            }
        }
        */
    }
}
