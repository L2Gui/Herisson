package view;

import model.Edge;
import opengl.resource.GLShader;
import opengl.resource.object.GLDrawableObject;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;

public class EdgeView extends ViewElement {
    private Edge edgeModel;
    private GLTextMesh labelMesh;
    private GLDrawableObject textDrawable;

    public EdgeView(Edge edgeModel, GLShader labelShader) {
        this.edgeModel = edgeModel;

        this.labelMesh = new GLTextMesh();
        this.textDrawable = new GLDrawableObject(labelShader, labelMesh);

        String label = this.edgeModel.getLabel();
        Font font = this.edgeModel.getFont();
        float height = 0.2f;

        this.labelMesh.setup(labelShader, label, font, height, GLObjectUsage.STATIC);
        this.computeMatrix();
    }

    @Override
    public void computeMatrix() {
        super.computeMatrix();

        Vector3f direction = Vector3f.sub(this.edgeModel.getDstVertex().getPosition(), this.edgeModel.getSrcVertex().getPosition(), null);
        direction.scale(0.5f);

        Vector3f position = Vector3f.add(this.edgeModel.getSrcVertex().getPosition(), direction, null);

        this.textDrawable.setPosition(position);
    }

    @Override
    public void render(Matrix4f transformationMatrix)
    {
        super.render(transformationMatrix);
        this.textDrawable.render(transformationMatrix);
    }

    public Edge getModel() {
        return this.edgeModel;
    }

    public void setModel(Edge edgeModel) {
        this.edgeModel = edgeModel;
    }
}
