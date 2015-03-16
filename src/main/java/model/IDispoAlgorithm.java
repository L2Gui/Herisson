package model;

import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;

import java.util.Set;

public interface IDispoAlgorithm {
    /**
     * Applique l'algorythme au graphe passé en paramètre (ne modifie pas le graphe !) et renvoie un set de paire<Coord,Vertex>
     * @param g graphe
     * @return un Set< Pair<Vertex, Vector3f> >
     */
    public Set<Pair<Vertex, Vector3f>> execute(Graph g);
}
