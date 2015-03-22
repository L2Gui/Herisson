package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.VertexShape;
import view.VertexView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        ImageIcon rightButtonIcon = new ImageIcon("res/edit_color.png");

        JFrame f = new JFrame("Editer les options d'un noeud");
        f.setSize(300, 420);

        JPanel p = new JPanel(new GridLayout(6, 2, 5, 5));

        JLabel labelText = new JLabel("Label");
        JLabel labelColor = new JLabel("Couleur du label");
        JLabel shape = new JLabel("Forme");
        JLabel size = new JLabel("Taille");
        JLabel position = new JLabel("Position");
        JLabel colorVertex = new JLabel("Couleur du noeud");
        JLabel border = new JLabel("Taille de la bordure");

        JTextField labelJTF = new JTextField("texte par défault");
        JComboBox shapeList = new JComboBox(VertexShape.values());
        JButton b1 = new JButton("Couleur du label",  rightButtonIcon);
        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                Color color = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", Color.white);
            }
        });
        JTextField sizeJTF = new JTextField("texte par défault");
        JPanel positionPanel = new JPanel(new GridLayout(1, 3));
        JTextField x = new JTextField("abscisse");
        JTextField y = new JTextField("ordonée");
        JTextField z = new JTextField("profondeur");
        positionPanel.add(x);
        positionPanel.add(y);
        positionPanel.add(z);
        JTextField borderJTF = new JTextField("texte par défault");


        p.add(labelText);
        p.add(labelJTF);
        p.add(labelColor);
        p.add(b1);
        p.add(shape);
        p.add(shapeList);
        p.add(size);
        p.add(positionPanel);
        p.add(sizeJTF);
        p.add(position);
        p.add(colorVertex);
        p.add(b1);
        p.add(border);
        p.add(borderJTF);


        f.setContentPane(p);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
