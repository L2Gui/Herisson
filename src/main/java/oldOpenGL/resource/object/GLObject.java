package oldOpenGL.resource.object;

import oldOpenGL.resource.object.IGLObject;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import utils.MathUtils;

public abstract class GLObject implements IGLObject {
	private Matrix4f modelMatrix;
	private Vector3f position;
	private Quaternion rotation;
	private Vector3f scale;
	
	public GLObject() {
		this.modelMatrix = new Matrix4f();
		this.position = new Vector3f();
		this.rotation = new Quaternion();
		this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public Vector3f getPosition() {
		return this.position;
	}
	
	@Override
	public Quaternion getRotation() {
		return this.rotation;
	}
	
	@Override
	public Vector3f getScale() {
		return this.scale;
	}
	
	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
		this.computeMatrix();
	}
	
	@Override
	public void setRotation(float angle, Vector3f axis) {
		float radHalfAngle = (angle * (float) Math.PI) / 360.0f;
		float sinVal = (float) Math.sin(radHalfAngle);
		float cosVal = (float) Math.cos(radHalfAngle);
		float xVal = axis.x * sinVal;
		float yVal = axis.y * sinVal;
		float zVal = axis.z * sinVal;
		this.rotation = new Quaternion(xVal, yVal, zVal, cosVal);
		this.computeMatrix();
	}
	
	@Override
	public void setScale(Vector3f scale) {
		this.scale = scale;
		this.computeMatrix();
	}
	
	@Override
	public void translate(Vector3f pos) {
		this.position.translate(pos.x, pos.y, pos.z);
		this.computeMatrix();
	}

	@Override
	public void rotate(float angle, Vector3f axis) {
		float radHalfAngle = (angle * (float) Math.PI) / 360.0f;
		float sinVal = (float) Math.sin(radHalfAngle);
		float cosVal = (float) Math.cos(radHalfAngle);
		float xVal = axis.x * sinVal;
		float yVal = axis.y * sinVal;
		float zVal = axis.z * sinVal;
		Quaternion rotation = new Quaternion(xVal, yVal, zVal, cosVal);
		Quaternion.mul(rotation, this.rotation, this.rotation);
		this.computeMatrix();
	}

	@Override
	public void scale(Vector3f scale) {
		this.scale.x *= scale.x;
		this.scale.y *= scale.y;
		this.scale.z *= scale.z;
		this.computeMatrix();
	}
	
	@Override
	public void computeMatrix() {
		this.modelMatrix.setIdentity();
		this.modelMatrix.scale(scale);
		Matrix4f.mul(this.modelMatrix, MathUtils.quaternionToMatrix(this.rotation), this.modelMatrix);
		this.modelMatrix.translate(this.position);
	}
	
	@Override
	public Matrix4f getModelMatrix() {
		return this.modelMatrix;
	}
}
