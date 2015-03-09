package oldOpenGL.resource.object.drawable;

import oldOpenGL.resource.IGLResource;
import oldOpenGL.utils.GLRay;

import org.lwjgl.util.vector.Matrix4f;

public interface IGLDrawable extends IGLResource {
	void render(Matrix4f projectionViewMatrix);
	boolean isIntersected(GLRay ray);
	float getDistance(GLRay ray);
}
