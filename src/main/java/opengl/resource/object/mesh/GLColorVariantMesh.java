package opengl.resource.object.mesh;

import opengl.resource.GLShader;
import org.lwjgl.opengl.GL20;

import java.awt.*;

/**
 * Created by Clement on 14/03/2015.
 */
public class GLColorVariantMesh extends GLMesh {
    private Color color;
    private int colorLocation;

    @Override
    public void attribVerticesPointer(GLShader shader) {
        this.colorLocation = shader.getUniform("in_Color");
        super.attribVerticesPointer(shader);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void bind() {
        super.bind();
        GL20.glUniform4f(this.colorLocation, this.color.getRed() / 255.0f, this.color.getGreen() / 255.0f, this.color.getBlue() / 255.0f, this.color.getAlpha() / 255.0f);
    }

    @Override
    public void unbind() {
        super.unbind();
    }
}
