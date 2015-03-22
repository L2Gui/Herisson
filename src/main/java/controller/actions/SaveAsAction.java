package controller.actions;

import controller.Controller;
import controller.MenuAction;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class SaveAsAction extends MenuAction{

    public SaveAsAction() {
        this(null);
    }

    /**
     * Constructeur de SaveAs Action
     */
    public SaveAsAction(Controller controller) {
        super(controller, "Enregistrer sous", null, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("./");
        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION){
            String filename = fileChooser.getSelectedFile().getName();
            System.out.println(filename);

            if (filename.endsWith(".graphml")){
                this.getController().getIoAlgorithms().get("GraphML").save(filename, this.getController().getCurrentGraph());
            } else if (filename.endsWith(".dot")){
                this.getController().getIoAlgorithms().get("Dot").save(filename, this.getController().getCurrentGraph());
            } else {
                this.getController().getIoAlgorithms().get("GraphML").save(filename, this.getController().getCurrentGraph());
            }
        }
        System.out.println("Enregistrer sous ("+e.getSource().getClass().getName()+")");
    }
}
