package model;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;

import main.App;
import opengl.resource.GLShader;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.resource.object.mesh.GLMesh;
import org.lwjgl.util.vector.Vector3f;

import view.VertexView;

public class Vertex {
	private Collection<Edge> edges;
	private VertexStyle style;
	private Graph graph;
    private GLColoredMesh mesh;
    private GLShader shader;
	private Vector3f position;
	private String label;

    public Vertex() {
        edges = new ArrayList<Edge>();
        style = null;
        graph = null;
        position = null;
        label = "";
    }

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
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
	public void setThickness(float thickness) {
		//TODO
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
	public void setSize(float size) {
		//TODO
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
	public void setBackgroundColor( Color color) {
		//TODO
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
	public void setTextColor(Color textColor) {
		//TODO
	}
	
	/**
	 * 
	 * @return this.style.font (Font)
	 */
	public Font getFont() {
		return style.getFont();
	}
	
	/**
	 * 
	 * @param font est la nouvelle valeur de this.style.font
	 */
	public void setFont(Font font) {
		//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBorderColor() {
		return this.style.getBorderColor();
	}
	
	public void setBorderColor(Color color) {
		//TODO
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
	public void setShape(VertexShape shape) {
		this.style.setShape(shape);
	}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
