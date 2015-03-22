package controller.action;

import controller.Controller;
import controller.MenuAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ChangeBackgroundColorAction extends MenuAction{

    public ChangeBackgroundColorAction() {
        this(null);
    }

    /**
     * Constructeur de Change Background Color Action
     */
    public ChangeBackgroundColorAction(Controller controller) {
        super(controller, "Couleur de fond", "res/background.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Color color = JColorChooser.showDialog(this.getController().getCanvas(), "Choisissez la nouvelle couleur de fond", this.getController().getCurrentGraph().getBackgroundColor());
        if(color != null){
            this.getController().setCurrentGraphBackgroundColor(color);
        }
    }
}
