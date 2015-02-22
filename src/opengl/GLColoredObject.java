package opengl;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class GLColoredObject extends GLObject {
	private GLShader shader;
	private GLColoredVertex[] vertices;
	private int[] indices;
	private int indicesCount;
	private int vaid;
	private int vid;
	private int iid;
	
	public GLColoredObject(GLShader shader, GLColoredVertex[] vertices, int[] indices) {
		this.shader = shader;
		this.vertices = vertices;
		this.indices = indices;
		this.indicesCount = indices.length;
	}
	
	public void init() {
		FloatBuffer fb = GLHelper.makeColoredVertexBuffer(this.vertices);
		IntBuffer ib = GLHelper.makeIntBuffer(this.indices);
		
		this.vid = GLHelper.makeBuffer(GL15.GL_ARRAY_BUFFER, fb);
    	this.iid = GLHelper.makeBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ib);
    	this.indices = null;
    	this.vertices = null;
		
    	this.vaid = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vaid);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
    	
    	GL20.glVertexAttribPointer(0, GLColoredVertex.positionElementCount, GL11.GL_FLOAT, false, GLColoredVertex.stride, GLColoredVertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, GLColoredVertex.colorElementCount, GL11.GL_FLOAT, false, GLColoredVertex.stride, GLColoredVertex.colorByteOffset);
   	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void render(Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		if (super.isModified()) {
			super.computeMatrix();
		}
		
		this.shader.bind();
		
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		projectionMatrix.store(matrix44Buffer); 
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("projectionMatrix"), false, matrix44Buffer);
		viewMatrix.store(matrix44Buffer); 
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("viewMatrix"), false, matrix44Buffer);
		super.getModelMatrix().store(matrix44Buffer);
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("modelMatrix"), false, matrix44Buffer);
		
		GL30.glBindVertexArray(this.vaid);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
		
    	glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
    	
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iid);
    	GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.indicesCount, 6, GL11.GL_UNSIGNED_INT, 0);
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    	
    	glDisableVertexAttribArray(1);
    	glDisableVertexAttribArray(0);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	
    	this.shader.unbind();
	}

}
