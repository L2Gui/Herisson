package view;

import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLDrawableObject;
import opengl.resource.object.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;

public class VertexView extends ViewElement {
	private Vertex model;

    private GLShader labelShader;
    private GLShader textShader;

    private GLTextMesh text;
    private GLDrawableObject textDrawable;

    public Vertex getModel() {
        return model;
    }

    public void setModel(Vertex model) {
        this.model = model;

        this.text = new GLTextMesh();
        //this.text.setup();
    }

    @Override
	public void render(Matrix4f transformationMatrix)
	{
        super.render(transformationMatrix);
	}

    public void remove() {

    }
}
