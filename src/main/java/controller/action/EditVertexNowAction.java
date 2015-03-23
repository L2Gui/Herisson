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
    private Color invert(Color color){
        return new Color(255-color.getRed(),
                255-color.getGreen(),
                255-color.getBlue());
    }
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

        flabelColor = vertexView.getModel().getTextColor();
        fvertexColor = vertexView.getModel().getBackgroundColor();
        fborderColor = vertexView.getModel().getBorderColor();

        final JFrame f = new JFrame("Editer les options d'un noeud");

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setSize(350, 380);
        f.setResizable(false);

        JPanel p = new JPanel(new GridLayout(9, 2, 5, 5));

        ArrayList<JComponent> jElements = new ArrayList<JComponent>();

        JLabel labelText = new JLabel("Label");
        JLabel labelColor = new JLabel("Couleur du label");
        JLabel shape = new JLabel("Forme");
        JLabel size = new JLabel("Taille");
        JLabel position = new JLabel("Position");
        final JLabel colorVertex = new JLabel("Couleur du noeud");
        JLabel borderSize = new JLabel("Taille de la bordure");
        JLabel borderColor = new JLabel("Couleur de la bordure");

        final NumericField labelJTF = new NumericField(vertexView.getModel().getLabel());
        shapeList = new JComboBox<VertexShape>(VertexShape.values());
        final JButton colorLabelBtn = new JButton("Couleur du label"); colorLabelBtn.setBackground(flabelColor); colorLabelBtn.setForeground(invert(flabelColor));
        final JButton colorVertexBtn = new JButton("Couleur du noeud"); colorVertexBtn.setBackground(fvertexColor); colorVertexBtn.setForeground(invert(fvertexColor));
        final JButton colorBorderBtn = new JButton("Couleur de la bordure"); colorBorderBtn.setBackground(fborderColor); colorBorderBtn.setForeground(invert(fborderColor));
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
        jElements.add(colorLabelBtn);
        jElements.add(colorVertexBtn);
        jElements.add(colorBorderBtn);
        jElements.add(ok);
        jElements.add(cancel);
        jElements.add(sizeJTF);
        jElements.add(positionPanel);
        jElements.add(borderJTF);
        jElements.add(borderColor);

        for(JComponent jc : jElements)
        {
            if (jc instanceof JLabel)
                jc.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
        }

        colorLabelBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //textColor
                Color newColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", flabelColor);
                if(newColor != null){
                    flabelColor = newColor;
                    colorLabelBtn.setBackground(newColor);
                    colorLabelBtn.setForeground(invert(newColor));
                }
            }
        });

        colorVertexBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //vertexColor
                Color newColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", fvertexColor);
                if(newColor != null){
                    fvertexColor = newColor;
                    colorVertexBtn.setBackground(newColor);
                    colorVertexBtn.setForeground(invert(newColor));
                }
            }
        });

        colorBorderBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            { //borderColor
                Color newColor = JColorChooser.showDialog(null, "Choisissez la nouvelle couleur de fond", fborderColor);
                if(newColor !=null){
                    fborderColor = newColor;
                    colorBorderBtn.setBackground(fborderColor);
                    colorBorderBtn.setForeground(invert(fborderColor));
                }
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
        p.add(colorLabelBtn);
        p.add(shape);
        p.add(shapeList);
        p.add(size);
        p.add(sizeJTF);
        p.add(position);
        p.add(positionPanel);
        p.add(colorVertex);
        p.add(colorVertexBtn);
        p.add(borderSize);
        p.add(borderJTF);
        p.add(borderColor);
        p.add(colorBorderBtn);

        JPanel panelOk = new JPanel();
        panelOk.add(ok);
        JPanel panelCancel = new JPanel();
        panelCancel.add(cancel);

        p.add(panelOk);
        p.add(panelCancel);


        f.setContentPane(p);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
