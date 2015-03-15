package oldOpenGL.resource.object.camera;

import oldOpenGL.resource.object.IGLObject;
import oldOpenGL.utils.GLRay;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

public interface IGLCamera extends IGLObject {
	Matrix4f getProjectionViewMatrix();
	void updateViewport(float width, float height);
	GLRay getCursorRay(Vector2f cursorLocation);
}
