package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/*
 * TODO 
 * ajouter methodes add/remove vertex
 * 
 */

public class VertexStyle implements IStyle {

    private long id;
    private static long idCount = 0;

	private VertexShape shape;
	private Collection<Vertex> vertices;
	private float size;
	private float borderThickness;
	private Color backgroundColor;
	private Color textColor;
	private Color borderColor;
	private Font font;
    private int usageCount;
	
	public VertexStyle(){
        id = idCount;
        idCount++;

		vertices = new ArrayList<Vertex>();
        this.shape = VertexShape.SQUARE;
        this.size = 4;
        this.borderThickness = 1;
        this.backgroundColor = Color.GRAY;
        this.textColor = Color.BLACK;
        this.borderColor = Color.BLACK;
        this.font = null;
        this.usageCount = 0;
	}

    public VertexStyle(VertexStyle vertexStyle){
        this();
        this.shape = vertexStyle.shape;
        this.size = vertexStyle.size;
        this.borderThickness = vertexStyle.borderThickness;
        this.backgroundColor = vertexStyle.backgroundColor;
        this.textColor = vertexStyle.textColor;
        this.borderColor = vertexStyle.borderColor;
        this.font = vertexStyle.font;
        this.usageCount = 0;
    }

	public VertexShape getShape() {
		return shape;
	}

	public VertexStyle setShape(VertexShape shape) {
		this.shape = shape;
        return this;
	}

	public float getSize() {
		return size;
	}

	public VertexStyle setSize(float size) {
		this.size = size;
        return this;
	}

	public float getBorderThickness() {
		return borderThickness;
	}

	public VertexStyle setBorderThickness(float borderThickness) {
		this.borderThickness = borderThickness;
        return this;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public VertexStyle setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
        return this;
	}

	public Color getTextColor() {
		return textColor;
	}

	public VertexStyle setTextColor(Color textColor) {
		this.textColor = textColor;
        return this;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public VertexStyle setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
        return this;
	}

	public Font getFont() {
		return font;
	}

	public VertexStyle setFont(Font font) {
		this.font = font;
        return this;
	}

	public Collection<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(Collection<Vertex> vertices) {
		this.vertices = vertices;
	}


    @Override
    public int getUsageCount() {
        return usageCount;
    }

    @Override
    public VertexStyle setUsageCount(int count) {
        this.usageCount = count;
        return this;
    }

    @Override
    public VertexStyle incrementUsageCount() {
        usageCount++;
        return this;
    }

    @Override
    public VertexStyle decrementUsageCount() {
        usageCount--;
        return this;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public boolean isEquals(VertexStyle style) {
        if (!this.borderColor.equals(style.borderColor)){
            return false;
        }
        if (this.size != style.size){
            return false;
        }
        if (!this.textColor.equals(style.textColor)){
            return false;
        }
        if (this.shape != style.shape){
            return false;
        }
        if (!this.backgroundColor.equals(style.backgroundColor)){
            return false;
        }
        if (!this.font.equals(style.font)){
            return false;
        }
        if (this.borderThickness != style.borderThickness){
            return false;
        }
        return true;
    }


}
