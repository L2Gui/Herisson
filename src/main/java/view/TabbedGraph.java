package view;

import model.Graph;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Vector;


public class TabbedGraph extends JTabbedPane {
    private GraphCanvas canvas = null;
    private int index=0;

    private Vector<Graph> models= new Vector<>();

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
                if(getSelectedIndex()!=-1) {
                    canvas.setGraph(models.elementAt(getSelectedIndex()));
                    setComponentAt(index, new JPanel());
                    setComponentAt(getSelectedIndex(), getEncapsulatedCanvas());
                    index = getSelectedIndex();
                }
            }
        });
    }

    /**
     * Ajoute un nouvel onglet avec le graphe passé en paramètre
     *
     * @param g le graphe à ajouter
     */
    public void addGraphTab(Graph g) {
        models.add(g);
        super.addTab(g.getName(), getEncapsulatedCanvas());
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
