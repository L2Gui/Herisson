package opengl.resource.object.drawable;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collection;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import opengl.GLHelper;
import opengl.resource.GLShader;
import opengl.resource.object.GLObject;
import opengl.resource.object.GLObjectUsage;
import opengl.vertex.GLVertex;

public abstract class GLDrawableObject extends GLObject implements IGLDrawable {
	private GLObjectUsage state;
	private GLShader shader;
	protected Collection<? extends GLVertex> vertices;
	private int[] indices;
	private int indicesCount;
	private int vaid;
	private int vid;
	private int iid;
	private boolean isInitialized;
	
	protected void setupObject(GLShader shader, Collection<? extends GLVertex> vertices, int[] indices, GLObjectUsage usage) {
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
		if (super.isModified()) {
			super.computeMatrix();
		}
		
		this.shader.bind();
		
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		Matrix4f.mul(projectionViewMatrix, super.getModelMatrix(), null).store(matrix44Buffer);
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("transformMatrix"), false, matrix44Buffer);
		
		GL30.glBindVertexArray(this.vaid);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
		
    	this.enableVerticesPointer();
    	
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iid);
    	GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.indicesCount, 6, GL11.GL_UNSIGNED_INT, 0);
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    	
    	this.disableVerticesPointer();
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	
    	this.shader.unbind();
	}
	
	protected void updateVertices(Collection<? extends GLVertex> vertices, int elementCount) {
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
}
