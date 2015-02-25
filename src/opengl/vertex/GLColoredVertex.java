package opengl.vertex;

import java.awt.Color;

import utils.ArrayUtils;

public class GLColoredVertex extends GLVertex {
	private Color color;
	
	public static final int colorElementCount = 4;
	public static final int colorByteCount = colorElementCount * elementBytes;
	public static final int colorByteOffset = positionByteOffset + positionBytesCount;
	public static final int elementCount = GLVertex.elementCount + colorElementCount;	
	public static final int stride = GLVertex.stride + colorByteCount;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public float[] getElements() {
		return ArrayUtils.concat(super.getElements(), this.colorToArray());
	}
	
	private float[] colorToArray() {
		return new float[] {
			(float) this.color.getRed() / 255.0f,
			(float) this.color.getGreen() / 255.0f,
			(float) this.color.getBlue() / 255.0f,
			(float) this.color.getAlpha() / 255.0f
		};
	}
}
