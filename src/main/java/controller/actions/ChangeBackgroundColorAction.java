package controller.actions;

import controller.Controller;
import controller.MenuAction;
import org.lwjgl.LWJGLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


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
        Color color = JColorChooser.showDialog(this.getController().getCanvas(), "Choisissez la nouvelle couleur de fond", this.getController().getCanvas().getBackgroundColor());
        if(color != null){
            this.getController().getCanvas().setBackgroundColor(color);
            this.getController().getCanvas().setBackground(color);
        }
    }
}
