package main;

import opengl.resource.GLShader;
import opengl.GLCanvas;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.resource.object.mesh.GLMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import view.VertexView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 12/03/2015.
 */
public class AppTestOpenGL extends GLCanvas {
    private GLMesh mesh;
    private VertexView vertexView;
    private IGLCamera camera;

    public AppTestOpenGL() throws LWJGLException {

    }

    public static void main(String[] args) {
        try {
            AppTestOpenGL app = new AppTestOpenGL();
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.getContentPane().add(app);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        GLShader shader = new GLShader("color3D.vert", "color.frag");
        shader.init();

        List<GLColoredVertex> vertices = new ArrayList<GLColoredVertex>();

        GLColoredVertex v0 = new GLColoredVertex();
        GLColoredVertex v1 = new GLColoredVertex();
        GLColoredVertex v2 = new GLColoredVertex();
        GLColoredVertex v3 = new GLColoredVertex();

        v0.setPosition(-0.5f, -0.5f, 0.0f);
        v1.setPosition(0.5f, -0.5f, 0.0f);
        v2.setPosition(-0.5f, 0.5f, 0.0f);
        v3.setPosition(0.5f, 0.5f, 0.0f);

        v0.setColor(Color.red);
        v1.setColor(Color.red);
        v2.setColor(Color.red);
        v3.setColor(Color.red);

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        int[] indices = {
                0, 1, 3,
                0, 2, 3
        };

        GLColoredMesh mesh = new GLColoredMesh();
        mesh.setup(vertices, indices, GLObjectUsage.STATIC);
        mesh.init();
        this.mesh = mesh;

        this.vertexView = new VertexView();
        this.vertexView.setShader(shader);
        this.vertexView.setMesh(this.mesh);

        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        super.setCamera(camera);

        this.camera.setPosition(0.0f, 0.0f, -5.0f);
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        this.vertexView.render(transformationMatrix);
    }
}
