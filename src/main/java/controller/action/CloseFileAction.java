package controller.action;

import controller.Controller;
import controller.MenuAction;
import model.Graph;
import view.GraphView;
import view.TabbedGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kwetzakwak on 23/03/2015.
 */
public class CloseFileAction extends MenuAction {
    TabbedGraph tabs;

    public CloseFileAction(){
        super(null, "Fermer fichier", "res/closefile.png", null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if(JOptionPane.showConfirmDialog(null, "Tout changement non sauvegardé sera perdu", "Fermer l'onglet courant ?", dialogButton) == JOptionPane.YES_OPTION){
            int posToRemove = tabs.getSelectedIndex();
            getController().removeGraphe(getController().getCurrentGraph());
            tabs.remove(posToRemove);
            if(tabs.getTabCount()<=0){
                Graph g = new Graph();
                g.setName("Nouveau graphe");
                getController().addGraph(g);
            }
        }
    }
    public void setTabbedGraph(TabbedGraph tabbedGraph){
        this.tabs = tabbedGraph;
    }
}
