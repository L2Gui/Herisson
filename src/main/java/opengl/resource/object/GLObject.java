package opengl.resource.object;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import utils.MathUtils;

public abstract class GLObject implements IGLObject {
	private Matrix4f modelMatrix;
	private Vector3f position;
	private Quaternion rotation;


	public GLObject() {
		this.modelMatrix = new Matrix4f();
		this.position = new Vector3f();
		this.rotation = new Quaternion();
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
    public void setPosition(Vector3f position) {
        this.position = position;
        this.computeMatrix();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.setPosition(new Vector3f(x, y, z));
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
    public void setRotation(float angle, float xAxis, float yAxis, float zAxis) {
        this.setRotation(angle, new Vector3f(xAxis, yAxis, zAxis));
    }

    @Override
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
        this.computeMatrix();
    }

    @Override
    public void translate(Vector3f translation) {
        this.position.translate(translation.x, translation.y, translation.z);
        this.computeMatrix();
    }

    @Override
    public void translate(float x, float y, float z) {
        this.translate(new Vector3f(x, y, z));
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
    public void rotate(float angle, float xAxis, float yAxis, float zAxis) {
        this.rotate(angle, new Vector3f(xAxis, yAxis, zAxis));
    }

    @Override
    public void rotate(Quaternion rotation) {
        Quaternion.mul(this.rotation, rotation, this.rotation);
        this.computeMatrix();
    }

	@Override
     public void computeMatrix() {
        this.modelMatrix.setIdentity();
        Matrix4f.mul(this.modelMatrix, MathUtils.quaternionToMatrix(this.rotation), this.modelMatrix);
        this.modelMatrix.translate(this.position);
    }

    @Override
	public Matrix4f getModelMatrix() {
		return this.modelMatrix;
	}
}
