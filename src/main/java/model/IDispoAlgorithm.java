package model;

import org.lwjgl.util.vector.Vector3f;

import java.util.Set;
import javafx.util.Pair;

public interface IDispoAlgorithm {
    /**
     * @return Le nom d'affichage de l'algo
     */
    public String getName();
    /**
     * Affecte à l'algo un nouveau nom d'affichage, et le retourne
     * @param newName Le nouveau nom de l'algo
     */
    public void setName(String newName);
    /**
     * Applique l'algorythme au graphe passé en paramètre (ne modifie pas le graphe !) et renvoie un set de paire<Coord,Vertex>
     * @param g graphe
     * @return un Set< Pair<Vertex, Vector3f> >
     */
    public Set<Pair<Vertex, Vector3f>> execute(Graph g);
}
