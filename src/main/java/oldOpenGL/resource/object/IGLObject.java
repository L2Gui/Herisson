package oldOpenGL.resource.object;

import oldOpenGL.resource.IGLResource;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public interface IGLObject extends IGLResource {
	Vector3f getPosition();
	Quaternion getRotation();
	Vector3f getScale();
	
	void setPosition(Vector3f position);
	void setRotation(float angle, Vector3f axis);
	void setScale(Vector3f scale);
	
	void translate(Vector3f pos);
	void rotate(float angle, Vector3f axis);
	void scale(Vector3f scale);
	
	void computeMatrix();
	Matrix4f getModelMatrix();
}
