package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.algorithm.IColorAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplyColorAlgorithmAction extends MenuAction {
    private IColorAlgorithm algorithm;
    private Color colorMin;
    private Color colorMax;
    private JFrame frame;
    private boolean mustApplyAlgorithm;

    public ApplyColorAlgorithmAction(IColorAlgorithm colorAlgorithm, Controller controller) {
        super(controller, colorAlgorithm.toString(), null, null, null);
        algorithm = colorAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //getController().getCanvas().getParent().getParent().getParent().getParent().getParent().getParent().setEnabled(false);
        mustApplyAlgorithm = false;

        frame = new JFrame("Paramétrez l'algorythme "+algorithm.toString());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(300,100));
        frame.setResizable(false);

        JPanel content = new JPanel(new GridLayout(2,2,5,5));
        final JButton colorMinBtn = new JButton("Couleur min");
        colorMinBtn.setBackground(algorithm.getMinColor());
        colorMinBtn.setFocusPainted(false);
        colorMinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(getController().getCanvas(), "Choisissez la nouvelle couleur min", algorithm.getMinColor());
                if (color != null) {
                    colorMin = color;
                    colorMinBtn.setBackground(color);
                }
            }
        });
        final JButton colorMaxBtn = new JButton("Couleur max");
        colorMaxBtn.setBackground(algorithm.getMaxColor());
        colorMaxBtn.setFocusPainted(false);
        colorMaxBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(getController().getCanvas(), "Choisissez la nouvelle couleur max", algorithm.getMaxColor());
                if(color != null){
                    colorMax = color;
                    colorMaxBtn.setBackground(color);
                }
            }
        });

        JPanel panelBtnCancel = new JPanel();
        JButton cancelBtn = new JButton("Annuler");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panelBtnCancel.add(cancelBtn);

        JPanel panelBtnOk = new JPanel();
        JButton OkBtn = new JButton("Lancer");
        OkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mustApplyAlgorithm = true;
                frame.dispose();
            }
        });
        panelBtnOk.add(OkBtn);


        content.add(colorMinBtn);
        content.add(colorMaxBtn);
        content.add(panelBtnCancel);
        content.add(panelBtnOk);

        frame.setContentPane(content);

        frame.setVisible(true);

        if(mustApplyAlgorithm){
            //TODO appliquer l'algo
            System.out.println("Appliquer algo commande");
        }
    }
}
