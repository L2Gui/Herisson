package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.fileChooserUtil.FileTypeFilter;
import model.Graph;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class OpenFileAction extends MenuAction{

    public OpenFileAction() {
        this(null);
    }
    
    /**
     * Constructeur de OpenFile Action
     */
    public OpenFileAction(Controller controller) {
        super(controller, "Ouvrir", null, KeyEvent.VK_O, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("./");

        FileFilter graphMLFilter = new FileTypeFilter(".graphml", "GraphML");
        FileFilter dotFilter = new FileTypeFilter(".dot", "Dot");

        fileChooser.addChoosableFileFilter(graphMLFilter);
        fileChooser.addChoosableFileFilter(dotFilter);

        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(graphMLFilter);

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filename);

            Graph graph = null;
            try {
                if (filename.endsWith(".graphml")){
                    if (this.getController().getIoAlgorithms().get("GraphML").isConform(filename)) {
                        graph = this.getController().getIoAlgorithms().get("GraphML").open(filename);
                    }
                } else if (filename.endsWith(".dot")){
                    if (this.getController().getIoAlgorithms().get("Dot").isConform(filename)){
                        graph = this.getController().getIoAlgorithms().get("Dot").open(filename);
                    }

                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            if (graph != null){
                this.getController().addGraph(graph);
            }
        }

        System.out.println("Ouvrir fichier ("+e.getSource().getClass().getName()+")");
    }
}
