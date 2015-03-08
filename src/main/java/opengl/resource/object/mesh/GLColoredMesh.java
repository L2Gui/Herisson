package opengl.resource.object.mesh;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import java.nio.FloatBuffer;
import java.util.List;

import opengl.GLHelper;
import opengl.resource.object.GLObjectUsage;
import opengl.vertex.GLColoredVertex;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GLColoredMesh extends GLMesh {
	
	public void setup(List<GLColoredVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupMesh(vertices, indices, usage);
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
    public void attribVerticesPointer() {
		GL20.glVertexAttribPointer(0, GLColoredVertex.positionElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, GLColoredVertex.colorElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.colorByteOffset);
	}

	@Override
    public void enableVerticesPointer() {
		glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
	}

	@Override
    public void disableVerticesPointer() {
		glDisableVertexAttribArray(1);
    	glDisableVertexAttribArray(0);
	}
}
