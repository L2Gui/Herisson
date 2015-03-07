package opengl.resource.object;

import opengl.resource.IGLResource;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public interface IGLObject extends IGLResource {
	void setPosition(Vector3f pos);
	void setRotation(float angle, Vector3f axis);
	void setScale(Vector3f scale);
	Matrix4f getModelMatrix();
}
