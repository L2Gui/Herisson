package opengl.resource.object.camera;

import opengl.resource.object.GLObject;

import org.lwjgl.util.vector.Matrix4f;

public class GLPerspectiveCamera extends GLObject implements IGLCamera {
	private Matrix4f projectionMatrix;
	private float fov;
	private float near;
	private float far;
	private boolean isInitialized;
	
	public GLPerspectiveCamera(float fov, float near, float far) {
		this.projectionMatrix = new Matrix4f();
		this.fov = fov;
		this.near = near;
		this.far = far;
		this.isInitialized = false;
	}

	@Override
	public void init() {
		float aspectRatio = 1.0f;
        
        float y_scale = coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = this.far - this.near;
        
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
        this.projectionMatrix.m22 = -((this.far + this.near) / frustum_length);
        this.projectionMatrix.m23 = -1.0f;
        this.projectionMatrix.m32 = -((2.0f * this.near * this.far) / frustum_length);
        this.projectionMatrix.m33 = 0.0f;
        
        this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}
	
	@Override
	public Matrix4f getProjectionViewMatrix() {
		return Matrix4f.mul(this.projectionMatrix, super.getModelMatrix(), null);
	}
	
	@Override
	public void updateViewport(float width, float height) {
		float aspectRatio = width / height;
        float y_scale = coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
	}
	
	private static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }
}
