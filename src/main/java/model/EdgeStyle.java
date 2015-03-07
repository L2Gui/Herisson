package model;

import java.awt.Color;
import java.awt.Font;


public class EdgeStyle {
	
	private float thickness;
	private Color color; 
	private Color textColor;
	private Font font;
	private LineStyle style;
	
	public float getThickness() {
		return thickness;
	}
	
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	public Font getFont() {
		return font;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public LineStyle getStyle() {
		return style;
	}
	
	public void setStyle(LineStyle style) {
		this.style = style;
	}
}
