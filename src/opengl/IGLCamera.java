package opengl;

import org.lwjgl.util.vector.Matrix4f;

public interface IGLCamera extends IGLObject {
	Matrix4f getProjectionMatrix();
	void updateViewport(float width, float height);
}
