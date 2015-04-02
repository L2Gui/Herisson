package view;

import model.Vertex;
import opengl.resource.GLShader;
import opengl.object.GLDrawableObject;
import opengl.object.GLObjectUsage;
import opengl.resource.mesh.GLColorVariantMesh;
import opengl.resource.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import utils.MathUtils;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VertexView extends ViewElement implements Observer {
	private Vertex vertexModel;
    private GLTextMesh labelMesh;
    private GLColorVariantMesh mesh;
    private GLDrawableObject textDrawable;
    private GLDrawableObject borderDrawable;

    public VertexView(Vertex vertexModel, GLColorVariantMesh mesh, GLShader objectShader, GLShader labelShader) {
        this.vertexModel = vertexModel;
        this.vertexModel.addObserver(this);
        this.labelMesh = new GLTextMesh();
        this.textDrawable = new GLDrawableObject(labelShader, labelMesh);
        this.borderDrawable = new GLDrawableObject(objectShader, mesh);
        this.mesh = mesh;

        super.setShader(objectShader);
        super.setMesh(this.mesh);

        String label = this.vertexModel.getLabel();
        Font font = new Font("Verdana", Font.PLAIN, 32); //normalement, on va chercher l'info dans model;
        float height = 0.2f;

        this.labelMesh.setup(labelShader, label, font, height, GLObjectUsage.STATIC);
        this.labelMesh.init();
        this.labelMesh.setColor(this.vertexModel.getTextColor());

        this.refreshTransform();
    }

    @Override
    public void onModelChange() {
        super.onModelChange();

        this.borderDrawable.setPosition(super.getPosition().x, super.getPosition().y, super.getPosition().z - 0.0001f);

        Vector3f textPosition = new Vector3f(this.vertexModel.getSize() / 4.0f, - this.vertexModel.getSize() / 4.0f, super.getPosition().z);
        textPosition.x += this.textDrawable.getScale().x / 8.0f;
        textPosition.y -= this.textDrawable.getScale().y / 8.0f;
        Vector3f.add(super.getPosition(),textPosition, textPosition);
        this.textDrawable.setPosition(textPosition);
    }

    @Override
    public void render(Matrix4f transformationMatrix) {
        this.mesh.setColor(this.vertexModel.getBorderColor());
        this.borderDrawable.render(transformationMatrix);
        this.mesh.setColor(this.vertexModel.getBackgroundColor());
        super.render(transformationMatrix);
    }

    public void translateTo(Vector3f newPosition, float percentage) {
        Vector3f position = super.getPosition();
        Vector3f interm = MathUtils.vectorLerp(position, newPosition, percentage);

        this.vertexModel.setPosition(interm);
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

    private void refreshTransform() {
        this.borderDrawable.setScale((this.vertexModel.getSize() / 2.0f) + this.vertexModel.getThickness(), (this.vertexModel.getSize() / 2.0f) + this.vertexModel.getThickness(), 1.0f);
        this.textDrawable.setScale(this.vertexModel.getSize(), this.vertexModel.getSize(), 1.0f);
        super.setPosition(vertexModel.getPosition());
        super.setScale(this.vertexModel.getSize() / 2.0f, this.vertexModel.getSize() / 2.0f, 1.0f);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.refreshTransform();
    }
}
