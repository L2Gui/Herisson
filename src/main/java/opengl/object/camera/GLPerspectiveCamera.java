package opengl.object.camera;

import opengl.object.GLObject;
import opengl.utils.GLRay;
import org.lwjgl.util.vector.*;
import utils.MathUtils;
import utils.QuaternionUtils;

public class GLPerspectiveCamera extends GLObject implements IGLCamera {
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Matrix4f viewInvertedMatrix;
	private float fov;
	private float near;
	private float far;
	private Vector2f viewport;

    private Vector3f View;

	public GLPerspectiveCamera(float fov, float near, float far) {
		this.projectionMatrix = new Matrix4f();
		this.viewMatrix = new Matrix4f();
        this.viewInvertedMatrix = new Matrix4f();
		this.fov = fov;
		this.near = near;
		this.far = far;
		this.viewport = new Vector2f();

        this.View = new Vector3f(0.0f, 0.0f, 1.0f);

        float aspectRatio = 1.0f;

        float y_scale = MathUtils.coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = this.far - this.near;

        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
        this.projectionMatrix.m22 = -((this.far + this.near) / frustum_length);
        this.projectionMatrix.m23 = -1.0f;
        this.projectionMatrix.m32 = -((2.0f * this.near * this.far) / frustum_length);
        this.projectionMatrix.m33 = 0.0f;
	}
	
	@Override
	public Matrix4f getTransformationMatrix() {
		return Matrix4f.mul(this.projectionMatrix, this.viewMatrix, null);
	}

    @Override
    public void onModelChange() {
        super.onModelChange();

        this.View = QuaternionUtils.quaternionTransform(super.getRotation(), new Vector3f(0.0f, 0.0f, 1.0f));
        Vector3f target = Vector3f.add(super.getPosition(), this.View, null);
        this.lookAt(super.getPosition(), target, new Vector3f(0.0f, 1.0f, 0.0f));
    }

    public void lookToDirection(Vector3f direction) {
        super.setRotation(QuaternionUtils.quaternionLookRotation(direction));
    }

    public void lookAt(Vector3f Eye, Vector3f Center, Vector3f Up)
    {
        Vector3f X, Y, Z;

        Z = Vector3f.sub(Eye, Center, null);
        Z = Z.normalise(null);
        Y = Up;
        X = Vector3f.cross(Y, Z, null);

        Y = Vector3f.cross(Z, X, null);

        X.normalise(null);
        Y.normalise(null);

        this.viewMatrix.m00 = X.x;
        this.viewMatrix.m10 = X.y;
        this.viewMatrix.m20 = X.z;
        this.viewMatrix.m30 = -Vector3f.dot(X, Eye);
        this.viewMatrix.m01 = Y.x;
        this.viewMatrix.m11 = Y.y;
        this.viewMatrix.m21 = Y.z;
        this.viewMatrix.m31 = -Vector3f.dot(Y, Eye);
        this.viewMatrix.m02 = Z.x;
        this.viewMatrix.m12 = Z.y;
        this.viewMatrix.m22 = Z.z;
        this.viewMatrix.m32 = -Vector3f.dot(Z, Eye);
        this.viewMatrix.m03 = 0;
        this.viewMatrix.m13 = 0;
        this.viewMatrix.m23 = 0;
        this.viewMatrix.m33 = 1.0f;

        this.viewInvertedMatrix = (Matrix4f) new Matrix4f(this.viewMatrix).invert();
    }


    @Override
	public void updateViewport(float width, float height) {
		this.viewport.x = width;
		this.viewport.y = height;
		
		float aspectRatio = width / height;
        float y_scale = MathUtils.coTangent((float) Math.toRadians(this.fov / 2.0f));
        float x_scale = y_scale / aspectRatio;
        
        this.projectionMatrix.m00 = x_scale;
        this.projectionMatrix.m11 = y_scale;
	}

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
}
