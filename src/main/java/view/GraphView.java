package view;

import controller.Controller;
import controller.commands.CreateEdgeCommand;
import controller.commands.CreateVertexCommand;
import model.Edge;
import model.Graph;
import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.utils.GLRay;
import opengl.vertex.GLVertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.*;

/**
 * Created by Clement on 21/03/2015.
 */
public class GraphView implements Observer {
    private Controller controller;
    private Graph graph;

    private Map<Vertex, VertexView> vertexViews;
    private Map<Edge, EdgeView> edgeViews;
    private List<VertexView> vertexViewsOrdonned;
    private List<EdgeView> edgeViewsOrdonned;

    private GLColorVariantMesh vertexMesh;
    private GLColorVariantMesh edgeMesh;
    private GLShader labelShader;
    private GLShader vertexEdgeShader;

    private boolean isInitialized;

    public GraphView() {
        this.vertexViews = new HashMap<Vertex, VertexView>();
        this.edgeViews = new HashMap<Edge, EdgeView>();
        this.vertexViewsOrdonned = new ArrayList<VertexView>();
        this.edgeViewsOrdonned = new ArrayList<EdgeView>();
        this.isInitialized = false;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        if (this.isInitialized) {
            this.loadGraph(graph);
        }
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (this.isInitialized) {
            Graph graph = (Graph) observable;
            this.loadGraph(graph);
        }
    }

    public void init() {
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

        this.vertexEdgeShader = new GLShader("coloru3D.vert", "color.frag");
        this.labelShader = new GLShader("textureu3D.vert", "texture.frag");
        this.vertexEdgeShader.init();
        this.labelShader.init();

        this.vertexMesh = new GLColorVariantMesh();
        this.edgeMesh = new GLColorVariantMesh();

        this.vertexMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);
        this.edgeMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);

        this.vertexMesh.init();
        this.edgeMesh.init();

        if (this.graph != null) {
            this.loadGraph(this.graph);
        }
        this.isInitialized = true;
    }

    public void paint(Matrix4f transformationMatrix) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        for (Map.Entry<Edge, EdgeView> edgeEntry : this.edgeViews.entrySet()) {
            edgeEntry.getValue().render(transformationMatrix);
        }

        for (Map.Entry<Vertex, VertexView> vertexEntry : this.vertexViews.entrySet()) {
            vertexEntry.getValue().renderText(transformationMatrix);
        }

        for (Map.Entry<Edge, EdgeView> edgeEntry : this.edgeViews.entrySet()) {
            edgeEntry.getValue().renderText(transformationMatrix);
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        for (Map.Entry<Vertex, VertexView> vertexEntry : this.vertexViews.entrySet()) {
            vertexEntry.getValue().render(transformationMatrix);
        }
    }

    public void addVertex(Vector3f position) {
        Vertex vertex = new Vertex();
        vertex.setPosition(position);
        this.controller.executeCommand(new CreateVertexCommand(vertex));

        VertexView vertexView = new VertexView(vertex, this.vertexMesh, this.labelShader);
        vertexView.setShader(this.vertexEdgeShader);

        this.vertexViews.put(vertex, vertexView);
        this.vertexViewsOrdonned.add(vertexView);
    }

    public void addEdge(Vertex src, Vertex dst) {
        Edge edge = new Edge();
        edge.setSrcVertex(src);
        edge.setDstVertex(dst);
        this.controller.executeCommand(new CreateEdgeCommand(edge));

        EdgeView edgeView = new EdgeView(edge, this.vertexMesh, this.labelShader);
        edgeView.setShader(this.vertexEdgeShader);

        this.edgeViews.put(edge, edgeView);
        this.edgeViewsOrdonned.add(edgeView);
    }

    public VertexView getIntersectedVertexView(GLRay ray) {
        VertexView intersectedVertex = null;
        for (int i = 0; i < vertexViewsOrdonned.size() && intersectedVertex==null; i++) {
            if (vertexViewsOrdonned.get(i).isIntersected(ray))
                intersectedVertex = vertexViewsOrdonned.get(i);
        }
        return intersectedVertex;
    }

    public void loadGraph(Graph graph) {
        this.vertexViews = new HashMap<Vertex, VertexView>();
        this.edgeViews = new HashMap<Edge, EdgeView>();
        this.vertexViewsOrdonned = new ArrayList<VertexView>();
        this.edgeViewsOrdonned = new ArrayList<EdgeView>();

        for (Vertex vertex : graph.getVertices()) {
            VertexView vertexView = new VertexView(vertex, this.vertexMesh, this.labelShader);
            vertexView.setShader(this.vertexEdgeShader);
            this.vertexViews.put(vertex, vertexView);
            this.vertexViewsOrdonned.add(vertexView);
        }

        for (Edge edge : graph.getEdges()) {
            EdgeView edgeView = new EdgeView(edge, this.edgeMesh, this.labelShader);
            edgeView.setShader(this.vertexEdgeShader);
            this.edgeViews.put(edge, edgeView);
            this.edgeViewsOrdonned.add(edgeView);
        }
    }

    public Map<Vertex, VertexView> getVertexViews() {
        return vertexViews;
    }

    public Map<Edge, EdgeView> getEdgeViews() {
        return edgeViews;
    }

    public List<VertexView> getVertexViewsOrdonned() {
        return vertexViewsOrdonned;
    }

    public List<EdgeView> getEdgeViewsOrdonned() {
        return edgeViewsOrdonned;
    }
}
