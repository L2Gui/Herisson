package controller.action;

import controller.Controller;
import controller.MenuAction;
import view.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;


public class EditVertexNowAction extends MenuAction{

    private VertexView vertexView;
    private VertexView modifiedVertexView;

    public EditVertexNowAction() {
        this(null, null);
    }

    /**
     * Constructeur de EditVertex Now Action
     */
    public EditVertexNowAction(Controller controller, VertexView vertexView) {
        super(controller, "Modifier Noeud", "res/edit_node3.png", null, null);
        this.vertexView = vertexView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //modifiedVertexView = new VertexView()
        System.out.println("edit vertex (" + e.getSource().getClass().getName() + ")");
        JFrame f = new JFrame("Editer les options d'un noeud");
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));

        ////// Placer les elements //////
        p.add(new JLabel("Label :"));
        p.add(new JTextField(vertexView.getModel().getLabel()));

        f.setContentPane(p);
        f.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                e.getNewState();
            }
        });

    }
}