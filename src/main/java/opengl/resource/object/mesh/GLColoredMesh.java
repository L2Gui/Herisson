package opengl.resource.object.mesh;

import opengl.GLHelper;
import opengl.resource.GLShader;
import opengl.vertex.GLColoredVertex;
import opengl.vertex.GLVertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

public class GLColoredMesh extends GLMesh {
    private int colorLocation;
	
	public void updateColoredVertices(List<? extends GLVertex> vertices) {
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
        super.attribVerticesPointer(shader);

        this.colorLocation = shader.getAttribLocation("in_Color");
		GL20.glVertexAttribPointer(this.colorLocation, GLColoredVertex.colorElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLColoredVertex.colorByteOffset);
	}

	@Override
    public void enableVerticesPointer() {
        super.enableVerticesPointer();
    	glEnableVertexAttribArray(this.colorLocation);
	}

	@Override
    public void disableVerticesPointer() {

		glDisableVertexAttribArray(this.colorLocation);
        super.disableVerticesPointer();
	}
}
