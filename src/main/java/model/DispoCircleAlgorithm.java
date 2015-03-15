package model;

import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashSet;
import java.util.Set;

public class DispoCircleAlgorithm implements IDispoAlgorithm {
    private String name;

    public DispoCircleAlgorithm(String name){
        this.name=name;
    }
    /**
     * Dispose les sommets en cercle (en prenant soin de ne pas avoir de chevauchement)
     *
     * @param g graphe
     * @return un Set< Pair<Vertex, Vector3f> > avec les nouvelles coordonnées par sommet
     */
    @Override
    public Set<Pair<Vertex, Vector3f>> execute(Graph g) {
        Set set = new HashSet();

        //on récupère la taille du plus gros noeud pour éviter tout chevauchement
        float maxSize=0;

        for( Vertex v : g.getVertices()){
            maxSize = Math.max(maxSize, v.getSize());
        }

        //attribution des nouvelles positions
        double angleBetweenTwoVertices = (2*Math.PI)/(g.getVertices().size());
        double radius=Math.sqrt(4*maxSize*maxSize);
        double currentAngle=0;
        Vector3f newPos;

        for( Vertex v : g.getVertices()){
            newPos=new Vector3f((float)(Math.cos(currentAngle)*radius),
                                (float)(Math.sin(currentAngle)*radius),
                                v.getPosition().getZ());

            set.add(new Pair<Vertex, Vector3f>(v, newPos));
            currentAngle += angleBetweenTwoVertices;
        }

        return set;
    }

    /**
     * @return Le nom d'affichage de l'algo
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Affecte à l'algo un nouveau nom d'affichage
     *
     * @param newName Le nouveau nom de l'algo
     */
    @Override
    public void setName(String newName) {
        this.name=newName;
    }
}
