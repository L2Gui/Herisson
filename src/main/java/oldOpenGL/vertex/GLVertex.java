package oldOpenGL.vertex;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GLVertex {
	public static final int elementBytes = 4;
	public static final int positionElementCount = 4;
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int positionByteOffset = 0;
	public static final int elementCount = positionElementCount;
	public static final int stride = positionBytesCount;
	
	private Vector4f position = new Vector4f(0f, 0f, 0f, 1.0f);
	
	public void setPosition(Vector3f position) {
		this.position = new Vector4f(position.x, position.y, position.z, 1.0f);
	}
	
	public void setPosition(Vector4f position) {
		this.position = position;
	}
	
	public Vector4f getPosition() {
		return this.position;
	}
	
	public void setX(float x) {
		this.position.x = x;
	}
	
	public void setY(float y) {
		this.position.y = y;
	}
	
	public void setZ(float z) {
		this.position.z = z;
	}
	
	public float getX() {
		return this.position.x;
	}
	
	public float getY() {
		return this.position.y;
	}
	
	public float getZ() {
		return this.position.z;
	}
	
	public float[] getElements() {		
		return new float[] {
			this.position.x,
			this.position.y,
			this.position.z,
			this.position.w
		};
	}
}
