package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.command.ChangeVertexThroughBoxCommand;
import model.LineStyle;
import model.Vertex;
import model.VertexShape;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Vector3f;
import view.EdgeView;
import view.NumericField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Kwetzakwak on 23/03/2015.
 */
public class EditEdgeNowAction extends MenuAction {

    private EdgeView edgeView;
    private Color color;
    private NumericField sizeJTF;


    public EditEdgeNowAction(Controller controller, EdgeView edgeView) {
        super(controller, "Modifier trait", "res/edit_edge3.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("edit edge (" + e.getSource().getClass().getName() + ")");

        Icon icone = new ImageIcon("res/edit_color.png");

        color = edgeView.getModel().getColor();
        sizeJTF.setText("" + edgeView.getModel().getThickness());

        final JFrame f = new JFrame("Éditer les options d'une arête");

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setSize(350, 380);
        f.setResizable(false);

        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));

        ArrayList<JComponent> jElements = new ArrayList<JComponent>();

        JLabel colorLabel = new JLabel("Couleur");
        JLabel size = new JLabel("Epaisseur");

        sizeJTF = new NumericField((double) edgeView.getModel().getThickness());
        final JButton colorBtn = new JButton(icone); colorBtn.setFocusPainted(false); colorBtn.setBackground(color);
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Annuler");

        // on ajoute tous nos éléments dans un arraylist pour pouvoir les modifier avec un foreach
        jElements.add(size);
        jElements.add(sizeJTF);
        jElements.add(ok);
        jElements.add(cancel);

        for(JComponent jc : jElements)
        {
            if (jc instanceof JLabel)
                jc.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
        }

        colorBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) { //textColor
                Color newColor = JColorChooser.showDialog(null, "Choisissez couleur de l'arête", color);
                if (newColor != null) {
                    color = newColor;
                    colorBtn.setBackground(newColor);
                }
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                edgeView.getModel().setColor(color);
                if (sizeJTF.isValid())
                    edgeView.getModel().setThickness(Float.parseFloat(sizeJTF.getText()));

                try {
                    getController().getCanvas().makeCurrent();
                } catch (LWJGLException ex) {
                    ex.printStackTrace();
                }
                getController().getCanvas().getGraphView().reloadEdge(edgeView);
                //PAREIL POUR EDGE getController().getCanvas().getController().executeCommand(new ChangeVertexThroughBoxCommand(getController().getCanvas().getGraphView(), oldVertex, vertexView));
                f.dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //EditVertexNowAction.f.dispose();
            }
        });

        p.add(size);
        p.add(sizeJTF);
        p.add(colorLabel);
        p.add(colorBtn);

        JPanel panelOk = new JPanel();
        panelOk.add(ok);
        JPanel panelCancel = new JPanel();
        panelCancel.add(cancel);

        p.add(panelCancel);
        p.add(panelOk);

        f.setContentPane(p);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
