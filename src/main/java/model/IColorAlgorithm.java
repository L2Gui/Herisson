package model;


import java.awt.*;
import java.util.Collection;

public interface IColorAlgorithm{
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
