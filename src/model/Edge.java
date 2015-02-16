package model;

import java.awt.Color;

import view.EdgeView;

public class Edge {
	
	private Vertex srcVertex;
	
	private Vertex dstVertex;
	
	private EdgeStyle style;
	
	private EdgeView mainView;
	
	private Graph graph;
	
	/**
	 * 
	 */
	public boolean isTurnedToSource;
		
	/**
	 * 
	 */
	public boolean isTurnedToDest;
	
	// Setter and getter for field : style
	
	/**
	 * 
	 * @return
	 */
	public EdgeStyle getStyle() {
		return style;
	}

	/**
	 * 
	 * @param style
	 */
	public void setStyle(EdgeStyle style) {
		this.style = style;
	}

	//Setter and getter for field : mainView
	
	/**
	 * 
	 * @return
	 */
	public EdgeView getMainView() {
		return mainView;
	}

	/**
	 * 
	 * @param mainView
	 */
	public void setMainView(EdgeView mainView) {
		this.mainView = mainView;
	}

	// Modification methods for fields in style
	
	public Color getColor()
	{
		return style.color;
	}
	
	public void setColor(Color color)
	{
		//TODO
	}
	
	public float getThickness()
	{
		return style.thickness;
	}
	
	public void setThickness(float thickness)
	{
		//TODO
	}
	
	public String getLabel()
	{
		return mainView.getLabel();
	}
	
	public void setLabel(String label)
	{
		mainView.setLabel(label);s
	}
	
	public Color getTextColor()
	{
		return style.color;
	}
	
	public void setTextColor(Color textColor)
	{
		//TODO
	}
	
	public Font getFont()
	{
		return style.font;
	}
	
	public void setFont(Font font)
	{
		//TODO
	}
	
	
	
	
	
	
}
