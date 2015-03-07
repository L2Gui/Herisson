package view;

import java.util.Collection;

public interface IVisuAlgorythm {
	/**
	 * @return Le nom d'affichage de l'algo
	 */
	public String getName();
	/**
	 * Affecte à l'algo un nouveau nom d'affichage, et le retourne
	 * @param newName Le nouveau nom de l'algo
	 * @return Le nouveau nom de l'algo
	 */
	public String setName(String newName);
	
	/**
	 * Applique l'algorithme sur la liste de sommets passés en paramètres
	 * @param vertexes Liste de sommets sur lesquels appliquer l'algoritme
	 */
	public void execute(Collection<VertexView> vertexes);
}
