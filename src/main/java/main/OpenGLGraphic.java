package main;

import controller.KeyAction;
import controller.KeyBinding;
import oldOpenGL.GLCanvas;
import oldOpenGL.resource.object.GLObjectUsage;
import oldOpenGL.resource.object.camera.GLPerspectiveCamera;
import oldOpenGL.resource.object.camera.IGLCamera;
import oldOpenGL.resource.object.drawable.GLColoredObject;
import oldOpenGL.resource.object.drawable.GLTexturedObject;
import oldOpenGL.resource.object.drawable.IGLDrawable;
import oldOpenGL.resource.texture.GLFileTexture;
import oldOpenGL.resource.texture.GLTexture;
import oldOpenGL.utils.GLRay;
import oldOpenGL.vertex.GLColoredVertex;
import oldOpenGL.vertex.GLTexturedVertex;
import org.lwjgl.util.vector.*;
import utils.QuaternionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

public class OpenGLGraphic {
    private JFrame frame;
    private JPanel panel;
    private IGLCamera camera;
    private Vector3f translation;
    private GLCanvas canvas;
    private GLTexture quadTexture;
    private float distance;
    private int z_index = 0;
    private Collection<IGLDrawable> drawables = new ArrayList<IGLDrawable>();

    private Vector3f position = new Vector3f();
    private KeyBinding zDown = new KeyBinding();
    private KeyBinding sDown = new KeyBinding();
    private KeyBinding qDown = new KeyBinding();
    private KeyBinding dDown = new KeyBinding();

    public static void main(String args[]) throws InterruptedException {
        OpenGLGraphic app = new OpenGLGraphic();
        app.run();
    }

