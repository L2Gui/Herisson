package view;

import model.Graph;
import opengl.GLCanvas;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.utils.GLRay;
import opengl.vertex.GLColoredVertex;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private Graph graph;
    private GLPerspectiveCamera camera;

    public GraphCanvas() throws LWJGLException {}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        super.lockDraw();
        this.graph = graph;
        this.onGraphChange();
        super.unlockDraw();
    }

    @Override
    public void init() {
        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        super.setCamera(this.camera);

        super.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    GraphCanvas.this.createObject(arg0.getX(), arg0.getY());
                }
            }
        });

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
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {

    }

    public void onGraphChange() {

    }

    private void createObject(int x, int y) {
        GLRay ray = this.camera.getCursorRay(new Vector2f(x, y));
        //Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(distance), null);

        //this.graph.addVertex(new Vertex(position, mesh, shader, graph));
    }
}
