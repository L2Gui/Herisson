package opengl.resource.object;

import opengl.resource.IGLResource;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public interface IGLObject {
	Vector3f getPosition();
	Quaternion getRotation();
	Vector3f getScale();

	void setPosition(Vector3f position);
    void setPosition(float x, float y, float z);

	void setRotation(float angle, Vector3f axis);
    void setRotation(float angle, float xAxis, float yAxis, float zAxis);

	void setScale(Vector3f scale);
    void setScale(float x, float y, float z);

	void translate(Vector3f pos);
    void translate(float x, float y, float z);

	void rotate(float angle, Vector3f axis);
    void rotate(float angle, float xAxis, float yAxis, float zAxis);

	void scale(Vector3f scale);
    void scale(float x, float y, float z);

	void computeMatrix();
	Matrix4f getModelMatrix();
}
