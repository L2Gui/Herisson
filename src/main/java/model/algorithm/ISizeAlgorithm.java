package model.algorithm;

import model.Graph;
import model.Vertex;
import org.javatuples.Pair;
import java.util.Set;

public interface ISizeAlgorithm {
    /**
     * @return Le nom de l'algorythme
     */
    @Override
    public String toString();
    /**
     *
     * @return la taille qu'aura un élément dont l'attribut paramètre est au maximum
     */
    public float getMaxSize();
    /**
     *
     * @return la taille qu'aura un élément dont l'attribut paramètre est au minimum
     */
    public float getMinSize();

    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre) en tenant compte de la couleur min et max.
     * Stock les couleurs min et max pour futur utilisation de l'algorithme
     * @param minSize
     * @param maxSize
     * @param g
     * @return un Set< Pair<Vertex, int> >
     */
    public Set<Pair<Vertex, Float>> execute(float minSize, float maxSize, Graph g);
}
