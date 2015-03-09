package opengl.resource.object.mesh;

import opengl.resource.IGLResource;
import opengl.vertex.GLVertex;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by Clement on 08/03/2015.
 */
public interface IGLMesh extends IGLResource {
    int getIndicesCount();
    GLVertex getVertexFromIndice(int indice);
    void bindVerticesArrayBuffer();
    void bindVerticesBuffer();
    void bindIndicesBuffer();
    void unbind();
    void updateVertices(List<? extends GLVertex> vertices, int elementCount);
    int getVertexStride();
    FloatBuffer getVerticesBuffer();
    void attribVerticesPointer();
    void enableVerticesPointer();
    void disableVerticesPointer();
}
