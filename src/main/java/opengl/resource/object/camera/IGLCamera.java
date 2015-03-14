package opengl.resource.object.camera;

import opengl.resource.object.IGLObject;
import org.lwjgl.util.vector.Matrix4f;

public interface IGLCamera extends IGLObject {
	Matrix4f getTransformationMatrix();
	void updateViewport(float width, float height);
}
