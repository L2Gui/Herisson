package opengl.resource.mesh;

import opengl.resource.GLShader;
import opengl.resource.IGLResource;
import opengl.vertex.GLVertex;

import java.nio.FloatBuffer;
import java.util.List;

/**
 * Created by Clement on 08/03/2015.
 */
public interface IGLMesh extends IGLResource {
    int getIndicesCount();
    GLVertex getVertexFromIndice(int indice);
    void bind();
    void unbind();
    void updateVertices(List<? extends GLVertex> vertices, int elementCount);
    int getVertexStride();
    FloatBuffer getVerticesBuffer();
    void attribVerticesPointer(GLShader shader);
    void enableVerticesPointer();
    void disableVerticesPointer();
}
