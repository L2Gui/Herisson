package view;

import model.Graph;
import model.Vertex;
import opengl.GLCanvas;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.camera.IGLCamera;
import opengl.utils.GLRay;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private Graph graph;
    private IGLCamera camera;
    private float distance;

    public GraphCanvas() throws LWJGLException {}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.onGraphChange();
    }

    @Override
    public void init() {
        super.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) {
                    GraphCanvas.this.createObject(arg0.getX(), arg0.getY(), distance);
                }
            }
        });

        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        super.setCamera(this.camera);
    }

    @Override
    public void paint(Matrix4f projectionViewMatrix) {

    }

    public void onGraphChange() {

    }

    // FONCTION DEPLACEE
    private void createObject(int x, int y, float distance) { //C
        GLRay ray = this.camera.getCursorRay(new Vector2f(x, y));
        Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(distance), null);

        this.graph.addVertex(new Vertex(position));
    }


}
