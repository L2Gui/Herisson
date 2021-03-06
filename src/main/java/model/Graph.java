package model;

import controller.CommandHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Graph extends GraphElement {
    private CommandHandler commandHandler;
    private StyleManager styleManager;

	private String name;
	private String filename;
	
	private List<Edge> edges;
	private List<Vertex> vertices;

    private Color backgroundColor;

    private boolean isOriented;

    public Graph(){
        this.name = "";
        this.filename = "";
        this.isOriented = false;
        this.edges = new ArrayList<Edge>();
        this.vertices = new ArrayList<Vertex>();
        this.styleManager = new StyleManager();
        this.commandHandler = new CommandHandler(this);
        backgroundColor = new Color(230,230,230);
    }
	
	// Fonctions d'ajout dans les collections edges et vertices.
	
	/**
	 * 
	 * @param edge : Correspond à l'élément à ajouter à this.edges (List<Edge>)
	 */
	public void addEdge(Edge edge) {
		/* Surrement � modifier, neanmoins on doit prendre en compte que 
		 * la m�thode add renvoie vrai si l'argument a pu etre ajout� ou 
		 * faux si l'�l�ment existe d�ja dans la Collection.
		 */
        if (edge != null) {
            if (!edges.add(edge)) {
                System.out.println("Edge already exists");
            } else {
                // On renseigne ici l'attribut graph du lien en l'ajoutant dans celui ci
                if (edge.getGraph() == null) {
                    edge.setGraph(this);
                }

                if (edge.getStyle() == null) {
                    edge.setStyle(this.styleManager.getDefaultEdgeStyle());
                }
                this.update(new GraphUpdate(GraphUpdate.UpdateType.ADD_EDGE, edge));
            }
        }
	}
	
	/**
	 * 
	 * @param vertex : Correspond � l'�l�ment � ajouter � this.vertices (List<Vertex>)
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

            if (vertex.getGraph() == null) {
                vertex.setGraph(this);
            }

            if (vertex.getStyle() == null){
                vertex.setStyle(this.styleManager.getDefaultVertexStyle());
            }

            this.update(new GraphUpdate(GraphUpdate.UpdateType.ADD_VERTEX, vertex));
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
            this.update(new GraphUpdate(GraphUpdate.UpdateType.REMOVE_EDGE, edge));
            // forcer la destruction du edge
            edge = null;
        }
	}

    /**
     * Supprime toutes les arêtes présentes dans la collection passée en paramètre
     *
     * @param edges Collection<Edge>
     */
    public void removeEdges(Collection<Edge> edges) {
        for(Edge edge : edges){
            if (!this.edges.remove(edge)){
                System.out.println("Edge suppression is impossible");
            } else {
                this.update(new GraphUpdate(GraphUpdate.UpdateType.REMOVE_EDGE, edge));
            }
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
            this.update(new GraphUpdate(GraphUpdate.UpdateType.REMOVE_VERTEX, vertex));
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
	 * @return this.edges (List<Edge>)
	 */
	public List<Edge> getEdges() {
		return edges;
	}

    public void update(GraphUpdate update) {
        super.setChanged();
        super.notifyObservers(update);
    }
	
	/**
	 * 
	 * @return this.vertices (List<Vertex>)
	 */
	public List<Vertex> getVertices() {
		return vertices;
	}

    public StyleManager getStyleManager()
    {
        return styleManager;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
