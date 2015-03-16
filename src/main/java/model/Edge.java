package model;

import java.awt.*;

public class Edge {
	private Vertex srcVertex;
	private Vertex dstVertex;
    private String label;
	private EdgeStyle style;
    private Graph graph;

    public Edge() {
        srcVertex = null;
        dstVertex = null;
        this.label = "";
    }


    public Edge(Graph graph) {
        this();
        this.graph = graph;
        this.style = graph.getStyleManager().getDefaultEdgeStyle();
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
        this.style = graph.getStyleManager().getDefaultEdgeStyle();
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
        EdgeStyle newStyle = new EdgeStyle(this.style);
        this.style.decrementUsageCount();

        newStyle.setThickness(thickness);
        this.style = newStyle;
        this.style.incrementUsageCount();

        graph.getStyleManager().addStyle(this.style);

        return this;
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
        EdgeStyle newStyle = new EdgeStyle(this.style);
        this.style.decrementUsageCount();

        newStyle.setFont(font);
        this.style = newStyle;
        this.style.incrementUsageCount();

        graph.getStyleManager().addStyle(this.style);

        return this;
	}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
