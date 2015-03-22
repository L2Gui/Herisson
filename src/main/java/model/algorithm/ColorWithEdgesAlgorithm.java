package model.algorithm;


import model.Graph;
import model.Vertex;
import model.VertexShape;

import javax.swing.*;
import java.awt.*;

public class ColorWithEdgesAlgorithm implements IColorAlgorithm {
    private Color maxColor;
    private Color minColor;
    /**
     * @return la couleur qu'aura un élément dont l'attribut paramètre est au maximum
     */
    @Override
    public Color getMaxColor() {
        return maxColor;
    }

    /**
     * @return la couleur qu'aura un élément dont l'attribut paramètre est au minimum
     */
    @Override
    public Color getMinColor() {
        return minColor;
    }

    /**
     * Affecte à l'algorythme la couleur qu'aura un élément dont l'attribut paramètre est au maximum
     *
     * @param maxColor
     */
    @Override
    public void setMaxColor(Color maxColor) {
        this.maxColor = maxColor;
    }

    /**
     * Affecte à l'algorythme la couleur qu'aura un élément dont l'attribut paramètre est au minimum
     *
     * @param minColor
     */
    @Override
    public void setMinColor(Color minColor) {
        this.minColor = minColor;
    }

    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre)
     *
     * @param g graphe
     */
    @Override
    public void execute(Graph g) {
        JFrame f = new JFrame();
        JPanel content = new JPanel(new GridLayout(2,2,5,5));
        content.add(new JLabel("chose"));
        JComboBox shape = new JComboBox(VertexShape.values());
        content.add(shape);

        content.add(new JLabel("chsoe 2"));
        content.add(new JTextField("valeur2"));

        f.setContentPane(content);

        f.setVisible(true);
        for(Vertex v : g.getVertices()){

        }
    }
}
