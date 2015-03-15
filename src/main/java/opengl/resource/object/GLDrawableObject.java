package opengl.resource.object;

import opengl.resource.GLShader;
import opengl.resource.object.mesh.IGLMesh;
import opengl.utils.GLRay;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import utils.QuaternionUtils;

import java.nio.FloatBuffer;

/**
 * Created by Clement on 08/03/2015.
 */
public class GLDrawableObject extends GLObject implements IGLDrawable {
    private IGLMesh mesh;
    private GLShader shader;
    private Vector3f scale;

    public GLDrawableObject() {
        this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public GLDrawableObject(GLShader shader, IGLMesh mesh) {
        this();
        this.shader = shader;
        this.mesh = mesh;
    }

    @Override
    public Vector3f getScale() {
        return this.scale;
    }

    public void setShader(GLShader shader) {
        this.shader = shader;
    }

    public void setMesh(IGLMesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void setScale(Vector3f scale) {
        this.scale = scale;
        this.computeMatrix();
    }

    @Override
    public void setScale(float x, float y, float z) {
        this.setScale(new Vector3f(x, y, z));
    }

    @Override
    public void render(Matrix4f transformationMatrix) {
        this.shader.bind();

        FloatBuffer matrix44Buffer = BufferUtils.createFloatBuffer(16);

        Matrix4f transformMatrix = Matrix4f.mul(transformationMatrix, super.getModelMatrix(), null);
        transformMatrix.store(matrix44Buffer);
        matrix44Buffer.flip();

        int transformationMatrixLocation = this.shader.getUniform("transformMatrix");
        GL20.glUniformMatrix4(transformationMatrixLocation, false, matrix44Buffer);

        this.mesh.bind();

        this.mesh.enableVerticesPointer();
        GL12.glDrawRangeElements(GL11.GL_TRIANGLES, 0, this.mesh.getIndicesCount(), this.mesh.getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
        this.mesh.disableVerticesPointer();

        this.mesh.unbind();

        this.shader.unbind();
    }

    @Override
    public void scale(Vector3f scale) {
        this.scale.x *= scale.x;
        this.scale.y *= scale.y;
        this.scale.z *= scale.z;
        this.computeMatrix();
    }

    @Override
    public void scale(float x, float y, float z) {
        this.scale(new Vector3f(x, y, z));
    }

    @Override
    public void computeMatrix() {
        super.getModelMatrix().setIdentity();
        super.getModelMatrix().translate(super.getPosition());
        Matrix4f.mul(super.getModelMatrix(), QuaternionUtils.quaternionToMatrix(super.getRotation()), super.getModelMatrix());
        super.getModelMatrix().scale(scale);
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
