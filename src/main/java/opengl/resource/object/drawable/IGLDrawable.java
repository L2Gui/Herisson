package opengl.resource.object.drawable;

import opengl.resource.IGLResource;
import opengl.utils.GLRay;

import org.lwjgl.util.vector.Matrix4f;

public interface IGLDrawable {
	void render(Matrix4f projectionViewMatrix);
	boolean isIntersected(GLRay ray);
	float getDistance(GLRay ray);
}
