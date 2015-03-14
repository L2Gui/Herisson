package opengl.resource.object.mesh;

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

public class GLTexturedMesh extends GLColoredMesh {
	private GLTexture texture;
    private int textureLocation;
	
	public void setup(GLShader shader, GLTexture texture, List<GLTexturedVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupMesh(shader, vertices, indices, usage);
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
    public void attribVerticesPointer(GLShader shader) {
		super.attribVerticesPointer(shader);

        this.textureLocation = shader.getAttribLocation("in_TextureCoord");
		GL20.glVertexAttribPointer(this.textureLocation, GLTexturedVertex.textureElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLTexturedVertex.textureByteOffset);
	}
	
	@Override
    public void enableVerticesPointer() {
		super.enableVerticesPointer();
    	glEnableVertexAttribArray(this.textureLocation);
	}

	@Override
    public void disableVerticesPointer() {
        glDisableVertexAttribArray(this.textureLocation);
		super.disableVerticesPointer();
	}
}
