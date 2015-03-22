package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.command.PasteCommand;
import model.GraphElement;
import model.Vertex;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;


public class PasteNowAction extends MenuAction{
    Collection<GraphElement> objects;
    private int x;
    private int y;


    /*public PasteNowAction() { this(null, 0, 0); }*/

    /**
     * Constructeur de Paste now Action
     */
    public PasteNowAction(Controller controller, int x, int y) {
        super(controller, "Coller", "res/paste.png", KeyEvent.VK_V, null);

        //this.objects = objects;
        this.x = x;
        this.y = y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Coller NOW ("+e.getSource().getClass().getName()+")");

        if (getController().getCanvas().getPasteBuffer() instanceof Vertex) {
            //GraphCanvas.this.controller.executeCommand(new CreateVertexCommand((Vertex) pasteBuffer));
            Vertex vertex = new Vertex((Vertex) getController().getCanvas().getPasteBuffer());
            vertex.setPosition(new Vector3f(x,y,vertex.getPosition().getZ()));
            this.getController().getCurrentGraph().getCommandHandler().executeCommand(new PasteCommand(vertex));
        }
    }
}