    public void run() throws InterruptedException {
        try {
            createWindow();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            distance = 5.0f;
            translation = new Vector3f(0.0f, 0.0f, - distance);

            this.canvas = new GLCanvas();
            camera = (IGLCamera) new GLPerspectiveCamera(70.0f, 0.001f, 100.0f);
            camera.translate(translation);

            quadTexture = new GLFileTexture("resource/png/ash_uvgrid04.png");


            // Indices des quads
            int[] indices = {
                    0, 1, 2, // Triangle 1
                    2, 3, 0  // Triangle 2
            };

            // Vertices du quad texturé
            GLTexturedVertex v0 = new GLTexturedVertex();
            GLTexturedVertex v1 = new GLTexturedVertex();
            GLTexturedVertex v2 = new GLTexturedVertex();
            GLTexturedVertex v3 = new GLTexturedVertex();

            v0.setPosition(new Vector3f(-.5f, 0.0f, .5f));
            v1.setPosition(new Vector3f(-.5f, 0.0f, -.5f));
            v2.setPosition(new Vector3f(.5f, 0.0f, -.5f));
            v3.setPosition(new Vector3f(.5f, 0.0f, .5f));

            v0.setColor(Color.red);
            v1.setColor(Color.green);
            v2.setColor(Color.blue);
            v3.setColor(Color.black);

            v0.setST(0.0f, 0.0f);
            v1.setST(1.0f, 0.0f);
            v2.setST(1.0f, 1.0f);
            v3.setST(0.0f, 1.0f);

            ArrayList<GLTexturedVertex> quadVertices = new ArrayList<GLTexturedVertex>();

            quadVertices.add(v0);
            quadVertices.add(v1);
            quadVertices.add(v2);
            quadVertices.add(v3);

            GLTexturedObject texturedObject = new GLTexturedObject();
            texturedObject.setup(quadTexture, quadVertices, indices, GLObjectUsage.STATIC);
            texturedObject.translate(position);
            texturedObject.scale(new Vector3f(50.0f, 0.0f, 50.0f));

            panel.add(canvas, BorderLayout.CENTER);


            Action actionZdown = new KeyAction(this.zDown, true);
            Action actionZup = new KeyAction(this.zDown, false);
            Action actionSdown = new KeyAction(this.sDown, true);
            Action actionSup = new KeyAction(this.sDown, false);
            Action actionQdown = new KeyAction(this.qDown, true);
            Action actionQup = new KeyAction(this.qDown, false);
            Action actionDdown = new KeyAction(this.dDown, true);
            Action actionDup = new KeyAction(this.dDown, false);

            this.panel.getInputMap().put(KeyStroke.getKeyStroke("Z"), "zDown");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("released Z"), "zUp");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("S"), "sDown");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("released S"), "sUp");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("Q"), "qDown");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("released Q"), "qUp");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("D"), "dDown");
            this.panel.getInputMap().put(KeyStroke.getKeyStroke("released D"), "dUp");

            this.panel.getActionMap().put("zDown", actionZdown);
            this.panel.getActionMap().put("zUp", actionZup);
            this.panel.getActionMap().put("sDown", actionSdown);
            this.panel.getActionMap().put("sUp", actionSup);
            this.panel.getActionMap().put("qDown", actionQdown);
            this.panel.getActionMap().put("qUp", actionQup);
            this.panel.getActionMap().put("dDown", actionDdown);
            this.panel.getActionMap().put("dUp", actionDup);

            canvas.addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent arg0) {

                }

                @Override
                public void mousePressed(MouseEvent arg0) {
                    if (arg0.getButton() == MouseEvent.BUTTON1) {
                        OpenGLGraphic.this.createObject(arg0.getX(), arg0.getY(), distance);
                    } else if (arg0.getButton() == MouseEvent.BUTTON3) {
                        int x = arg0.getX();
                        int y = arg0.getY();

                        y = OpenGLGraphic.this.canvas.getHeight() - y;
                        GLRay ray = OpenGLGraphic.this.camera.getCursorRay(new Vector2f(x, y));

                        boolean over = false;
                        for (IGLDrawable drawable : OpenGLGraphic.this.drawables) {
                            if (drawable.isIntersected(ray)) {
                                over = true;
                            }
                            if (over) {
                                break;
                            }
                        }

                        if (over) {
                            System.out.println("INTERSECTION !!!!");
                        } else {
                            System.out.println("PAS INTERSECTION !!!!");
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent arg0) {

                }

                @Override
                public void mouseEntered(MouseEvent arg0) {

                }

                @Override
                public void mouseClicked(MouseEvent arg0) {

                }
            });

            canvas.setCamera(camera);
            canvas.add(quadTexture);
            canvas.add(this.z_index, texturedObject);
            this.z_index++;
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);

        float speed = 0.1f;

        while (frame.isVisible()) {
            this.canvas.lockDraw();

            if (zDown.getState()) {
                Quaternion quat = this.camera.getRotation();
                Matrix4f quatMatrix = QuaternionUtils.quaternionToMatrix(quat);
                Vector4f translation = Matrix4f.transform(quatMatrix, new Vector4f(0.0f, 0.0f, -speed, 0.0f), null);
                this.position.translate(translation.x, translation.y, translation.z);
                this.camera.translate(new Vector3f(translation.x, translation.y, translation.z));
            }
            if (sDown.getState()) {
                Quaternion quat = this.camera.getRotation();
                Matrix4f quatMatrix = QuaternionUtils.quaternionToMatrix(quat);
                Vector4f translation = Matrix4f.transform(quatMatrix, new Vector4f(0.0f, 0.0f, speed, 0.0f), null);
                this.position.translate(translation.x, translation.y, translation.z);
                this.camera.translate(new Vector3f(translation.x, translation.y, translation.z));
            }
            if (qDown.getState()) {
                this.camera.translate(new Vector3f(position).negate(null));
                this.camera.rotate(speed * 0.25f, new Vector3f(0.0f, 1.0f, 0.0f));
                this.camera.translate(position);
            }
            if (dDown.getState()) {
                this.camera.translate(new Vector3f(position).negate(null));
                this.camera.rotate(-speed * 0.25f, new Vector3f(0.0f, 1.0f, 0.0f));
                this.camera.translate(position);
            }

            this.canvas.unlockDraw();
            Thread.sleep(16L);
        }
    }

    private void createObject(int x, int y, float distance) {
        // Changement de repère
        y = OpenGLGraphic.this.canvas.getHeight() - y;
        GLRay ray = OpenGLGraphic.this.camera.getCursorRay(new Vector2f(x, y));
        Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(distance), null);

        // Indices des quads
        int[] indices = {
                0, 1, 2, // Triangle 1
                2, 3, 0  // Triangle 2
        };

        // Vertices du quad texturé
        GLColoredVertex v0 = new GLColoredVertex();
        GLColoredVertex v1 = new GLColoredVertex();
        GLColoredVertex v2 = new GLColoredVertex();
        GLColoredVertex v3 = new GLColoredVertex();

        v0.setPosition(new Vector3f(-.5f, .5f, 0.0f));
        v1.setPosition(new Vector3f(-.5f, -.5f, 0.0f));
        v2.setPosition(new Vector3f(.5f, -.5f, 0.0f));
        v3.setPosition(new Vector3f(.5f, .5f, 0.0f));

        v0.setColor(Color.gray);
        v1.setColor(Color.gray);
        v2.setColor(Color.gray);
        v3.setColor(Color.gray);

        ArrayList<GLColoredVertex> quadVertices = new ArrayList<GLColoredVertex>();

        quadVertices.add(v0);
        quadVertices.add(v1);
        quadVertices.add(v2);
        quadVertices.add(v3);

        GLColoredObject coloredObject = new GLColoredObject();
        coloredObject.setup(quadVertices, indices, GLObjectUsage.STATIC);
        coloredObject.translate(position);

        drawables.add(coloredObject);
        canvas.add(this.z_index, coloredObject);
        this.z_index++;
    }

    private void createWindow() throws Exception {
        frame = new JFrame("Test");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        this.panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setContentPane(panel);
    }
}