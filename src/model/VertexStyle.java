package model;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;

/*
 * TODO 
 * ajouter methodes add/remove vertex
 * 
 */

public class VertexStyle {
	private VertexShape shape;
	private Collection<Vertex> vertices;
	private float size;
	private float borderThickness;
	private Color backgroundColor;
	private Color textColor;
	private Color borderColor;
	private Font font;
	
	VertexStyle(){
		setVertices(new ArrayList<Vertex>());
	}

	public VertexShape getShape() {
		return shape;
	}

	public void setShape(VertexShape shape) {
		this.shape = shape;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getBorderThickness() {
		return borderThickness;
	}

	public void setBorderThickness(float borderThickness) {
		this.borderThickness = borderThickness;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Collection<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(Collection<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	
	
	
}
