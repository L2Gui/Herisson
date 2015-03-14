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
	private VertexView mainView;
	private VertexStyle style;
	private Graph graph;
    private GLColoredMesh mesh;
    private GLShader shader;
	private Vector3f position;
	private String label;


    public Vertex()
    {
        edges = new ArrayList<Edge>();
        mainView = null;
        style = null;
        graph = null;
        position = null;
        label = "";
    }


    public Vertex(Vector3f position, GLColoredMesh mesh, GLShader shader, Graph graph) {
        this.graph = graph;
        this.style = graph.getStyleManager().getDefaultVertexStyle();
        this.edges = new ArrayList<Edge>();
        this.position = position;
        this.mesh = mesh;
        this.shader = shader;
        this.mainView = new VertexView(this, mesh, shader);
    }

    public Vertex(Graph graph){
        this();
        this.graph = graph;
        this.style = this.graph.getStyleManager().getDefaultVertexStyle();
    }


    public VertexView getMainView() {
		return mainView;
	}

	public void setMainView(VertexView mainView) {
		this.mainView = mainView;
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
	public float getThickness(){
		return style.getBorderThickness();
	}
	
	/**
	 * 
	 * @param thickness est la nouvelle valeur de this.style.borderThickness
	 */
	public Vertex setThickness(float thickness){
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBorderThickness(thickness);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

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
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setSize(size);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBackgroundColor(){
		return this.style.getBackgroundColor();
	}
	
	/**
	 * 
	 * @param color
	 */
	public Vertex setBackgroundColor( Color color){
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBackgroundColor(color);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        return this;
	}
	
	/**
	 * 
	 * @return this.mainView.label (String)
	 */
	public String getLabel(){
		return mainView.getLabel();
	}
	
	/**
	 * 
	 * @param label est la nouvelle valeur de this.mainView.label
	 */
	public void setLabel(String label){
		mainView.setLabel(label);
	}
	
	/**
	 * 
	 * @return this.style.textColor (Color)
	 */
	public Color getTextColor(){
		return style.getTextColor();
	}
	
	/**
	 * 
	 * @param textColor est la nouvelle valeur de this.style.textColor
	 */
	public Vertex setTextColor(Color textColor){
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setTextColor(textColor);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        return this;
	}
	
	/**
	 * 
	 * @return this.style.font (Font)
	 */
	public Font getFont(){
		return style.getFont();
	}
	
	/**
	 * 
	 * @param font est la nouvelle valeur de this.style.font
	 */
	public Vertex setFont(Font font){
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setFont(font);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);

        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBorderColor(){
		return this.style.getBorderColor();
	}
	
	public Vertex setBorderColor(Color color){
		//TODO
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setBorderColor(color);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public VertexShape getShape()
	{
		return this.style.getShape();
	}
	
	/**
	 * 
	 * @param shape
	 */
	public Vertex setShape(VertexShape shape)
	{
        VertexStyle newStyle = new VertexStyle(this.style);
        this.style.decrementUsageCount();
        this.style.getVertices().remove(this);

        newStyle.setShape(shape);
        this.style = newStyle;
        this.style.incrementUsageCount();
        this.style.getVertices().add(this);

        graph.getStyleManager().addStyle(this.style);
        return this;
	}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
