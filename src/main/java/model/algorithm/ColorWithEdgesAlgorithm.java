package model.algorithm;


import model.Graph;
import model.Vertex;
import model.VertexShape;
import org.javatuples.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class ColorWithEdgesAlgorithm implements IColorAlgorithm {
    private Color maxColor = Color.BLUE;
    private Color minColor = Color.RED;

    @Override
    public String toString() {
        return "Couleur en fonction du degrès";
    }
    
    public ColorWithEdgesAlgorithm(){
    }

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
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre) en tenant compte de la couleur min et max.
     * Stock les couleurs min et max pour futur utilisation de l'algorithme
     *  @param minColor
     * @param maxColor
     * @param g
     * @return un Set< Pair<Vertex, Color> >
     */
    @Override
    public Set<Pair<Vertex, Color>> execute(Color minColor, Color maxColor, Graph g) {

        Set set = new HashSet();

        this.minColor = minColor;
        this.maxColor = maxColor;

        // récupération du max et min du nombre d'arêtes
        int nbMaxVertices = Integer.MIN_VALUE;
        int nbMinVertices = Integer.MAX_VALUE;
        for(Vertex v : g.getVertices()){
            nbMaxVertices = Math.max(nbMaxVertices, v.getEdges().size());
            nbMinVertices = Math.min(nbMinVertices, v.getEdges().size());
        }

        //affectation des couleurs
        for(Vertex v : g.getVertices()){
            set.add(new Pair<Vertex, Color>(v, getNewColorOf(v.getEdges().size(), nbMaxVertices)));
        }
        return set;
    }

    private Color getNewColorOf(int value, int maxEdges){
        double ratio = 1.*value/maxEdges;
        int red = (int) (ratio*(this.maxColor.getRed()-this.minColor.getRed()))+this.minColor.getRed();
        int green = (int) (ratio*(this.maxColor.getGreen()-this.minColor.getGreen()))+this.minColor.getGreen();
        int blue = (int) (ratio*(this.maxColor.getBlue()-this.minColor.getBlue()))+this.minColor.getBlue();

        return new Color(red, green, blue);
    }
}
