package opengl;

import org.lwjgl.util.vector.Matrix4f;

public class GLPerspectiveCamera extends GLObject implements IGLCamera {
	
	private Matrix4f projectionMatrix;
	private float fov;
	
	public GLPerspectiveCamera(float width, float height, float fov, float near, float far) {
		this.fov = fov;
		
        float aspectRatio = width / height;
         
        float y_scale = coTangent((float) Math.toRadians(fov / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far + near) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near * far) / frustum_length);
        projectionMatrix.m33 = 0;
	}
	
	@Override
	public void updateViewport(float width, float height) {
		float aspectRatio = width / height;
        float y_scale = coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
	}
	
	@Override
	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
	}
	
	private static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }

}
