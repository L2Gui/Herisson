package opengl.resource.object.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import opengl.resource.GLShader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import opengl.GLHelper;
import opengl.resource.object.GLObjectUsage;
import opengl.vertex.GLVertex;

public abstract class GLMesh implements IGLMesh {
	private GLObjectUsage state;
	protected List<? extends GLVertex> vertices;
	private int[] indices;
	private int vaid;
	private int vid;
	private int iid;
	private boolean isInitialized;
    private GLShader shader;
	
	public GLMesh() {

	}

	protected void setupMesh(GLShader shader, List<? extends GLVertex> vertices, int[] indices, GLObjectUsage usage) {
		this.vertices = vertices;
		this.state = usage;
		this.indices = indices;
		this.isInitialized = false;
        this.shader = shader;
	}

	@Override
	public void init() {
		FloatBuffer fb = this.getVerticesBuffer();
		IntBuffer ib = GLHelper.makeIntBuffer(this.indices);

		this.vid = GLHelper.makeBuffer(GL15.GL_ARRAY_BUFFER, fb, this.state.getUsage());
    	this.iid = GLHelper.makeBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ib, this.state.getUsage());

    	this.vaid = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vaid);
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);

    	this.attribVerticesPointer(shader);

    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		this.isInitialized = true;
	}

	@Override
	public boolean isInitialized() {
		return this.isInitialized;
	}

    @Override
    public int getIndicesCount() {
        return this.indices.length;
    }

    @Override
    public GLVertex getVertexFromIndice(int indice) {
        return this.vertices.get(this.indices[indice]);
    }

    @Override
	public void bindVerticesArrayBuffer() {
        GL30.glBindVertexArray(this.vaid);
    }

    @Override
    public void bindVerticesBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vid);
    }

    @Override
    public void bindIndicesBuffer() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.iid);
    }

    @Override
	public void unbind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    @Override
	public void updateVertices(List<? extends GLVertex> vertices, int elementCount) {
		if (this.vertices.size() > vertices.size()) {
			System.out.println("Warning: there's less vertices than before: indices could make the app crash");
		}
		
		this.bindVerticesArrayBuffer();
		
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(elementCount * vertices.size());
		
		for (GLVertex vertex : vertices) {
			verticesBuffer.put(vertex.getElements());
		}
		verticesBuffer.flip();
		
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, verticesBuffer);
		
		this.unbind();
		
		this.vertices = vertices;
	}
}
