package model;

public interface ISizeAlgorithm {
    /**
     *
     * @return la taille qu'aura un élément dont l'attribut paramètre est au maximum
     */
    public float getMaxSize();
    /**
     *
     * @return la taille qu'aura un élément dont l'attribut paramètre est au minimum
     */
    public void getMinSize();

    /**
     * Affecte à l'algorythme la taille qu'aura un élément dont l'attribut paramètre est au maximum
     */
    public void setMaxSize(float maxSize);
    /**
     * Affecte à l'algorythme la taille qu'aura un élément dont l'attribut paramètre est au minimum
     */
    public void setMinSize(float minSize);
    /**
     * Applique l'algorythme au graphe passé en paramètre (en fonction d'un paramètre)
     * @param g graphe
     */
    public void execute(Graph g);
}
