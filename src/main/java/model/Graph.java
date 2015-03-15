package model;

import java.util.ArrayList;
import java.util.Collection;

public class Graph {

    private StyleManager styleManager;

	private String name;
	private String filename;
	
	private Collection<Edge> edges;
	private Collection<Vertex> vertices;

    private boolean isOriented;

    public Graph()
    {
        name = "";
        filename = "";
        isOriented = false;
        edges = new ArrayList<Edge>();
        vertices = new ArrayList<Vertex>();
        styleManager = new StyleManager();
    }
	
	// Fonctions d'ajout dans les collections edges et vertices.
	
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
                edge.setStyle(this.styleManager.getDefaultEdgeStyle());
            }
        }
	}
	
	/**
	 * 
	 * @param vertex : Correspond � l'�l�ment � ajouter � this.vertices (Collection<Vertex>)
	 */
	public void addVertex(Vertex vertex){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode add renvoie vrai si l'argument a pu etre ajout� ou 
		 * faux si l'�l�ment existe d�ja dans la Collection.
		 */
		if (!vertices.add(vertex)){
			System.out.println("Vertex already exist");
		} else {
            // On renseigne ici l'attribut graph du noeud en l'ajoutant dans celui ci
            vertex.setGraph(this);
            vertex.setStyle(this.styleManager.getDefaultVertexStyle());
        }
	}
	
	// Fonctions de suppression dans les collections edges et vertices.
	
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
	 * @param vertex : Correspond � l'�l�ment � supprimer de this.vertices (Collection<Vertex>)
	 */
	public void removeVertex(Vertex vertex){
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode remove renvoie vrai si l'argument a pu etre supprim� ou 
		 * faux sinon.
		 */
		if (!vertices.remove(vertex)){
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
	 * @param name est le nouveau nom du Graph courant.
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
	 * @return this.vertices (Collection<Vertex>)
	 */
	public Collection<Vertex> getVertices() {
		return vertices;
	}

    public StyleManager getStyleManager()
    {
        return styleManager;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isOriented() {
        return isOriented;
    }

    public void setOriented(boolean isOriented) {
        this.isOriented = isOriented;
    }
}
