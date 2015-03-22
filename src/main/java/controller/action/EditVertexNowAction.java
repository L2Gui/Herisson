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
import java.util.ArrayList;


public class EditVertexNowAction extends MenuAction{

    private VertexView vertexView;

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

        final JFrame f = new JFrame("Editer les options d'un noeud");

        f.setSize(350, 300);

        JPanel p = new JPanel(new GridLayout(8, 2));

        ArrayList<JComponent> jElements = new ArrayList<JComponent>();

        JLabel labelText = new JLabel("Label");
        JLabel labelColor = new JLabel("Couleur du label");
        JLabel shape = new JLabel("Forme");
        JLabel size = new JLabel("Taille");
        JLabel position = new JLabel("Position");
        JLabel colorVertex = new JLabel("Couleur du noeud");
        JLabel border = new JLabel("Taille de la bordure");

        final JTextField labelJTF = new JTextField("texte par défault");
        JComboBox shapeList = new JComboBox(VertexShape.values());
        JButton b1 = new JButton("Couleur du label",  rightButtonIcon);
        JButton b2 = new JButton("Couleur du noeud",  rightButtonIcon);
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Annuler");

        JTextField sizeJTF = new JTextField("texte par défault");
        JPanel positionPanel = new JPanel(new GridLayout(1, 3));
        JTextField x = new JTextField("x");
        JTextField y = new JTextField("y");
        JTextField z = new JTextField("z");
        positionPanel.add(x);
        positionPanel.add(y);
        positionPanel.add(z);
        JTextField borderJTF = new JTextField("texte par défault");


        // on ajoute tous nos éléments dans un arraylist pour pouvoir les modifier avec un foreach
        jElements.add(labelText);
        jElements.add(labelColor);
        jElements.add(shape);
        jElements.add(size);
        jElements.add(position);
        jElements.add(colorVertex);
        jElements.add(border);
        jElements.add(labelJTF);
        jElements.add(shapeList);
        jElements.add(b1);
        jElements.add(b2);
        jElements.add(ok);
        jElements.add(cancel);
        jElements.add(sizeJTF);
        jElements.add(positionPanel);
        jElements.add(borderJTF);

        for(JComponent jc : jElements)
        {

        }

        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                Color color = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", Color.white);
            }
        });

        b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                Color color = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", Color.white);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vertexView.getModel().setLabel(labelJTF.getText());
                vertexView.getModel().setTextColor(Color.blue);
                vertexView.getModel().setSize(5);
                //vertexView.getModel().setPosition();
                vertexView.getModel().setBackgroundColor(Color.blue);
                //vertexView.getModel().

                f.dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //EditVertexNowAction.f.dispose();
            }
        });

        p.add(labelText);
        p.add(labelJTF);
        p.add(labelColor);
        p.add(b1);
        p.add(shape);
        p.add(shapeList);
        p.add(size);
        p.add(sizeJTF);
        p.add(position);
        p.add(positionPanel);
        p.add(colorVertex);
        p.add(b2);
        p.add(border);
        p.add(borderJTF);
        p.add(ok);
        p.add(cancel);


        f.setContentPane(p);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
