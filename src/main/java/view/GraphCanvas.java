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
import org.lwjgl.util.vector.Vector3f;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private Graph graph;
    private GLPerspectiveCamera camera;
    private Map<Vertex, VertexView> vertexViews;
    private Map<Edge, EdgeView> edgeViews;
    private GLColorVariantMesh vertexMesh;
    private GLColorVariantMesh edgeMesh;
    private GLShader labelShader;
    private GLShader vertexEdgeShader;

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
        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        this.camera.lookToDirection(new Vector3f(0.0f, 0.0f, -1.0f));
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
        this.vertexEdgeShader.init();
        this.labelShader.init();

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

        this.graph = GraphCanvas.getSampleGraph();
        this.loadGraph();

        this.camera.setPosition(0.0f, 0.0f, 10.0f);
    }

    @Override
    public void paint(Matrix4f transformationMatrix) {
        for (Map.Entry<Vertex, VertexView> vertexEntry : this.vertexViews.entrySet()) {
            vertexEntry.getValue().render(transformationMatrix);
        }

        for (Map.Entry<Edge, EdgeView> edgeEntry : this.edgeViews.entrySet()) {
            edgeEntry.getValue().render(transformationMatrix);
        }

        for (Map.Entry<Vertex, VertexView> vertexEntry : this.vertexViews.entrySet()) {
            vertexEntry.getValue().renderText(transformationMatrix);
        }

        for (Map.Entry<Edge, EdgeView> edgeEntry : this.edgeViews.entrySet()) {
            edgeEntry.getValue().renderText(transformationMatrix);
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
        super.lockDraw();

        this.vertexViews = new HashMap<Vertex, VertexView>();
        this.edgeViews = new HashMap<Edge, EdgeView>();

        for (Vertex vertex : this.graph.getVertices()) {
            VertexView vertexView = new VertexView(vertex, this.vertexMesh, this.labelShader);
            vertexView.setShader(this.vertexEdgeShader);
            this.vertexViews.put(vertex, vertexView);
        }

        for (Edge edge : this.graph.getEdges()) {
            EdgeView edgeView = new EdgeView(edge, this.edgeMesh, this.labelShader);
            edgeView.setShader(this.vertexEdgeShader);
            this.edgeViews.put(edge, edgeView);
        }

        super.unlockDraw();
    }

    private static Graph getSampleGraph() {
        Graph g = new Graph();
        g.setName("graphe test");
        Vertex v0 = new Vertex();
        v0.setPosition(new Vector3f(2f, 0f, 0f));
        v0.setLabel("Coucou");
        Vertex v1 = new Vertex();
        v1.setPosition(new Vector3f(4f,-3f,0f));
        v1.setLabel("Tranquille ?");
        Vertex v2 = new Vertex();
        v2.setPosition(new Vector3f(-4f,3f,0f));
        Vertex v3 = new Vertex();
        v3.setPosition(new Vector3f(0f,0f,0f));
        Vertex v4 = new Vertex();
        v4.setPosition(new Vector3f(2f,5f,0f));
        Edge edge = new Edge();
        edge.setSrcVertex(v0);
        edge.setDstVertex(v1);

        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(edge);

        return g;
    }
}
