package opengl.resource.mesh;

import opengl.GLHelper;
import opengl.resource.GLShader;
import opengl.vertex.GLTexturedVertex;
import opengl.vertex.GLVertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

/**
 * Created by Clement on 14/03/2015.
 */
public class GLTextureColorVariantMesh extends GLColorVariantMesh {
    private int textureLocation;

    public void updateTexturedVertices(List<? extends GLVertex> vertices) {
        super.updateVertices(vertices, GLTexturedVertex.elementCount);
    }

    @Override
    public int getVertexStride() {
        return GLTexturedVertex.stride;
    }

    @Override
    public FloatBuffer getVerticesBuffer() {
        return GLHelper.makeVertexBuffer(this.vertices, GLTexturedVertex.elementCount);
    }

    @Override
    public void attribVerticesPointer(GLShader shader) {
        super.attribVerticesPointer(shader);

        this.textureLocation = shader.getAttribLocation("in_TextureCoord");
        GL20.glVertexAttribPointer(this.textureLocation, GLTexturedVertex.textureElementCount, GL11.GL_FLOAT, false, this.getVertexStride(), GLTexturedVertex.textureByteOffset);
    }

    @Override
    public void enableVerticesPointer() {
        super.enableVerticesPointer();
        glEnableVertexAttribArray(this.textureLocation);
    }

    @Override
    public void disableVerticesPointer() {
        super.disableVerticesPointer();
        glDisableVertexAttribArray(this.textureLocation);
    }
}
