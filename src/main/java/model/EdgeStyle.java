package model;

import java.awt.*;


public class EdgeStyle implements IStyle {
	
	private float thickness;
	private Color color; 
	private Color textColor;
	private Font font;
	private LineStyle style;
    private int usageCount;

    public EdgeStyle(){
        this.thickness = 1;
        this.color = Color.BLACK;
        this.textColor = Color.BLACK;
        this.font = null;
        this.style = LineStyle.SOLID;
        this.usageCount = 0;
    }

    public EdgeStyle(EdgeStyle edgeStyle){
        this();
        this.thickness = edgeStyle.thickness;
        this.color = edgeStyle.color;
        this.textColor = edgeStyle.textColor;
        this.font = edgeStyle.font;
        this.style = edgeStyle.style;
        usageCount = 0;
    }

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
	
	public EdgeStyle setStyle(LineStyle style) {
		this.style = style;
        return this;
	}

    @Override
    public int getUsageCount() {
        return usageCount;
    }

    @Override
    public EdgeStyle setUsageCount(int count) {
        this.usageCount = count;
        return this;
    }

    @Override
    public EdgeStyle incrementUsageCount() {
        usageCount++;
        return this;
    }

    @Override
    public EdgeStyle decrementUsageCount() {
        usageCount--;
        return this;
    }
}
