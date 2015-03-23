package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.VertexShape;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;
import view.NumericField;
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
    Color flabelColor;
    Color fvertexColor;
    Color fborderColor;
    JComboBox<VertexShape> shapeList;
    NumericField x;
    NumericField y;
    NumericField z;

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

        flabelColor = vertexView.getModel().getTextColor();
        fvertexColor = vertexView.getModel().getBackgroundColor();
        fborderColor = vertexView.getModel().getBorderColor();

        final JFrame f = new JFrame("Editer les options d'un noeud");

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setSize(350, 350);
        f.setResizable(false);

        JPanel p = new JPanel(new GridLayout(9, 2));

        ArrayList<JComponent> jElements = new ArrayList<JComponent>();

        JLabel labelText = new JLabel("Label");
        JLabel labelColor = new JLabel("Couleur du label");
        JLabel shape = new JLabel("Forme");
        JLabel size = new JLabel("Taille");
        JLabel position = new JLabel("Position");
        JLabel colorVertex = new JLabel("Couleur du noeud");
        JLabel borderSize = new JLabel("Taille de la bordure");
        JLabel borderColor = new JLabel("Couleur de la bordure");

        final NumericField labelJTF = new NumericField(vertexView.getModel().getLabel());
        shapeList = new JComboBox<VertexShape>(VertexShape.values());
        JButton b1 = new JButton("Couleur du label",  rightButtonIcon);
        JButton b2 = new JButton("Couleur du noeud",  rightButtonIcon);
        JButton b3 = new JButton("Couleur de la bordure",  rightButtonIcon);
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Annuler");

        final NumericField sizeJTF = new NumericField("" + vertexView.getModel().getSize());
        JPanel positionPanel = new JPanel(new GridLayout(1, 3));
        x = new NumericField("" + String.format("%.2f", vertexView.getModel().getPosition().getX()));
        y = new NumericField("" + String.format("%.2f", vertexView.getModel().getPosition().getY()));
        z = new NumericField("" + String.format("%.2f", vertexView.getModel().getPosition().getZ()));
        z.setEditable(false);
        z.setBackground(new Color(255, 150, 150));

        positionPanel.add(x);
        positionPanel.add(y);
        positionPanel.add(z);
        final NumericField borderJTF = new NumericField("" + vertexView.getModel().getThickness());


        // on ajoute tous nos éléments dans un arraylist pour pouvoir les modifier avec un foreach
        jElements.add(labelText);
        jElements.add(labelColor);
        jElements.add(shape);
        jElements.add(size);
        jElements.add(position);
        jElements.add(colorVertex);
        jElements.add(borderSize);
        jElements.add(labelJTF);
        jElements.add(shapeList);
        jElements.add(b1);
        jElements.add(b2);
        jElements.add(b3);
        jElements.add(ok);
        jElements.add(cancel);
        jElements.add(sizeJTF);
        jElements.add(positionPanel);
        jElements.add(borderJTF);

        for(JComponent jc : jElements)
        {
           // if (jc instanceof JLabel)
                //(JLabel)jc.setHorizontalAlignment(SwingConstants.CENTER);
        }

        b1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //textColor
                flabelColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", flabelColor);
            }
        });

        b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //vertexColor
                fvertexColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", fvertexColor);
            }
        });

        b3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //borderColor
                fborderColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", fborderColor);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vertexView.getModel().setLabel(labelJTF.getText());
                vertexView.getModel().setTextColor(flabelColor);
                vertexView.getModel().setShape((VertexShape)shapeList.getSelectedItem());
                if (sizeJTF.isValid())
                    vertexView.getModel().setSize(Float.parseFloat(sizeJTF.getText()));
                else {System.out.println("pas un float");}
                if (x.isValid() && y.isValid())
                    vertexView.getModel().setPosition(new Vector3f(Float.parseFloat(x.getText().replaceFirst(",", ".")), Float.parseFloat(y.getText().replaceFirst(",", ".")), 0)); // z = 0
                vertexView.getModel().setBackgroundColor(fvertexColor);
                vertexView.getModel().setBorderColor(fborderColor);
                if (borderJTF.isValid())
                    vertexView.getModel().setThickness(Float.parseFloat(borderJTF.getText()));
                try {
                    getController().getCanvas().makeCurrent();
                } catch (LWJGLException ex) {
                    ex.printStackTrace();
                }
                getController().getCanvas().getGraphView().reloadVertex(vertexView);
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
        p.add(borderSize);
        p.add(borderJTF);
        p.add(borderColor);
        p.add(b3);
        p.add(ok);
        p.add(cancel);


        f.setContentPane(p);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
