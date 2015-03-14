package model;

import java.awt.Color;
import java.awt.Font;

import view.EdgeView;

public class Edge {
	
	private Vertex srcVertex;
	private Vertex dstVertex;
	private EdgeStyle style;
	private EdgeView mainView;
    private Graph graph;

    public Edge() {
        srcVertex = null;
        dstVertex = null;
        style = null;
        mainView = null;
    }

    /**
     *
     * @return
     */
    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
		
	/**
	 * 
	 * @return
	 */
	public Vertex getSrcVertex() {
		return srcVertex;
	}

	/**
	 * 
	 * @param srcVertex
	 */
	public void setSrcVertex(Vertex srcVertex) {
		this.srcVertex = srcVertex;
	}

	/**
	 * 
	 * @return
	 */
	public Vertex getDstVertex() {
		return dstVertex;
	}

	/**
	 * 
	 * @param dstVertex
	 */
	public void setDstVertex(Vertex dstVertex) {
		this.dstVertex = dstVertex;
	}

	
	// Setter and getter for field : style
	
	/**
	 * 
	 * @return this.style (EdgeStyle)
	 */
	public EdgeStyle getStyle() {
		return style;
	}

	/**
	 * 
	 * @param style est la nouvelle valeur de this.style
	 */
	public void setStyle(EdgeStyle style) {
		this.style = style;
	}

	//Setter and getter for field : mainView
	
	/**
	 * 
	 * @return this.mainView (EdgeView)
	 */
	public EdgeView getMainView() {
		return mainView;
	}

	/**
	 * 
	 * @param mainView est nouvelle valeur de this.mainView
	 */
	public void setMainView(EdgeView mainView) {
		this.mainView = mainView;
	}

	// Modification methods for fields in style
	
	/**
	 * 
	 * @return this.style.color (Color)
	 */
	public Color getColor(){
		return style.getColor();
	}
	
	/**
	 * 
	 * @param color est la nouvelle valeur de this.style.color
	 */
	public Edge setColor(Color color){
		//TODO
        EdgeStyle newStyle = new EdgeStyle(this.style);
        this.style.decrementUsageCount();

        newStyle.setColor(color);
        this.style = newStyle;
        this.style.incrementUsageCount();

        graph.getStyleManager().addStyle(this.style);

        return this;
	}
	
	/**
	 * 
	 * @return this.style.thickness (float)
	 */
	public float getThickness(){
		return style.getThickness();
	}
	
	/**
	 * 
	 * @param thickness est la nouvelle valeur de this.style.thickness
	 */
	public Edge setThickness(float thickness){
		//TODO

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
	public Edge setTextColor(Color textColor){
		//TODO
        EdgeStyle newStyle = new EdgeStyle(this.style);
        this.style.decrementUsageCount();

        newStyle.setTextColor(textColor);
        this.style = newStyle;
        this.style.incrementUsageCount();

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
	public Edge setFont(Font font){
		//TODO
        EdgeStyle newStyle = new EdgeStyle(this.style);
        this.style.decrementUsageCount();

        newStyle.setFont(font);
        this.style = newStyle;
        this.style.incrementUsageCount();

        graph.getStyleManager().addStyle(this.style);

        return this;
	}	
}
