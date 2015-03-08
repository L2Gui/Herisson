package main;

import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.drawable.GLDrawableObject;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import utils.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OpenGLApp {
    private GLCanvas canvas;
    private JPanel panel;
    private JFrame frame;
    private GLColoredMesh mesh;
    private GLDrawableObject drawableObject;
    private IGLCamera camera;

    public static void main(String[] args) {
        OpenGLApp app = new OpenGLApp();
        app.run();
    }

    public void run() {
        try {
            this.canvas = new GLCanvas();
            this.frame = new JFrame("Test");
            this.panel = new JPanel();
            this.mesh = new GLColoredMesh();
            this.drawableObject = new GLDrawableObject();
            this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);

            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.setLocationByPlatform(true);
            this.frame.setSize(600, 600);

            this.frame.setContentPane(this.panel);
            this.panel.add(this.canvas);

            this.frame.setVisible(true);

            GLShader shader = new GLShader("color3D.vert", "color.frag");

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

            this.drawableObject.setShader(shader);
            this.drawableObject.setMesh(this.mesh);

            this.canvas.addResource(shader);
            this.canvas.addResource(this.mesh);
            this.canvas.addDrawable(0, this.drawableObject);
            this.canvas.setCamera(this.camera);

            float speed = 0.01f;
            Vector3f dest = new Vector3f(-5.0f, 2.0f, 5.0f);

            while (this.frame.isVisible()) {
                Vector3f position = this.camera.getPosition();
                Vector3f newPosition = MathUtils.vectorLerp(position, dest, speed);

                Quaternion cameraToObject = MathUtils.quaternionLookAt(position, dest);
                Quaternion objectToCamera = MathUtils.invertQuaternion(cameraToObject);
                Quaternion objectToCameraSlerped = MathUtils.quaternionSlerp(this.drawableObject.getRotation(), objectToCamera, speed);

                this.camera.setRotation(cameraToObject);
                this.drawableObject.setRotation(objectToCameraSlerped);

                this.canvas.lockDraw();
                this.camera.setPosition(newPosition);
                this.canvas.unlockDraw();
                Thread.sleep(16L);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
