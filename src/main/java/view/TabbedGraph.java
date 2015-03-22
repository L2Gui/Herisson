package view;

import controller.Controller;
import model.Graph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Vector;


public class TabbedGraph extends JTabbedPane {
    private Controller controller = null;
    private int index=0;

    /**
     * Constructeur de TabbedGraph. Prend un canvas en paramètre
     */
    public TabbedGraph() {
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(getSelectedIndex()!=-1) {
                    TabbedGraph.this.controller.selectGraph(getSelectedIndex());
                    setComponentAt(index, new JPanel());
                    setComponentAt(getSelectedIndex(), new JPanel());
                    index = getSelectedIndex();
                }
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Ajoute un nouvel onglet avec le graphe passé en paramètre
     *
     * @param name le graphe à ajouter
     */
    public void addGraphTab(String name) {
        super.addTab(name, new JPanel());
        setSelectedIndex(getTabCount()-1);
        index=getSelectedIndex();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 20);
    }
}
