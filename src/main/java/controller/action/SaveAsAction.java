package controller.action;

import controller.Controller;
import controller.MenuAction;
import controller.fileChooserUtil.FileTypeFilter;
import view.TabbedGraph;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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

        FileFilter graphMLFilter = new FileTypeFilter(".graphml", "GraphML");
        FileFilter dotFilter = new FileTypeFilter(".dot", "Dot");

        fileChooser.addChoosableFileFilter(graphMLFilter);
        fileChooser.addChoosableFileFilter(dotFilter);

        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(graphMLFilter);

        int response = fileChooser.showSaveDialog(null);



        if (response == JFileChooser.APPROVE_OPTION){
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(filename);

            fileChooser.getFileFilter();
            if (fileChooser.getFileFilter() == graphMLFilter){
                this.getController().getIoAlgorithms().get("GraphML").save(filename, this.getController().getCurrentGraph());
            } else if (fileChooser.getFileFilter() == dotFilter){
                this.getController().getIoAlgorithms().get("Dot").save(filename, this.getController().getCurrentGraph());
            } else {
                if (filename.endsWith(".graphml")){
                    this.getController().getIoAlgorithms().get("GraphML").save(filename, this.getController().getCurrentGraph());
                } else if (filename.endsWith(".dot")){
                    this.getController().getIoAlgorithms().get("Dot").save(filename, this.getController().getCurrentGraph());
                } else {
                    this.getController().getIoAlgorithms().get("GraphML").save(filename, this.getController().getCurrentGraph());
                }
            }
            getController().getCurrentGraph().setName(fileChooser.getSelectedFile().getName());
            getController().getWindow().getTabs().setTitleAt(getController().getWindow().getTabs().getSelectedIndex(), getController().getCurrentGraph().getName());
            getController().getWindow().getTabs().repaint();
        }

    }
}
