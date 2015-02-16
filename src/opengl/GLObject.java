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

public class GLObject {
	private GLTexture texture;
	private GLShader shader;
	private GLTexturedVertex[] vertices;
	private int[] indices;
	private int indicesCount;
	private int vaid;
	private int vid;
	private int iid;
	
	public GLObject(GLShader shader, GLTexture texture, GLTexturedVertex[] vertices, int[] indices) {
		this.texture = texture;
		this.shader = shader;
		this.vertices = vertices;
		this.indices = indices;
		this.indicesCount = indices.length;
	}
	
	public void init() {
		FloatBuffer fb = GLHelper.makeTexturedVertexBuffer(this.vertices);
		IntBuffer ib = GLHelper.makeIntBuffer(this.indices);
		
		this.vid = GLHelper.makeBuffer(GL15.GL_ARRAY_BUFFER, fb);
    	this.iid = GLHelper.makeBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ib);
    	this.indices = null;
    	this.vertices = null;
		
    	this.vaid = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vaid);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
    	
    	GL20.glVertexAttribPointer(0, GLTexturedVertex.positionElementCount, GL11.GL_FLOAT, false, GLTexturedVertex.stride, GLTexturedVertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, GLTexturedVertex.colorElementCount, GL11.GL_FLOAT, false, GLTexturedVertex.stride, GLTexturedVertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, GLTexturedVertex.textureElementCount, GL11.GL_FLOAT, false, GLTexturedVertex.stride, GLTexturedVertex.textureByteOffset);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void render(Matrix4f projectionMatrix) {
		this.texture.bind();
		this.shader.bind();
		
		FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		projectionMatrix.store(matrix44Buffer); 
		matrix44Buffer.flip();
		GL20.glUniformMatrix4(this.shader.getUniform("projectionMatrix"), false, matrix44Buffer);
		
		GL30.glBindVertexArray(this.vaid);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
		
    	glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
    	glEnableVertexAttribArray(2);
    	
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iid);
    	GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.indicesCount, 6, GL11.GL_UNSIGNED_INT, 0);
    	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    	
    	glDisableVertexAttribArray(2);
    	glDisableVertexAttribArray(1);
    	glDisableVertexAttribArray(0);
    	
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    	
    	this.shader.unbind();
    	this.texture.unbind();
	}
}
