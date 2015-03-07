package opengl.resource.object.camera;

import opengl.resource.object.GLObject;
import opengl.utils.GLRay;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GLPerspectiveCamera extends GLObject implements IGLCamera {
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Matrix4f viewInvertedMatrix;
	private float fov;
	private float near;
	private float far;
	private boolean isInitialized;
	private Vector2f viewport;
	
	public GLPerspectiveCamera(float fov, float near, float far) {
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
		this.fov = fov;
		this.near = near;
		this.far = far;
		this.isInitialized = false;
		this.viewport = new Vector2f();
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
		return Matrix4f.mul(this.projectionMatrix, this.viewMatrix, null);
	}
	
	@Override
	public void translate(Vector3f pos) {
		super.translate(pos);
		this.viewMatrix.translate(pos.negate(null));
		this.viewInvertedMatrix = (Matrix4f) new Matrix4f(this.viewMatrix).invert();
	};
	
	@Override
	public void rotate(float angle, Vector3f axis) {
		super.rotate(angle, axis);
		this.viewMatrix.rotate(-angle, axis);
		this.viewInvertedMatrix = (Matrix4f) new Matrix4f(this.viewMatrix).invert();
	};
	
	@Override
	public void scale(Vector3f scale) {
		super.scale(scale);
		this.viewMatrix.scale(scale);
		this.viewInvertedMatrix = (Matrix4f) new Matrix4f(this.viewMatrix).invert();
	};
	
	@Override
	public void updateViewport(float width, float height) {
		this.viewport.x = width;
		this.viewport.y = height;
		
		float aspectRatio = width / height;
        float y_scale = coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
	}
	
	@Override
	public GLRay getCursorRay(Vector2f cursorLocation) {
		int mouseX = (int) cursorLocation.x;
	    int mouseY = (int) (this.viewport.y - cursorLocation.y);
	    
	    float aspectRatio = this.viewport.x / this.viewport.y;

	    //get the mouse position in screenSpace coords
	    double screenSpaceX = ((float) mouseX / (this.viewport.x / 2) - 1.0f) * aspectRatio;
	    double screenSpaceY = (1.0f - (float) mouseY / (this.viewport.y / 2));

	    double viewRatio = Math.tan(((float) Math.PI / (180.f/this.fov) / 2.00f));

	    screenSpaceX = screenSpaceX * viewRatio;
	    screenSpaceY = screenSpaceY * viewRatio;

	    //Find the far and near camera spaces
	    Vector4f cameraSpaceNear = new Vector4f((float) (screenSpaceX * this.near), (float) (screenSpaceY * this.near), (float) (-this.near), 1);
	    Vector4f cameraSpaceFar = new Vector4f((float) (screenSpaceX * this.far), (float) (screenSpaceY * this.far), (float) (-this.far), 1);


	    //Unproject the 2D window into 3D to see where in 3D we're actually clicking
	    Vector4f worldSpaceNear = new Vector4f();
	    Matrix4f.transform(this.viewInvertedMatrix, cameraSpaceNear, worldSpaceNear);
	    Vector4f worldSpaceFar = new Vector4f();
	    Matrix4f.transform(this.viewInvertedMatrix, cameraSpaceFar, worldSpaceFar);

	    //calculate the ray position and direction
	    Vector3f rayPosition = new Vector3f(worldSpaceNear.x, worldSpaceNear.y, worldSpaceNear.z);
	    Vector3f rayDirection = new Vector3f(worldSpaceFar.x - worldSpaceNear.x, worldSpaceFar.y - worldSpaceNear.y, worldSpaceFar.z - worldSpaceNear.z);

	    rayDirection.normalise();

	    return new GLRay(rayPosition, rayDirection);
	}
	
	private static float coTangent(float angle) {
        return (float)(1f / Math.tan(angle));
    }
}
