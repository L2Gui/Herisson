package model.algorithm;


import model.Graph;
import model.Vertex;
import org.javatuples.Pair;

import java.awt.*;
import java.util.Set;

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
     * @return Le nom de l'algorythme
     */
    @Override
    public String toString();

    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre) en tenant compte de la couleur min et max.
     * Stock les couleurs min et max pour futur utilisation de l'algorithme
     * @param minColor
     * @param maxColor
     * @param g
     * @return un Set< Pair<Vertex, Color> >
     */
    public Set<Pair<Vertex, Color>> execute(Color minColor, Color maxColor, Graph g);
}
