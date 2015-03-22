package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.Graph;
import model.algorithm.IColorAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kwetzakwak on 22/03/2015.
 */
public class ApplyColorAlgorithmAction extends MenuAction {
    IColorAlgorithm algorithm;

    public ApplyColorAlgorithmAction(IColorAlgorithm colorAlgorithm, Controller controller) {
        super(controller, colorAlgorithm.toString(), null, null, null);
        algorithm = colorAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO faire marcher
        JFrame f = new JFrame();
        JPanel content = new JPanel(new GridLayout(2,2,5,5));
        content.add(new JLabel("chose"));
        JButton colorMin = new JButton("Couleur min");
        colorMin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        content.add(new JLabel("chsoe 2"));
        content.add(new JTextField("valeur2"));

        f.setContentPane(content);

        f.setVisible(true);
    }
}
