package opengl.resource.object.mesh;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.util.List;

import opengl.GLHelper;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.vertex.GLColoredVertex;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GLColoredMesh extends GLMesh {
	private int positionLocation;
    private int colorLocation;

	public void setup(GLShader shader, List<GLColoredVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupMesh(shader, vertices, indices, usage);
	}
	
	public void updateColoredVertices(List<GLColoredVertex> vertices) {
		super.updateVertices(vertices, GLColoredVertex.elementCount);
	}
	
	@Override
    public int getVertexStride() {
		return GLColoredVertex.stride;
	}

	@Override
    public FloatBuffer getVerticesBuffer() {
		return GLHelper.makeVertexBuffer(this.vertices, GLColoredVertex.elementCount);
	}

	@Override
    public void attribVerticesPointer(GLShader shader) {
        this.positionLocation = shader.getAttribLocation("in_Position");
        this.colorLocation = shader.getAttribLocation("in_Color");

		GL20.glVertexAttribPointer(this.positionLocation, GLColoredVertex.positionElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.positionByteOffset);
		GL20.glVertexAttribPointer(this.colorLocation, GLColoredVertex.colorElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.colorByteOffset);
	}

	@Override
    public void enableVerticesPointer() {
		glEnableVertexAttribArray(this.positionLocation);
    	glEnableVertexAttribArray(this.colorLocation);
	}

	@Override
    public void disableVerticesPointer() {
		glDisableVertexAttribArray(this.colorLocation);
    	glDisableVertexAttribArray(this.positionLocation);
	}
}
