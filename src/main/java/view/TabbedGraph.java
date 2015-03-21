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
    private GraphCanvas canvas;
    private int index=0;

    /**
     * Constructeur de TabbedGraph. Prend un canvas en paramètre
     *
     * @param canvas le canvas qui sera utilisé par tous les graphes
     */
    public TabbedGraph(GraphCanvas canvas){
        this.canvas = canvas;
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(getSelectedIndex()!=-1) {
                    TabbedGraph.this.controller.selectGraph(getSelectedIndex());
                    setComponentAt(index, new JPanel());
                    setComponentAt(getSelectedIndex(), getEncapsulatedCanvas());
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
        super.addTab(name, getEncapsulatedCanvas());
        setSelectedIndex(getTabCount()-1);
        index=getSelectedIndex();
    }

    /**
     *
     * @return le canvas encapsulé dans un JPanel
     */
    private JPanel getEncapsulatedCanvas(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(canvas, BorderLayout.CENTER);
        return p;
    }
}
