package opengl.resource.object.drawable;

import opengl.resource.IGLResource;

import org.lwjgl.util.vector.Matrix4f;

public interface IGLDrawable extends IGLResource {
	void render(Matrix4f projectionViewMatrix);
}
