package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph {

    private StyleManager styleManager;

	private String name;
	private String filename;
	
	private Collection<Edge> edges;
	private Collection<Vertex> vertexs;

    private boolean isOriented;

    public Graph()
    {
        name = "";
        filename = "";
        isOriented = false;
        edges = new ArrayList<Edge>();
        vertexs = new ArrayList<Vertex>();
        styleManager = new StyleManager();
    }
	
	// Fonctions d'ajout dans les collections edges et vertexs.
	
	/**
	 * 
	 * @param edge : Correspond à l'élément à ajouter à this.edges (Collection<Edge>)
	 */
	public void addEdge(Edge edge){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode add renvoie vrai si l'argument a pu etre ajout� ou 
		 * faux si l'�l�ment existe d�ja dans la Collection.
		 */
        if (edge != null) {
            if (!edges.add(edge)) {
                System.out.println("Edge already exists");
            } else {
                // On renseigne ici l'attribut graph du lien en l'ajoutant dans celui ci
                edge.setGraph(this);
            }
        }
	}
	
	/**
	 * 
	 * @param vertex : Correspond � l'�l�ment � ajouter � this.vertexs (Collection<Vertex>)
	 */
	public void addVertex(Vertex vertex){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode add renvoie vrai si l'argument a pu etre ajout� ou 
		 * faux si l'�l�ment existe d�ja dans la Collection.
		 */
		if (!vertexs.add(vertex)){
			System.out.println("Vertex already exist");
		} else {
            // On renseigne ici l'attribut graph du noeud en l'ajoutant dans celui ci
            vertex.setGraph(this);
        }
	}
	
	// Fonctions de suppression dans les collections edges et vertexs.
	
	/**
	 * 
	 * @param edge : Correspond � l'�l�ment � supprimer de this.edges (Collection<Edge>)
	 */
	public void removeEdge(Edge edge){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode remove renvoie vrai si l'argument a pu etre supprim� ou 
		 * faux sinon.
		 */
		if (!edges.remove(edge)){
			System.out.println("Edge suppression is impossible");
		} else {
            // forcer la destruction du edge
            edge = null;
        }
	}
	
	/**
	 * 
	 * @param vertex : Correspond � l'�l�ment � supprimer de this.vertexs (Collection<Vertex>)
	 */
	public void removeVertex(Vertex vertex){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode remove renvoie vrai si l'argument a pu etre supprim� ou 
		 * faux sinon.
		 */
		if (!vertexs.remove(vertex)){
			System.out.println("Vertex suppression is impossible");
		} else {
            // forcer la destruction du vertex
            vertex = null;
        }
	}
	
	/**
	 * 
	 * @return this.name (String)
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Modifie le champs this.name
	 * @param name est le nouveau nom du Graph courrant.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return this.edges (Collection<Edge>)
	 */
	public Collection<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * 
	 * @return this.vertexs (Collection<Vertex>)
	 */
	public Collection<Vertex> getVertices() {
		return vertexs;
	}

    public StyleManager getStyleManager()
    {
        return styleManager;
    }
	
	
}
