package opengl.resource.object.drawable;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.util.List;

import opengl.GLHelper;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.texture.GLTexture;
import opengl.vertex.GLTexturedVertex;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class GLTexturedObject extends GLColoredObject {	
	private GLTexture texture;
	
	public void setup(GLTexture texture, List<GLTexturedVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupObject(GLShader.texture3D, vertices, indices, usage);
		this.texture = texture;
	}
	
	public void updateTexturedVertices(List<GLTexturedVertex> vertices) {
		super.updateVertices(vertices, GLTexturedVertex.elementCount);
	}
	
	public void setTexture(GLTexture texture) {
		this.texture = texture;
	}
	
	@Override
	public void render(Matrix4f projectionViewMatrix) {
		this.texture.bind();
		super.render(projectionViewMatrix);
    	this.texture.unbind();
	}
	
	@Override
	protected int getVertexStride() {
		return GLTexturedVertex.stride;
	}

	@Override
	protected FloatBuffer getVerticesBuffer() {
		return GLHelper.makeVertexBuffer(this.vertices, GLTexturedVertex.elementCount);
	}
	
	@Override
	protected void attribVerticesPointer() {
		super.attribVerticesPointer();
		GL20.glVertexAttribPointer(2, GLTexturedVertex.textureElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLTexturedVertex.textureByteOffset);
	}
	
	@Override
	protected void enableVerticesPointer() {
		super.enableVerticesPointer();
    	glEnableVertexAttribArray(2);
	}

	@Override
	protected void disableVerticesPointer() {
		super.disableVerticesPointer();
    	glDisableVertexAttribArray(0);
	}
}
