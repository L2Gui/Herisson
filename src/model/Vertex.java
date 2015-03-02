package model;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;

import org.lwjgl.util.vector.Vector3f;

import view.VertexView;

public class Vertex {
	private Collection<Edge> edges;
	private VertexView mainView;
	private VertexStyle style;
	private Graph graph;
	private Vector3f position;
	private String label;
	
	
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

	
	/**
	 * 
	 * @return this.style.borderThickness (float)
	 */
	public float getThickness()
	{
		return style.getBorderThickness();
	}
	
	/**
	 * 
	 * @param thickness est la nouvelle valeur de this.style.borderThickness
	 */
	public void setThickness(float thickness)
	{
		//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public float getSize()
	{
		return this.style.getSize();
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setSize(float size)
	{
		//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBackgroundColor()
	{
		return this.style.getBackgroundColor();
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setBackgroundColor( Color color)
	{
		//TODO
	}
	
	/**
	 * 
	 * @return this.mainView.label (String)
	 */
	public String getLabel()
	{
		return mainView.getLabel();
	}
	
	/**
	 * 
	 * @param label est la nouvelle valeur de this.mainView.label
	 */
	public void setLabel(String label)
	{
		mainView.setLabel(label);
	}
	
	/**
	 * 
	 * @return this.style.textColor (Color)
	 */
	public Color getTextColor()
	{
		return style.getTextColor();
	}
	
	/**
	 * 
	 * @param textColor est la nouvelle valeur de this.style.textColor
	 */
	public void setTextColor(Color textColor)
	{
		//TODO
	}
	
	/**
	 * 
	 * @return this.style.font (Font)
	 */
	public Font getFont()
	{
		return style.getFont();
	}
	
	/**
	 * 
	 * @param font est la nouvelle valeur de this.style.font
	 */
	public void setFont(Font font)
	{
		//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public Color getBorderColor()
	{
		return this.style.getBorderColor();
	}
	
	public void setBorderColor(Color color)
	{
		//TODO
	}
	
}
