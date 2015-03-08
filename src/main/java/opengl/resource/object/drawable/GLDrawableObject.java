package opengl.resource.object.drawable;

import opengl.resource.GLShader;
import opengl.resource.object.GLObject;
import opengl.resource.object.mesh.GLMesh;
import opengl.utils.GLRay;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.nio.FloatBuffer;

/**
 * Created by Clement on 08/03/2015.
 */
public class GLDrawableObject extends GLObject implements IGLDrawable {
    private GLMesh mesh;
    private GLShader shader;

    public GLDrawableObject() {

    }

    public GLDrawableObject(GLShader shader, GLMesh mesh) {
        this.shader = shader;
        this.mesh = mesh;
    }

    public void setShader(GLShader shader) {
        this.shader = shader;
    }

    public void setMesh(GLMesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void render(Matrix4f projectionViewMatrix) {
        this.shader.bind();

        FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);

        Matrix4f transformMatrix = Matrix4f.mul(projectionViewMatrix, super.getModelMatrix(), null);
        transformMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();
        GL20.glUniformMatrix4(this.shader.getUniform("transformMatrix"), false, matrix44Buffer);

        this.mesh.bindVerticesArrayBuffer();
        this.mesh.bindVerticesBuffer();

        this.mesh.enableVerticesPointer();
        this.mesh.bindIndicesBuffer();
        GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.mesh.getIndicesCount(), this.mesh.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
        this.mesh.disableVerticesPointer();

        this.mesh.unbind();

        this.shader.unbind();
    }

    @Override
    public boolean isIntersected(GLRay ray) {
        boolean found = false;
        for (int i = 0;i < this.mesh.getIndicesCount() && !found;i += 3) {
            Vector4f v0, v1, v2;
            Matrix4f modelMatrix = super.getModelMatrix();
            v0 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i).getPosition(), null);
            v1 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i + 1).getPosition(), null);
            v2 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i + 2).getPosition(), null);
            float distance = RayIntersectsTriangle(ray, new Vector3f(v0.x, v0.y, v0.z), new Vector3f(v1.x, v1.y, v1.z), new Vector3f(v2.x, v2.y, v2.z));
            if (distance != Float.MAX_VALUE) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public float getDistance(GLRay ray) {
        float min = Float.MAX_VALUE;

        for (int i = 0;i < this.mesh.getIndicesCount();i += 3) {
            Vector4f v0, v1, v2;
            Matrix4f modelMatrix = super.getModelMatrix();
            v0 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i).getPosition(), null);
            v1 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i + 1).getPosition(), null);
            v2 = Matrix4f.transform(modelMatrix, this.mesh.getVertexFromIndice(i + 2).getPosition(), null);
            float distance = RayIntersectsTriangle(ray, new Vector3f(v0.x, v0.y, v0.z), new Vector3f(v1.x, v1.y, v1.z), new Vector3f(v2.x, v2.y, v2.z));
            if (distance < min) {
                min = distance;
            }
        }

        return min;
    }

    private static float RayIntersectsTriangle(GLRay R, Vector3f vertex1, Vector3f vertex2, Vector3f vertex3) {
        // Compute vectors along two edges of the triangle.
        Vector3f edge1 = null, edge2 = null;

        edge1 = Vector3f.sub(vertex2, vertex1, edge1);
        edge2 = Vector3f.sub(vertex3, vertex1, edge2);

        // Compute the determinant.
        Vector3f directionCrossEdge2 = null;
        directionCrossEdge2 = Vector3f.cross(R.getDirection(), edge2, directionCrossEdge2);


        float determinant = Vector3f.dot(directionCrossEdge2, edge1);
        // If the ray and triangle are parallel, there is no collision.
        if (determinant > -.0000001f && determinant < .0000001f) {
            return Float.MAX_VALUE;
        }

        float inverseDeterminant = 1.0f / determinant;

        // Calculate the U parameter of the intersection point.
        Vector3f distanceVector = null;
        distanceVector = Vector3f.sub(R.getPosition(), vertex1, distanceVector);


        float triangleU = Vector3f.dot(directionCrossEdge2, distanceVector);
        triangleU *= inverseDeterminant;

        // Make sure the U is inside the triangle.
        if (triangleU < 0 || triangleU > 1) {
            return Float.MAX_VALUE;
        }

        // Calculate the V parameter of the intersection point.
        Vector3f distanceCrossEdge1 = null;
        distanceCrossEdge1 = Vector3f.cross(distanceVector, edge1, distanceCrossEdge1);


        float triangleV = Vector3f.dot(R.getDirection(), distanceCrossEdge1);
        triangleV *= inverseDeterminant;

        // Make sure the V is inside the triangle.
        if (triangleV < 0 || triangleU + triangleV > 1) {
            return Float.MAX_VALUE;
        }

        // Get the distance to the face from our ray origin
        float rayDistance = Vector3f.dot(distanceCrossEdge1, edge2);
        rayDistance *= inverseDeterminant;


        // Is the triangle behind us?
        if (rayDistance < 0) {
            rayDistance *= -1;
            return Float.MAX_VALUE;
        }
        return rayDistance;
    }
}
