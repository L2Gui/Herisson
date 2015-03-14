package opengl.resource.object.mesh;

import java.awt.Color;
import java.awt.Font;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import opengl.resource.GLShader;
import opengl.resource.IGLResource;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.texture.GLTextTexture;
import opengl.vertex.GLTexturedVertex;

import opengl.vertex.GLVertex;
import org.lwjgl.util.vector.Vector3f;

public class GLTextMesh implements IGLMesh {
	private GLTextTexture textTexture;
	private GLTexturedMesh texturedMesh;
	private float width;
	private float height;
	
	public GLTextMesh() {
		this.texturedMesh = new GLTexturedMesh();
	}
	
	public void setup(GLShader shader, String text, Font font, float height, GLObjectUsage usage) {
		this.textTexture = new GLTextTexture(text, font);
		
		float ratio = this.textTexture.getRatio();
		this.height = height;
		this.width = ratio * height;
		
		float midHeight = this.height / 2.0f;
		float midWidth = this.width / 2.0f;
		
		List<GLTexturedVertex> vertices = new ArrayList<GLTexturedVertex>();
		GLTexturedVertex v0 = new GLTexturedVertex();
		GLTexturedVertex v1 = new GLTexturedVertex();
		GLTexturedVertex v2 = new GLTexturedVertex();
		GLTexturedVertex v3 = new GLTexturedVertex();
		
		v0.setPosition(new Vector3f(-midWidth, midHeight, 0.0f));
		v1.setPosition(new Vector3f(midWidth, midHeight, 0.0f));
		v2.setPosition(new Vector3f(midWidth, -midHeight, 0.0f));
		v3.setPosition(new Vector3f(-midWidth, -midHeight, 0.0f));
		
		v0.setColor(Color.black);
		v1.setColor(Color.black);
		v2.setColor(Color.black);
		v3.setColor(Color.black);
		
		v0.setST(0.0f, 0.0f);
		v1.setST(1.0f, 0.0f);
		v2.setST(1.0f, 1.0f);
		v3.setST(0.0f, 1.0f);
		
		vertices.add(v0);
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		
		int[] indices = {
			0, 1, 2,
			2, 3, 0
		};
		
		this.texturedMesh.setup(shader, vertices, indices, usage);
	}
	
	@Override
	public void init() {
		this.textTexture.init();
		this.texturedMesh.init();
	}

	@Override
	public boolean isInitialized() {
		return this.textTexture.isInitialized() && this.texturedMesh.isInitialized();
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}

    @Override
    public int getIndicesCount() {
        return this.texturedMesh.getIndicesCount();
    }

    @Override
    public GLVertex getVertexFromIndice(int indice) {
        return this.texturedMesh.getVertexFromIndice(indice);
    }

    @Override
    public void bind() {
        this.textTexture.bind();
        this.texturedMesh.bind();
    }

    @Override
    public void unbind() {
        this.texturedMesh.unbind();
        this.textTexture.unbind();
    }

    @Override
    public void updateVertices(List<? extends GLVertex> vertices, int elementCount) {
        this.texturedMesh.updateVertices(vertices, elementCount);
    }

    @Override
    public int getVertexStride() {
        return this.texturedMesh.getVertexStride();
    }

    @Override
    public FloatBuffer getVerticesBuffer() {
        return this.texturedMesh.getVerticesBuffer();
    }

    @Override
    public void attribVerticesPointer(GLShader shader) {
        this.texturedMesh.attribVerticesPointer(shader);
    }

    @Override
    public void enableVerticesPointer() {
        this.texturedMesh.enableVerticesPointer();
    }

    @Override
    public void disableVerticesPointer() {
        this.texturedMesh.disableVerticesPointer();
    }
}
