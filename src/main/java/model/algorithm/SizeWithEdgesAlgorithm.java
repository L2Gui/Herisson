package model.algorithm;


import model.Graph;
import model.Vertex;
import org.javatuples.Pair;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SizeWithEdgesAlgorithm implements ISizeAlgorithm {

    @Override
    public String toString() {
        return "Taille en fonction du degrès";
    }

    private float minSize;
    private float maxSize;

    public SizeWithEdgesAlgorithm(){
        this.minSize=(float)1.0;
        this.maxSize=(float)3.0;
    }
    /**
     * @return la taille qu'aura un élément dont l'attribut paramètre est au maximum
     */
    @Override
    public float getMaxSize() {
        return maxSize;
    }

    /**
     * @return la taille qu'aura un élément dont l'attribut paramètre est au minimum
     */
    @Override
    public float getMinSize() {
        return minSize;
    }

    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre) en tenant compte de la couleur min et max.
     * Stock les couleurs min et max pour futur utilisation de l'algorithme
     *
     * @param minSize
     * @param maxSize
     * @param g
     * @return un Set< Pair<Vertex, int> >
     */
    @Override
    public Set<Pair<Vertex, Float>> execute(float minSize, float maxSize, Graph g) {
        Set set = new HashSet();

        this.minSize = minSize;
        this.maxSize = maxSize;

        // récupération du max et min du nombre d'arêtes
        int nbMaxVertices = Integer.MIN_VALUE;
        int nbMinVertices = Integer.MAX_VALUE;
        for(Vertex v : g.getVertices()){
            nbMaxVertices = Math.max(nbMaxVertices, v.getEdges().size());
            nbMinVertices = Math.min(nbMinVertices, v.getEdges().size());
        }

        if(nbMaxVertices > 0) {
            //affectation des tailles par rapport au nb d'arêtes si le max d'arête et != 0
            for (Vertex v : g.getVertices()) {
                set.add(new Pair<Vertex, Float>(v, getNewSizeOf(v.getEdges().size(), nbMaxVertices)));
            }
        }else{
            //sinon, taille min pour tout le monde
            for (Vertex v : g.getVertices()) {
                set.add(new Pair<Vertex, Float>(v, this.minSize));
            }
        }
        return set;
    }

    private float getNewSizeOf(int value, int maxValue){
        Double ratio = 1.*value/maxValue;
        return (float) (ratio*(this.maxSize-this.minSize))+this.minSize;
    }
}
