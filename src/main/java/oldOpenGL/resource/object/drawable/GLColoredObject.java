package oldOpenGL.resource.object.drawable;

import oldOpenGL.GLHelper;
import oldOpenGL.resource.GLShader;
import oldOpenGL.resource.object.GLObjectUsage;
import oldOpenGL.vertex.GLColoredVertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class GLColoredObject extends GLDrawableObject {
	
	public void setup(List<GLColoredVertex> vertices, int[] indices, GLObjectUsage usage) {
		super.setupObject(GLShader.color3D, vertices, indices, usage);
	}
	
	public void updateColoredVertices(List<GLColoredVertex> vertices) {
		super.updateVertices(vertices, GLColoredVertex.elementCount);
	}
	
	@Override
	protected int getVertexStride() {
		return GLColoredVertex.stride;
	}

	@Override
	protected FloatBuffer getVerticesBuffer() {
		return GLHelper.makeVertexBuffer(this.vertices, GLColoredVertex.elementCount);
	}

	@Override
	protected void attribVerticesPointer() {
		GL20.glVertexAttribPointer(0, GLColoredVertex.positionElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, GLColoredVertex.colorElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.colorByteOffset);
	}

	@Override
	protected void enableVerticesPointer() {
		glEnableVertexAttribArray(0);
    	glEnableVertexAttribArray(1);
	}

	@Override
	protected void disableVerticesPointer() {
		glDisableVertexAttribArray(1);
    	glDisableVertexAttribArray(0);
	}
}
