package view;

import model.Vertex;
import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.resource.object.mesh.GLMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.util.vector.Matrix4f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VertexView extends ViewElement {
	private Vertex model;
    private GLColoredMesh mesh;
    GLShader shader;

    public VertexView() {

    }

    public VertexView(Vertex model, GLColoredMesh mesh, GLShader shader) {
        this.mesh = mesh;
        this.shader = shader;
        this.model = model;
    }




    public Vertex getModel() {
        return model;
    }

    public void setModel(Vertex model) {
        this.model = model;
    }

    @Override
	public void render(Matrix4f transformationMatrix)
	{
        super.render(transformationMatrix);

        this.setShader(shader);
        this.setMesh(this.mesh);
	}
}
