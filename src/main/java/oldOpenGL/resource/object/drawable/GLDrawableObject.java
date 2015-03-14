package oldOpenGL.resource.object.drawable;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import oldOpenGL.GLHelper;
import oldOpenGL.resource.GLShader;
import oldOpenGL.resource.object.GLObject;
import oldOpenGL.resource.object.GLObjectUsage;
import oldOpenGL.utils.GLRay;
import oldOpenGL.vertex.GLVertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public abstract class GLDrawableObject extends GLObject implements IGLDrawable {
	protected Mutex mutex;
	private GLObjectUsage state;
	private GLShader shader;
	protected List<? extends GLVertex> vertices;
	private int[] indices;
	private int indicesCount;
	private int vaid;
	private int vid;
	private int iid;
	private boolean isInitialized;
	
	public GLDrawableObject() {
		this.mutex = new Mutex();
	}
	
	protected void setupObject(GLShader shader, List<? extends GLVertex> vertices, int[] indices, GLObjectUsage usage) {
		this.shader = shader;
		this.vertices = vertices;
		this.state = usage;
		this.indices = indices;
		this.indicesCount = indices.length;
		this.isInitialized = false;
	}
	
	@Override
	public void init() {
		FloatBuffer fb = this.getVerticesBuffer();
		IntBuffer ib = GLHelper.makeIntBuffer(this.indices);
		
		this.vid = GLHelper.makeBuffer(GL15.GL_ARRAY_BUFFER, fb, this.state.getUsage());
    	this.iid = GLHelper.makeBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ib, this.state.getUsage());
		
    	this.vaid = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vaid);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
    	
    	this.attribVerticesPointer();
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		this.isInitialized = true;
	}
	
	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}
	
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vaid);
	}
	
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	@Override
	public void render(Matrix4f projectionViewMatrix) {
		this.shader.bind();
		
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		Matrix4f transformMatrix = Matrix4f.mul(projectionViewMatrix, super.getModelMatrix(), null);
		transformMatrix.store(matrix44Buffer);
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("transformMatrix"), false, matrix44Buffer);

		GL30.glBindVertexArray(this.vaid);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
		
    	this.enableVerticesPointer();
    	
    	try {
    		this.mutex.acquire();
        	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iid);
        	GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.indicesCount, this.indicesCount, GL11.GL_UNSIGNED_INT, 0);
        	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        	this.mutex.release();
    	} catch(InterruptedException ex) {
    		ex.printStackTrace();
    	}
    	
    	this.disableVerticesPointer();
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	
    	this.shader.unbind();
	}
	
	protected void updateVertices(List<? extends GLVertex> vertices, int elementCount) {
		if (this.vertices.size() > vertices.size()) {
			System.out.println("Warning: there's less vertices than before: indices could make the app crash");
		}
		
		this.bind();
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(elementCount * vertices.size());
		
		for (GLVertex vertex : vertices) {
			verticesBuffer.put(vertex.getElements());
		}
		verticesBuffer.flip();
		
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, verticesBuffer);
		
		this.unbind();
		
		this.vertices = vertices;
	}
	
	protected abstract int getVertexStride();
	protected abstract FloatBuffer getVerticesBuffer();
	protected abstract void attribVerticesPointer();
	protected abstract void enableVerticesPointer();
	protected abstract void disableVerticesPointer();
	
	@Override
	public void computeMatrix() {
		try {
			this.mutex.acquire();
			super.computeMatrix();
			this.mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isIntersected(GLRay ray) {
		boolean found = false;
		for (int i = 0;i < this.indices.length && !found;i += 3) {
			Vector4f v0, v1, v2;
			Matrix4f modelMatrix = super.getModelMatrix();
			v0 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i]).getPosition(), null);
			v1 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i + 1]).getPosition(), null);
			v2 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i + 2]).getPosition(), null);
			float distance = RayIntersectsTriangle(ray, new Vector3f(v0.x, v0.y, v0.z), new Vector3f(v1.x, v1.y, v1.z), new Vector3f(v2.x, v2.y, v2.z));
			if (distance != Float.MAX_VALUE) {
				found = true;
			}
		}
		return found;
	}
	
	@Override
	public float getDistance(GLRay ray) {
		float min = Float.MAX_VALUE;
		
		for (int i = 0;i < this.indices.length;i += 3) {
			Vector4f v0, v1, v2;
			Matrix4f modelMatrix = super.getModelMatrix();
			v0 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i]).getPosition(), null);
			v1 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i + 1]).getPosition(), null);
			v2 = Matrix4f.transform(modelMatrix, this.vertices.get(this.indices[i + 2]).getPosition(), null);
			float distance = RayIntersectsTriangle(ray, new Vector3f(v0.x, v0.y, v0.z), new Vector3f(v1.x, v1.y, v1.z), new Vector3f(v2.x, v2.y, v2.z));
			if (distance < min) {
				min = distance;
			}
		}
		
		return min;
	}
	
	private static float RayIntersectsTriangle(GLRay R, Vector3f vertex1, Vector3f vertex2, Vector3f vertex3) {		
	    // Compute vectors along two edges of the triangle.
	    Vector3f edge1 = null, edge2 = null;

	    edge1 = Vector3f.sub(vertex2, vertex1, edge1);
	    edge2 = Vector3f.sub(vertex3, vertex1, edge2);

	    // Compute the determinant.
	    Vector3f directionCrossEdge2 = null;
	    directionCrossEdge2 = Vector3f.cross(R.getDirection(), edge2, directionCrossEdge2);


	    float determinant = Vector3f.dot(directionCrossEdge2, edge1);
	    // If the ray and triangle are parallel, there is no collision.
	    if (determinant > -.0000001f && determinant < .0000001f) {
	        return Float.MAX_VALUE;
	    }

	    float inverseDeterminant = 1.0f / determinant;

	    // Calculate the U parameter of the intersection point.
	    Vector3f distanceVector = null;
	    distanceVector = Vector3f.sub(R.getPosition(), vertex1, distanceVector);


	    float triangleU = Vector3f.dot(directionCrossEdge2, distanceVector);
	    triangleU *= inverseDeterminant;

	    // Make sure the U is inside the triangle.
	    if (triangleU < 0 || triangleU > 1) {
	        return Float.MAX_VALUE;
	    }

	    // Calculate the V parameter of the intersection point.
	    Vector3f distanceCrossEdge1 = null;
	    distanceCrossEdge1 = Vector3f.cross(distanceVector, edge1, distanceCrossEdge1);


	    float triangleV = Vector3f.dot(R.getDirection(), distanceCrossEdge1);
	    triangleV *= inverseDeterminant;

	    // Make sure the V is inside the triangle.
	    if (triangleV < 0 || triangleU + triangleV > 1) {
	        return Float.MAX_VALUE;
	    }

	    // Get the distance to the face from our ray origin
	    float rayDistance = Vector3f.dot(distanceCrossEdge1, edge2);
	    rayDistance *= inverseDeterminant;


	    // Is the triangle behind us?
	    if (rayDistance < 0) {
	        rayDistance *= -1;
	        return Float.MAX_VALUE;
	    }
	    return rayDistance;
	}
}
