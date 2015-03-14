package main;

import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.drawable.GLDrawableObject;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 14/03/2015.
 */
public class TestAMD extends GLCanvas {
    private GLColoredMesh mesh;
    private GLDrawableObject object;

    public static void main(String[] args) {
        TestAMD canvas = null;
        try {
            canvas = new TestAMD();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Test AMD");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setSize(800, 600);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public TestAMD() throws LWJGLException {
        this.mesh = new GLColoredMesh();
        this.object = new GLDrawableObject();
    }

    @Override
    public void init() {
        GLShader colorShader = new GLShader("color3D.vert", "color.frag");
        colorShader.init();

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
        v1.setColor(Color.blue);
        v2.setColor(Color.green);
        v3.setColor(Color.white);

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        int[] indices = {
                0, 1, 3,
                0, 2, 3
        };

        this.mesh.setup(vertices, indices, GLObjectUsage.STATIC);
        this.mesh.init();

        this.object.setMesh(this.mesh);
        this.object.setShader(colorShader);
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        this.object.render(transformationMatrix);
    }
}
