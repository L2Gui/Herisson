package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.command.ApplySizeAlgorithmCommand;
import model.algorithm.ISizeAlgorithm;
import view.NumericField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplySizeAlgorithmAction extends MenuAction {
    private ISizeAlgorithm algorithm;
    private float sizeMin;
    private float sizeMax;
    private NumericField sizeMinField;
    private NumericField sizeMaxField;
    private JFrame frame;
    private boolean mustApplyAlgorithm;

    public ApplySizeAlgorithmAction(ISizeAlgorithm colorAlgorithm, Controller controller) {
        super(controller, colorAlgorithm.toString(), null, null, null);
        algorithm = colorAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.sizeMin = 1.0f;
        this.sizeMax = 3.0f;

        this.sizeMinField = new NumericField((double) this.sizeMin);
        this.sizeMaxField = new NumericField((double) this.sizeMax);

        frame = new JFrame("Paramétrez l'algorithme "+algorithm.toString());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(300,100));
        frame.setResizable(false);

        JPanel content = new JPanel(new GridLayout(2,2,5,5));

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
                if(!(sizeMinField.isValid() && sizeMaxField.isValid())){
                    if(sizeMinField.isValid()){
                        sizeMinField.setBackground(new Color(1f,1f,1f,1f));
                    }else {
                        sizeMinField.setBackground(new Color(0.8f, 0.1f, 0.1f, 0.8f));
                    }
                    if(sizeMaxField.isValid()){
                        sizeMaxField.setBackground(new Color(1f,1f,1f,1f));
                    }else {
                        sizeMaxField.setBackground(new Color(0.8f, 0.1f, 0.1f, 0.8f));
                    }
                    frame.repaint();
                }else {
                    getController().executeCommand(new ApplySizeAlgorithmCommand(getController().getCanvas().getGraphView(), algorithm, sizeMin, sizeMax));
                    frame.dispose();
                }
            }
        });
        panelBtnOk.add(OkBtn);


        content.add(sizeMinField);
        content.add(sizeMaxField);
        content.add(panelBtnCancel);
        content.add(panelBtnOk);

        frame.setContentPane(content);

        frame.setVisible(true);
    }

}
