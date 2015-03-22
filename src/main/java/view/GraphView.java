package view;

import controller.Controller;
import controller.command.CreateEdgeCommand;
import controller.command.CreateVertexCommand;
import model.Edge;
import model.Graph;
import model.GraphUpdate;
import model.Vertex;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.resource.object.mesh.GLMesh;
import opengl.utils.GLRay;
import opengl.vertex.GLVertex;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import utils.ColorUtils;
import utils.MathUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Clement on 21/03/2015.
 */
public class GraphView implements Observer {
    private Controller controller;
    private Graph graph;

    private Map<Vertex, VertexView> vertexViews;
    private Map<Edge, EdgeView> edgeViews;

    private Collection<VertexView> createdVertices;
    private Map<VertexView, Vector3f> translatingVertices;
    private Map<VertexView, Color> coloringVertices;

    private GLColorVariantMesh vertexSquareMesh;
    private GLColorVariantMesh vertexCircleMesh;
    private GLColorVariantMesh vertexDiamondMesh;
    private GLColorVariantMesh edgeMesh;


    private GLShader labelShader;
    private GLShader vertexEdgeShader;

    private float animationSpeed = 0.05f;
    private boolean isInitialized;

    public GraphView() {
        this.createdVertices = new ArrayList<VertexView>();
        this.translatingVertices = new HashMap<VertexView, Vector3f>();
        this.coloringVertices = new HashMap<VertexView, Color>();
        this.vertexViews = new HashMap<Vertex, VertexView>();
        this.edgeViews = new HashMap<Edge, EdgeView>();
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
            GraphUpdate update = (GraphUpdate) o;

            switch (update.getType()) {
            case ADD_VERTEX: addVertex((Vertex) update.getSubject()); break;
            case ADD_EDGE: addEdge((Edge) update.getSubject()); break;
            case REMOVE_VERTEX: removeVertex((Vertex) update.getSubject()); break;
            case REMOVE_EDGE: removeEdge((Edge) update.getSubject()); break;
            default: break;
            }
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

        this.vertexSquareMesh = new GLColorVariantMesh();
        this.edgeMesh = new GLColorVariantMesh();

        this.vertexSquareMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);
        this.vertexCircleMesh = GraphView.SetupCircleMesh(40, this.vertexEdgeShader);
        this.vertexDiamondMesh = GraphView.SetupCircleMesh(4, this.vertexEdgeShader);
        this.edgeMesh.setup(this.vertexEdgeShader, vertices, indices, GLObjectUsage.STATIC);

        this.vertexCircleMesh.init();
        this.vertexSquareMesh.init();
        this.vertexDiamondMesh.init();
        this.edgeMesh.init();

