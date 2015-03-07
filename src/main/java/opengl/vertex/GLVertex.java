package opengl.vertex;

public class GLVertex {
	public static final int elementBytes = 4;
	public static final int positionElementCount = 4;
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int positionByteOffset = 0;
	public static final int elementCount = positionElementCount;
	public static final int stride = positionBytesCount;
	
	private float[] position = new float[] {0f, 0f, 0f, 1f};
	
	public void setPosition(float x, float y, float z) {
		this.setPosition(x, y, z, 1f);
	}
	
	public void setPosition(float x, float y, float z, float w) {
		this.position = new float[] {x, y, z, w};
	}
	
	public float[] getPosition() {
		return new float[] {this.position[0], this.position[1], this.position[2], this.position[3]};
	}
	
	public void setX(float x) {
		this.position[0] = x;
	}
	
	public void setY(float y) {
		this.position[1] = y;
	}
	
	public void setZ(float z) {
		this.position[2] = z;
	}
	
	public float getX() {
		return this.position[0];
	}
	
	public float getY() {
		return this.position[1];
	}
	
	public float getZ() {
		return this.position[2];
	}
	
	public float[] getElements() {		
		return this.position.clone();
	}
}
