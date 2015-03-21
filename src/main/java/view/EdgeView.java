package view;

import model.Edge;
import opengl.resource.GLShader;
import opengl.resource.object.GLDrawableObject;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.resource.object.mesh.GLTextMesh;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import utils.QuaternionUtils;

import java.awt.*;

public class EdgeView extends ViewElement {
    private Edge edgeModel;
    private GLTextMesh labelMesh;
    private GLColorVariantMesh mesh;
    private GLDrawableObject textDrawable;

    public EdgeView(Edge edgeModel, GLColorVariantMesh mesh, GLShader labelShader) {
        this.edgeModel = edgeModel;
        this.mesh = mesh;

        this.labelMesh = new GLTextMesh();
        this.textDrawable = new GLDrawableObject(labelShader, labelMesh);

        super.setMesh(this.mesh);

        String label = this.edgeModel.getLabel();
        Font font = new Font("Verdana", Font.PLAIN, 32); //normalement, on va chercher l'info dans model :  Font font = this.edgeModel.getFont();
        float height = 0.2f;

        this.labelMesh.setup(labelShader, label, font, height, GLObjectUsage.STATIC);
        this.labelMesh.init();
        this.labelMesh.setColor(this.edgeModel.getTextColor());

        Quaternion rotationZ = QuaternionUtils.quaternionLookRotation(new Vector3f(0.0f, 0.0f, 1.0f));
        Vector3f directionXY = Vector3f.sub(this.edgeModel.getDstVertex().getPosition(), this.edgeModel.getSrcVertex().getPosition(), null);

        float angle = (float) Math.toDegrees(Math.atan(directionXY.y / directionXY.x));
        Quaternion rotationXY = QuaternionUtils.quaternionFromAxisAngle(new Vector3f(0.0f, 0.0f, 1.0f), -angle);
        Quaternion rotation = Quaternion.mul(rotationZ, rotationXY, null);

        Vector3f center = Vector3f.add(this.edgeModel.getDstVertex().getPosition(), this.edgeModel.getSrcVertex().getPosition(), null);
        center.scale(0.5f);

        this.setPosition(center);
        this.setRotation(rotation);
        this.setScale(new Vector3f(directionXY.length(), this.edgeModel.getThickness(), 1.0f));

        this.onModelChange();
    }

    @Override
    public void onModelChange() {
        super.onModelChange();

        Vector3f direction = new Vector3f(0.0f, this.edgeModel.getThickness() + 0.02f + (this.labelMesh.getHeight() * this.textDrawable.getScale().getY()) / 2.0f, 0.0f);
        direction = QuaternionUtils.quaternionTransform(super.getRotation(), direction);
        Vector3f position = Vector3f.add(this.edgeModel.getSrcVertex().getPosition(), direction, null);

        this.textDrawable.setPosition(position);
        this.textDrawable.setRotation(super.getRotation());
    }

    @Override
    public void render(Matrix4f transformationMatrix) {
        this.mesh.setColor(this.edgeModel.getColor());
        super.render(transformationMatrix);
    }

    public void renderText(Matrix4f transformationMatrix) {
        this.textDrawable.render(transformationMatrix);
    }

    public Edge getModel() {
        return this.edgeModel;
    }

    public void setModel(Edge edgeModel) {
        this.edgeModel = edgeModel;
    }
}
