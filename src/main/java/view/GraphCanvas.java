package view;

import model.Edge;
import model.Graph;
import model.Vertex;
import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.utils.GLRay;
import opengl.vertex.GLVertex;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private Graph graph;
    private GLPerspectiveCamera camera;
    private Collection<VertexView> vertexViews;
    private Collection<EdgeView> edgeViews;
    private GLColorVariantMesh vertexMesh;
    private GLColorVariantMesh edgeMesh;
    private GLShader labelShader;
    private GLShader vertexEdgeShader;

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

        this.vertexEdgeShader = new GLShader("coloru3D.vert", "color.frag");
        this.labelShader = new GLShader("textureu3D.vert", "texture.frag");

        List<GLVertex> vertices = new ArrayList<GLVertex>();

        GLVertex v0 = new GLVertex();
        GLVertex v1 = new GLVertex();
        GLVertex v2 = new GLVertex();
        GLVertex v3 = new GLVertex();

        v0.setPosition(-0.5f, -0.5f, 0.0f);
        v1.setPosition(0.5f, -0.5f, 0.0f);
        v2.setPosition(-0.5f, 0.5f, 0.0f);
        v3.setPosition(0.5f, 0.5f, 0.0f);

        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        int[] indices = {
            0, 1, 3,
            0, 2, 3
        };

        this.vertexMesh = new GLColorVariantMesh();
        this.edgeMesh = new GLColorVariantMesh();

        this.vertexMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);
        this.edgeMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);

        this.vertexMesh.init();
        this.edgeMesh.init();
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        for (VertexView vertexView : this.vertexViews) {
            vertexView.render(transformationMatrix);
        }

        for (EdgeView edgeView : this.edgeViews) {
            edgeView.render(transformationMatrix);
        }
    }

    public void onGraphChange() {
        this.loadGraph();
    }

    private void createObject(int x, int y) {
        GLRay ray = this.camera.getCursorRay(new Vector2f(x, y));
        //Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(distance), null);

        //this.graph.addVertex(new Vertex(position, mesh, shader, graph));
    }

    private void loadGraph() {
        this.vertexViews = new ArrayList<VertexView>();
        this.edgeViews = new ArrayList<EdgeView>();

        for (Vertex vertex : this.graph.getVertices()) {
            VertexView vertexView = new VertexView(vertex, this.labelShader);
            vertexView.setMesh(this.vertexMesh);
            vertexView.setShader(this.vertexEdgeShader);
        }

        for (Edge edge : this.graph.getEdges()) {
            EdgeView edgeView = new EdgeView(edge, this.labelShader);
            edgeView.setMesh(this.edgeMesh);
            edgeView.setShader(this.vertexEdgeShader);
        }
    }
}
