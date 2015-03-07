package opengl.resource.object.drawable;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.util.Collection;

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
	
	public void setup(GLShader shader, GLTexture texture, Collection<GLTexturedVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupObject(shader, vertices, indices, usage);
		this.texture = texture;
	}
	
	public void updateTexturedVertices(Collection<GLTexturedVertex> vertices) {
		super.updateVertices(vertices, GLTexturedVertex.elementCount);
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
