package model;

import opengl.resource.GLShader;
import opengl.resource.object.mesh.GLColoredMesh;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class Vertex {
	private Collection<Edge> edges;
	private VertexStyle style;
	private Graph graph;
	private Vector3f position;
	private String label;

    public Vertex() {
        edges = new ArrayList<Edge>();
        style = null;
        graph = null;
        position = null;
        label = "";
    }
    public Vertex(Vertex v){
        this.edges = new ArrayList<Edge>(v.getEdges());
        this.style = new VertexStyle(v.getStyle());
        this.graph = v.getGraph();
        this.position = new Vector3f(v.getPosition());
        this.label = new String(v.getLabel());
    }

    public Vertex(Graph graph){
        this();
        this.graph = graph;
        this.style = this.graph.getStyleManager().getDefaultVertexStyle();
    }

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
        if (this.graph != null) {
            this.graph.notifyObservers();
        }
	}

	
	public void addEdge(Edge edge) {
		if (!edges.contains(edge)) {
			edges.add(edge);
		}
	}

    public void removeEdge(Edge edge) {
        if (edges.contains(edge)) {
            edges.remove(edge);
        }
    }
	
	/**
	 * 
	 * @return this.style.borderThickness (float)
	 */
	public float getThickness() {
		return style.getBorderThickness();
	}
	
	/**
	 * 
	 * @param thickness est la nouvelle valeur de this.style.borderThickness
	 */

	public Vertex setThickness(float thickness){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBorderThickness(thickness);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getSize(){
		return this.style.getSize();
	}
	
	/**
	 * 
	 * @param size
	 */
	public Vertex setSize(float size){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setSize(size);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBackgroundColor() {
		return this.style.getBackgroundColor();
	}
	
	/**
	 * 
	 * @param color
	 */
	public Vertex setBackgroundColor(Color color){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBackgroundColor(color);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return this.mainView.label (String)
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * 
	 * @param label est la nouvelle valeur de this.mainView.label
	 */
	public void setLabel(String label) {
		this.label = label;
        if (this.graph != null) {
            this.graph.notifyObservers();
        }
	}
	
	/**
	 * 
	 * @return this.style.textColor (Color)
	 */
	public Color getTextColor() {
		return style.getTextColor();
	}
	
	/**
	 * 
	 * @param textColor est la nouvelle valeur de this.style.textColor
	 */
	public Vertex setTextColor(Color textColor){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setTextColor(textColor);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return this.style.font (Font)
	 */
	public Font getFont() {
        /*if (style.getFont() == null);
            style.setFont(new Font("Verdana", Font.PLAIN, 32));*/

        return style.getFont();
        //return new Font("Verdana", Font.PLAIN, 32);
	}
	
	/**
	 * 
	 * @param font est la nouvelle valeur de this.style.font
	 */
	public Vertex setFont(Font font){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setFont(font);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBorderColor() {
		return this.style.getBorderColor();
	}

	public Vertex setBorderColor(Color color){

        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBorderColor(color);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public VertexShape getShape() {
		return this.style.getShape();
	}
	
	/**
	 * 
	 * @param shape
	 */
	public Vertex setShape(VertexShape shape){
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setShape(shape);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        if (this.graph != null) {
            this.graph.notifyObservers();
        }

        return this;
	}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public VertexStyle getStyle() {
        return style;
    }

    public void setStyle(VertexStyle style) {
        this.style = style;
        if (this.graph != null) {
            this.graph.notifyObservers();
        }
    }

    public Collection<Edge> getEdges() {
        return edges;
    }
}
