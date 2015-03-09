package opengl.resource.object.camera;

import opengl.resource.object.IGLObject;
import opengl.utils.GLRay;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public interface IGLCamera extends IGLObject {
	Matrix4f getProjectionViewMatrix();
    Quaternion getViewRotation();
	void updateViewport(float width, float height);
	GLRay getCursorRay(Vector2f cursorLocation);
    void lookAtRH( Vector3f eye, Vector3f target, Vector3f up );
}
