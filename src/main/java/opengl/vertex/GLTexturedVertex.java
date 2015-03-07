package opengl.vertex;

import utils.ArrayUtils;

public class GLTexturedVertex extends GLColoredVertex {
	public static final int textureElementCount = 2;
	public static final int textureByteCount = textureElementCount * elementBytes;
	public static final int textureByteOffset = colorByteOffset + colorByteCount;
	public static final int elementCount = GLColoredVertex.elementCount + textureElementCount;	
	public static final int stride = GLColoredVertex.stride + textureByteCount;
	
	private float[] st = new float[] {0f, 0f};
	
	public void setST(float s, float t) {
		this.st = new float[] {s, t};
	}
	
	public float[] getST() {
		return new float[] {this.st[0], this.st[1]};
	}
	
	@Override
	public float[] getElements() {
		return ArrayUtils.concat(super.getElements(), this.st);
	}
}
