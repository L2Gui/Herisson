package main;

import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.drawable.GLDrawableObject;
import opengl.resource.object.mesh.GLColoredMesh;
import opengl.resource.object.mesh.GLTextMesh;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import utils.MathUtils;
import utils.QuaternionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OpenGLApp {
    private GLCanvas canvas;
    private JPanel panel;
    private JFrame frame;
    private Font font;
    private GLColoredMesh mesh;
    private GLTextMesh textMesh;
    private GLDrawableObject drawableObject;
    private GLDrawableObject textObject;
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
            this.textMesh = new GLTextMesh();
            this.drawableObject = new GLDrawableObject();
            this.textObject = new GLDrawableObject();
            this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);

            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.setLocationByPlatform(true);
            this.frame.setSize(600, 600);

            this.frame.setContentPane(this.panel);
            this.panel.add(this.canvas);

            this.frame.setVisible(true);

            GLShader colorShader = new GLShader("color3D.vert", "color.frag");
            GLShader textureShader = new GLShader("texture3D.vert", "texture.frag");

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

            this.font = new Font("Arial", Font.PLAIN, 512);

            this.mesh.setup(vertices, indices, GLObjectUsage.STATIC);
            this.textMesh.setup("Test", this.font, 0.5f, GLObjectUsage.STATIC);

            this.drawableObject.setShader(colorShader);
            this.drawableObject.setMesh(this.mesh);

            this.textObject.setShader(textureShader);
            this.textObject.setMesh(this.textMesh);

            this.canvas.addResource(colorShader);
            this.canvas.addResource(textureShader);
            this.canvas.addResource(this.mesh);
            this.canvas.addResource(this.textMesh);
            this.canvas.addDrawable(0, this.drawableObject);
            this.canvas.setCamera(this.camera);

            Vector3f eye = new Vector3f(-1.0f, 2.0f, -4.0f);
            Vector3f direction = Vector3f.sub(new Vector3f(0.0f, 0.0f, 0.0f), eye, null);
            Vector3f idirection = new Vector3f(eye);

            Quaternion rotation = QuaternionUtils.quaternionLookRotation(direction);
            Quaternion irotation = QuaternionUtils.quaternionLookRotation(idirection);

            while (frame.isVisible()) {

                Vector3f intermPos = MathUtils.vectorLerp(this.camera.getPosition(), eye, 0.02f);
                Quaternion intermRot = QuaternionUtils.quaternionSlerp(this.camera.getRotation(), rotation, 0.1f);
                Quaternion intermIRot = QuaternionUtils.quaternionSlerp(this.drawableObject.getRotation(), irotation, 0.1f);

                this.canvas.lockDraw();
                this.camera.setPosition(intermPos);
                this.camera.setRotation(intermRot);
                this.drawableObject.setRotation(intermIRot);
                this.canvas.unlockDraw();
                Thread.sleep(16L);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
