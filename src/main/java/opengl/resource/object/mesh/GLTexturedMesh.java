package opengl.resource.object.mesh;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.util.List;

import opengl.GLHelper;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.texture.GLTexture;
import opengl.vertex.GLTexturedVertex;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GLTexturedMesh extends GLColoredMesh {
	private GLTexture texture;
	
	public void setup(GLTexture texture, List<GLTexturedVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupMesh(vertices, indices, usage);
		this.texture = texture;
	}
	
	public void updateTexturedVertices(List<GLTexturedVertex> vertices) {
		super.updateVertices(vertices, GLTexturedVertex.elementCount);
	}
	
	public void setTexture(GLTexture texture) {
		this.texture = texture;
	}
	
	@Override
    public int getVertexStride() {
		return GLTexturedVertex.stride;
	}

	@Override
    public FloatBuffer getVerticesBuffer() {
		return GLHelper.makeVertexBuffer(this.vertices, GLTexturedVertex.elementCount);
	}
	
	@Override
    public void attribVerticesPointer() {
		super.attribVerticesPointer();
		GL20.glVertexAttribPointer(2, GLTexturedVertex.textureElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLTexturedVertex.textureByteOffset);
	}
	
	@Override
    public void enableVerticesPointer() {
		super.enableVerticesPointer();
    	glEnableVertexAttribArray(2);
	}

	@Override
    public void disableVerticesPointer() {
		super.disableVerticesPointer();
    	glDisableVertexAttribArray(0);
	}
}
