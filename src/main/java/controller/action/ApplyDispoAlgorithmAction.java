package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.algorithm.IDispoAlgorithm;
import view.NumericField;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kwetzakwak on 22/03/2015.
 */
public class ApplyDispoAlgorithmAction extends MenuAction {
    private IDispoAlgorithm algorithm;
    private float sizeMin;
    private float sizeMax;
    private NumericField sizeMinField;
    private NumericField sizeMaxField;
    private JFrame frame;
    private boolean mustApplyAlgorithm;

    public ApplyDispoAlgorithmAction(IDispoAlgorithm dispoAlgorithm, Controller controller) {
        super(controller, dispoAlgorithm.toString(), null, null, null);
        algorithm = dispoAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO smthing
    }
}