        if (this.graph != null) {
            this.loadGraph(this.graph);
        }
        this.isInitialized = true;
    }
    public void reloadVertex(Vertex v){
        loadGraph(graph);
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

        this.addVertex(vertex);
    }

    public void addVertex(Vertex vertex) {
        GLColorVariantMesh mesh = null;
        switch (vertex.getShape()) {
            case SQUARE: mesh = this.vertexSquareMesh; break;
            case CIRCLE: mesh = this.vertexCircleMesh; break;
            case DIAMOND: mesh = this.vertexDiamondMesh; break;
        }

        VertexView vertexView = new VertexView(vertex, mesh, this.vertexEdgeShader, this.labelShader);
        vertexView.setPosition(vertexView.getPosition().x, vertexView.getPosition().y, 3.0f);

        this.createdVertices.add(vertexView);
        this.vertexViews.put(vertex, vertexView);
    }

    public void addEdge(Vertex src, Vertex dst) {

        Edge edge = new Edge();
        edge.setSrcVertex(src);
        edge.setDstVertex(dst);
        if(!(src.gotAnEdgeDirectedTo(dst) && dst.gotAnEdgeComingFrom(src))) {
            this.controller.executeCommand(new CreateEdgeCommand(edge));
            this.addEdge(edge);
        }
    }

    public void addEdge(Edge edge) {
        EdgeView edgeView = new EdgeView(edge, this.vertexSquareMesh, this.labelShader);
        edgeView.setShader(this.vertexEdgeShader);

        this.edgeViews.put(edge, edgeView);
    }

    private void removeVertex(Vertex vertex) {
        this.vertexViews.remove(vertex);
    }

    private void removeEdge(Edge edge) {
        this.edgeViews.remove(edge);
    }

    public VertexView getIntersectedVertexView(GLRay ray) {
        VertexView intersectedVertex = null;
        //TODO
        for (Map.Entry<Vertex, VertexView> entry : this.vertexViews.entrySet()) {
            if (entry.getValue().isIntersected(ray)) {
                intersectedVertex = entry.getValue();
                break;
            }
        }

        return intersectedVertex;
    }

    public EdgeView getIntersectedEdgeView(GLRay ray) {
        EdgeView intersectedEdge = null;
        //TODO
        for (Map.Entry<Edge, EdgeView> entry : this.edgeViews.entrySet()) {
            if (entry.getValue().isIntersected(ray)) {
                intersectedEdge = entry.getValue();
                break;
            }
        }
        return intersectedEdge;
    }

    public void addTranslatingVertex(VertexView vertexView, Vector3f position) {
        this.translatingVertices.put(vertexView, position);
    }

    public void removeTranslatingVertex(VertexView vertexView) {
        this.translatingVertices.remove(vertexView);
    }

    public void addColoringVertex(Vertex vertex, Color color) {
        this.coloringVertices.put(this.vertexViews.get(vertex), color);
    }

    public void loadGraph(Graph graph) {
        this.vertexViews.clear();
        this.edgeViews.clear();

        for (Vertex vertex : graph.getVertices()) {
            this.addVertex(vertex);
        }

        for (Edge edge : graph.getEdges()) {
            this.addEdge(edge);
        }
    }

    public Map<Vertex, VertexView> getVertexViews() {
        return vertexViews;
    }

    public Map<Edge, EdgeView> getEdgeViews() {
        return edgeViews;
    }

    public void animate() {
        Collection<VertexView> finishedVertices = new ArrayList<VertexView>();

        for (VertexView vertexView : this.createdVertices) {
            if (vertexView.getPosition().z < 0.01f) {
                vertexView.getPosition().setZ(0.0f);
                finishedVertices.add(vertexView);
            } else {
                Vector3f position = vertexView.getPosition();
                Vector3f target = new Vector3f(position);
                target.z = 0.0f;
                Vector3f interm = MathUtils.vectorLerp(position, target, this.animationSpeed);
                vertexView.setPosition(interm);
            }
        }

        this.createdVertices.removeAll(finishedVertices);
        finishedVertices.clear();

        for (Map.Entry<VertexView, Vector3f> entry : this.translatingVertices.entrySet()) {
            float distance = Vector3f.sub(entry.getKey().getPosition(), entry.getValue(), null).length();
            if (distance < 0.01f) {
                entry.getKey().setPosition(entry.getValue());
                finishedVertices.add(entry.getKey());
            } else {
                entry.getKey().translateTo(entry.getValue(), 0.05f);
            }
        }

        for (VertexView vertexView : finishedVertices) {
            if (this.translatingVertices.containsKey(vertexView)) {
                this.translatingVertices.remove(vertexView);
            }
        }
        finishedVertices.clear();

        for (Map.Entry<VertexView, Color> entry : this.coloringVertices.entrySet()) {
            float distance = ColorUtils.colorDiff(entry.getKey().getModel().getBackgroundColor(), entry.getValue());
            if (distance < 0.01f) {
                entry.getKey().getModel().setBackgroundColor(entry.getValue());
                finishedVertices.add(entry.getKey());
            } else {
                Color interm = ColorUtils.colorLerp(entry.getKey().getModel().getBackgroundColor(), entry.getValue(), 0.05f);
                entry.getKey().getModel().setBackgroundColor(interm);
            }
        }

        for (VertexView vertexView : finishedVertices) {
            if (this.coloringVertices.containsKey(vertexView)) {
                this.coloringVertices.remove(vertexView);
            }
        }
        finishedVertices.clear();
    }

    private static GLColorVariantMesh SetupCircleMesh(int nbSides, GLShader shader) {
        GLColorVariantMesh mesh = new GLColorVariantMesh();
        ArrayList<GLVertex> vertices = new ArrayList<GLVertex>();

        GLVertex center = new GLVertex();
        center.setPosition(0.0f, 0.0f, 0.0f);
        vertices.add(center);

        double midNbSides = (double) nbSides / 2.0f;

        for (int i = 0;i < nbSides;i++) {
            double x = Math.cos(((double) i * Math.PI) / midNbSides) / 2.0f;
            double y = Math.sin(((double) i * Math.PI) / midNbSides) / 2.0f;
            GLVertex vertex = new GLVertex();
            vertex.setPosition((float) x, (float) y, 0.0f);

            vertices.add(vertex);
        }

        int[] indices = new int[(nbSides) * 3];
        for (int i = 1;i < nbSides;i++) {
            indices[(i - 1) * 3] = 0;
            indices[(i - 1) * 3 + 1] = i;
            indices[(i - 1) * 3 + 2] = i + 1;
        }

        indices[indices.length - 3] = 0;
        indices[indices.length - 2] = 1;
        indices[indices.length - 1] = vertices.size() - 1;

        mesh.setup(shader, vertices, indices, GLObjectUsage.STATIC);
        return mesh;
    }
}
