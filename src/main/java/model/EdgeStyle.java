package model;

import java.awt.Color;
import java.awt.Font;


public class EdgeStyle implements IStyle {
	
	private float thickness;
	private Color color; 
	private Color textColor;
	private Font font;
	private LineStyle style;
    private int usageCount;
	
	public float getThickness() {
		return thickness;
	}
	
	public EdgeStyle setThickness(float thickness) {
		this.thickness = thickness;
        return this;
	}
	
	public Color getColor() {
		return color;
	}
	
	public EdgeStyle setColor(Color color) {
		this.color = color;
        return this;
	}
	
	public Color getTextColor() {
		return textColor;
	}
	
	public EdgeStyle setTextColor(Color textColor) {
		this.textColor = textColor;
        return this;
	}
	
	public Font getFont() {
		return font;
	}
	
	public EdgeStyle setFont(Font font) {
		this.font = font;
        return this;
	}
	
	public LineStyle getStyle() {
		return style;
	}
	
	public void setStyle(LineStyle style) {
		this.style = style;
	}

    @Override
    public int getUsageCount() {
        return usageCount;
    }

    @Override
    public void setUsageCount(int count) {
        this.usageCount = count;
    }
}
