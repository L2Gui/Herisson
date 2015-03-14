package view;

import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLDrawableObject;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.resource.object.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;

public class VertexView extends ViewElement {
	private Vertex vertexModel;
    private GLTextMesh labelMesh;
    private GLDrawableObject textDrawable;

    public VertexView(Vertex vertexModel, GLShader labelShader) {
        this.vertexModel = vertexModel;

        this.labelMesh = new GLTextMesh();
        this.textDrawable = new GLDrawableObject(labelShader, labelMesh);

        String label = this.vertexModel.getLabel();
        Font font = this.vertexModel.getFont();
        float height = 0.2f;

        this.labelMesh.setup(labelShader, label, font, height, GLObjectUsage.STATIC);
        this.computeMatrix();
    }

    @Override
    public void computeMatrix() {
        super.computeMatrix();

        this.textDrawable.setPosition(Vector3f.add(super.getPosition(), new Vector3f(this.vertexModel.getSize() / 2.0f, - this.vertexModel.getSize() / 2.0f, 0.0f), null));
    }

    @Override
    public void render(Matrix4f transformationMatrix)
    {
        super.render(transformationMatrix);
        this.textDrawable.render(transformationMatrix);
    }

    public Vertex getModel() {
        return vertexModel;
    }

    public void setModel(Vertex vertexModel) {
        this.vertexModel = vertexModel;
    }
}
