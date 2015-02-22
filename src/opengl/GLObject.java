package opengl;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class GLObject implements IGLObject {
	private Matrix4f modelMatrix;
	private Vector3f position;
	private Vector3f rotationAxis;
	private Vector3f scale;
	private float rotationAngle;
	private boolean modified;
	
	public GLObject() {
		this.modelMatrix = new Matrix4f();
		this.position = new Vector3f();
		this.rotationAxis = new Vector3f();
		
		this.rotationAngle = 0.0f;
		this.modified = false;
	}
	
	public boolean isModified() {
		return this.modified;
	}
	
	public Matrix4f getModelMatrix() {
		return this.modelMatrix;
	}
	
	@Override
	public void setPosition(Vector3f pos) {
		this.position = pos;
		this.modified = true;
	}

	@Override
	public void setRotation(float angle, Vector3f axis) {
		this.rotationAngle = angle;
		this.rotationAxis = axis;
		this.modified = true;
	}

	@Override
	public void setScale(Vector3f scale) {
		this.scale = scale;
		this.modified = true;
	}
	
	protected void computeMatrix() {
		this.modelMatrix.setIdentity();
		this.modelMatrix.translate(this.position);
		this.modelMatrix.rotate(this.rotationAngle, this.rotationAxis);
		this.modelMatrix.scale(this.scale);
		this.modified = false;
	}
}
