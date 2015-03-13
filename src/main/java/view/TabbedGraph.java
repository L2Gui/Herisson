package view;

import model.Graph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Vector;


public class TabbedGraph extends JTabbedPane {
    GraphCanvas canvas = null;

    Vector<Graph> models= new Vector<Graph>();

    /**
     * Constructeur de TabbedGraph. Prend un canvas en paramètre
     *
     * @param canvas le canvas qui sera utilisé par tous les graphes
     */
    public TabbedGraph(final GraphCanvas canvas){
        this.canvas=canvas;
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.setGraph(models.elementAt(getSelectedIndex()));
            }
        });
    }

    /**
     * Ajoute un nouvel onglet avec le graphe passé en paramètre
     *
     * @param g le graphe à ajouter
     */
    public void addGraphTab(Graph g) {
        JPanel p = new JPanel();
        p.add(canvas);
        models.add(g);

        super.addTab(g.getName(), p);
    }
}
