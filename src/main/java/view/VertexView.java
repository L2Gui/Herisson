package view;

import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLDrawableObject;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.resource.object.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;

public class VertexView extends ViewElement {
	private Vertex vertexModel;
    private GLTextMesh labelMesh;
    private GLColorVariantMesh mesh;
    private GLDrawableObject textDrawable;

    public VertexView(Vertex vertexModel, GLColorVariantMesh mesh, GLShader labelShader) {
        this.vertexModel = vertexModel;

        this.labelMesh = new GLTextMesh();
        this.mesh = mesh;
        this.textDrawable = new GLDrawableObject(labelShader, labelMesh);

        super.setMesh(this.mesh);

        String label = this.vertexModel.getLabel();
        Font font = this.vertexModel.getFont();
        float height = 0.2f;

        this.labelMesh.setup(labelShader, label, font, height, GLObjectUsage.STATIC);
        this.labelMesh.init();
        this.labelMesh.setColor(this.vertexModel.getTextColor());

        this.textDrawable.scale(this.vertexModel.getSize(), this.vertexModel.getSize(), 1.0f);
        super.setPosition(vertexModel.getPosition());
    }

    @Override
    public void computeMatrix() {
        super.computeMatrix();
        //Vector3f.add(super.getPosition(), new Vector3f(this.vertexModel.getSize() / 2.0f, - this.vertexModel.getSize() / 2.0f, 0.0f), null)
        Vector3f position = Vector3f.add(super.getPosition(), new Vector3f(this.vertexModel.getSize() / 4.0f, - this.vertexModel.getSize() / 4.0f, 0.001f), null);
        this.textDrawable.setPosition(position);
    }

    @Override
    public void render(Matrix4f transformationMatrix)
    {
        this.mesh.setColor(this.vertexModel.getBackgroundColor());
        super.render(transformationMatrix);
    }

    public void renderText(Matrix4f transformationMatrix) {
        this.textDrawable.render(transformationMatrix);
    }

    public Vertex getModel() {
        return vertexModel;
    }

    public void setModel(Vertex vertexModel) {
        this.vertexModel = vertexModel;
    }
}
