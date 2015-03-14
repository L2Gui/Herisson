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
import utils.MathUtils;
import utils.QuaternionUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OpenGLApp extends GLCanvas {
    private Lock mutex;
    private Font font;
    private GLColoredMesh mesh;
    private GLTextMesh textMesh;
    private GLDrawableObject drawableObject;
    private GLDrawableObject textObject;
    private IGLCamera camera;

    public static void main(String[] args) {
        try {
            JPanel panel = new JPanel();
            JFrame frame = new JFrame("Test");
            OpenGLApp app = new OpenGLApp();

            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.setSize(600, 600);

            frame.setContentPane(panel);
            panel.add(app);

            frame.setVisible(true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public OpenGLApp() throws Exception {
    }

    @Override
    public void init() {
        try {
            this.mesh = new GLColoredMesh();
            this.textMesh = new GLTextMesh();
            this.drawableObject = new GLDrawableObject();
            this.textObject = new GLDrawableObject();
            this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);

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

            colorShader.init();
            textureShader.init();
            this.mesh.init();
            this.textMesh.init();

            this.drawableObject.setShader(colorShader);
            this.drawableObject.setMesh(this.mesh);

            this.textObject.setShader(textureShader);
            this.textObject.setMesh(this.textMesh);

            super.setCamera(this.camera);

            Thread runThread = new Thread() {
                @Override
                public void run() {
                    OpenGLApp.this.run();
                }
            };

            runThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        this.drawableObject.render(transformationMatrix);
        this.textObject.render(transformationMatrix);
    }

    private void run() {
        try {
            this.textObject.setPosition(1.0f, 1.0f, 0.0f);
            Vector3f eye = new Vector3f(-0.5f, 0.5f, 5.0f);
            Vector3f direction = Vector3f.sub(new Vector3f(0.0f, 0.0f, 0.0f), eye, null);
            Vector3f idirection = new Vector3f(eye);

            Quaternion rotation = QuaternionUtils.quaternionLookRotation(direction);
            Quaternion irotation = QuaternionUtils.quaternionLookRotation(idirection);

            while (super.isShowing()) {
                Vector3f intermPos = MathUtils.vectorLerp(this.camera.getPosition(), eye, 0.02f);
                Quaternion intermRot = QuaternionUtils.quaternionSlerp(this.camera.getRotation(), rotation, 0.05f);
                Quaternion intermIRot = QuaternionUtils.quaternionSlerp(this.drawableObject.getRotation(), irotation, 0.05f);

                super.lockDraw();
                this.camera.setPosition(intermPos);
                this.camera.setRotation(intermRot);
                this.drawableObject.setRotation(intermIRot);
                this.textObject.setRotation(intermIRot);
                super.unlockDraw();
                Thread.sleep(16L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
