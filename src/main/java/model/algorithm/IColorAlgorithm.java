package model.algorithm;


import model.Graph;

import java.awt.*;

public interface IColorAlgorithm {
    /**
     *
     * @return la couleur qu'aura un élément dont l'attribut paramètre est au maximum
     */
    public Color getMaxColor();
    /**
     *
     * @return la couleur qu'aura un élément dont l'attribut paramètre est au minimum
     */
    public Color getMinColor();

    /**
     * Affecte à l'algorythme la couleur qu'aura un élément dont l'attribut paramètre est au maximum
     */
    public void setMaxColor(Color maxColor);
    /**
     * Affecte à l'algorythme la couleur qu'aura un élément dont l'attribut paramètre est au minimum
     */
    public void setMinColor(Color minColor);
    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre)
     * @param g graphe
     */
    public void execute(Graph g);
}
