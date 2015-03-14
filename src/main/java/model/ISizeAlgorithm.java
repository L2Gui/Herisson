package model;

public interface ISizeAlgorithm {

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
