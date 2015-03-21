package view;

import controller.Controller;
import controller.ControllerState;
import controller.actions.*;
import controller.commands.CreateVertexCommand;
import model.*;
import opengl.GLCanvas;
import opengl.resource.GLShader;
import opengl.resource.object.GLObjectUsage;
import opengl.resource.object.camera.GLPerspectiveCamera;
import opengl.resource.object.mesh.GLColorVariantMesh;
import opengl.utils.GLRay;
import opengl.vertex.GLVertex;
import org.javatuples.Pair;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Clement on 13/03/2015.
 */
public class GraphCanvas extends GLCanvas {
    private Graph graph;
    private Controller controller;
    private GLPerspectiveCamera camera;
    private Map<Vertex, VertexView> vertexViews;
    private Map<Edge, EdgeView> edgeViews;
    private List<VertexView> vertexViewsOrdonned;
    private List<EdgeView> edgeViewsOrdonned;

    private GLColorVariantMesh vertexMesh;
    private GLColorVariantMesh edgeMesh;
    private GLShader labelShader;
    private GLShader vertexEdgeShader;

    //attributs utilitaires
    private boolean isMousePressed = false;
    VertexView selectedVertex;

    public GraphCanvas() throws LWJGLException {}

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.onGraphChange();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void init() {
        this.camera = new GLPerspectiveCamera(70.0f, 0.01f, 100.0f);
        this.camera.lookToDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        this.camera.setPosition(new Vector3f(0.0f, 0.0f, 10.0f));
        super.setCamera(this.camera);

        super.addMouseListener(new MouseInputAdapter() {
            public void initGraphCanvas() {
                try {
                    GraphCanvas.this.makeCurrent();
                } catch (LWJGLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent arg0) {

                initGraphCanvas();

                if (arg0.getButton() == MouseEvent.BUTTON1) { // CLIC GAUCHE

                    VertexView intersectedVertex = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    if (intersectedVertex != null) {
                        GraphCanvas.this.selectedVertex = intersectedVertex;
                        isMousePressed = true;
                    }
                    switch(GraphCanvas.this.controller.getState())
                    {
                        case VERTEX_CREATION:

                            if(intersectedVertex == null) {
                                GraphCanvas.this.createVertex(arg0.getX(), arg0.getY());
                            }
                            break;

                        case EDGE_CREATION:
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            break;

                        case DELETION:
                            /*if(intersectedVertex != null) {
                                ViewElement viewElement = intersectedVertex;
                                GraphCanvas.this.deleteElement(viewElement);
                            }*/
                            break;

                        default:
                    }


                } else if (arg0.getButton() == MouseEvent.BUTTON3) { // CLIC DROIT

                    VertexView intersectedVertex = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    switch(GraphCanvas.this.controller.getState())
                    {
                        case VERTEX_CREATION:

                            break;

                        case EDGE_CREATION:
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            break;

                        case DELETION:
                            break;

                        default:
                            System.out.println("ERREUR STATE");
                    }

                    if (intersectedVertex != null) {
                        getPopupOnVertex(intersectedVertex).show(arg0.getComponent(), arg0.getX(), arg0.getY());
                    } else {
                        System.out.println("PAS INTERSECTION !!!!");
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {

                initGraphCanvas();

                if (arg0.getButton() == MouseEvent.BUTTON1) {

                    isMousePressed = false;
                    VertexView intersectedVertex = getIntersectedVertexView(arg0.getX(), arg0.getY());
                    switch (GraphCanvas.this.controller.getState()) {
                        case VERTEX_CREATION:
                            break;

                        case EDGE_CREATION:
                            if (intersectedVertex != null && selectedVertex != null)
                                createEdge(GraphCanvas.this.selectedVertex, intersectedVertex);
                            break;

                        case VERTEX_EDITION:
                            break;

                        case EDGE_EDITION:
                            break;

                        case MOVE:
                            break;

                        case DELETION:
                            break;

                        default:
                            System.out.println("ERREUR STATE");
                    }
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
    }

    @Override
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

    public void onGraphChange() {
        this.loadGraph();
    }

    ////////////////////////////// CREATION D'UN NOEUD //////////////////////////////////////////////////////
    private void createVertex(int x, int y) {
        System.out.println("Clic");

        GLRay ray = this.camera.getCursorRay(new Vector2f(x, super.getSize().height - y));

        /**
         * La partie suivante sert à trouver la distance à laquelle afficher le sommet de manière à ce qu'il soit sur le même plan 2D que les autres.
         * Pour comprendre la démonstration mathématique, demandez à Louis, Corentin ou Clément
         */
        Vector3f vecDirection = ray.getDirection(); //vecteur direction clic souris
        Vector3f vecCamera = new Vector3f(0,0, -this.camera.getPosition().getZ());

        vecDirection = vecDirection.normalise(null);
        vecCamera = vecCamera.normalise(null);

        double alpha; //angle entre les deux vecteurs (camera et direction du clic)
        float hypothenuse;

        alpha = Math.acos(Vector3f.dot(vecCamera, vecDirection)); //produit scalaire entre les deux vecteurs !
        hypothenuse = this.camera.getPosition().getZ() / (float)Math.cos(alpha);

        Vector3f position = Vector3f.add(ray.getPosition(), (Vector3f) ray.getDirection().scale(hypothenuse), null);

        Vertex v = new Vertex(graph);
        v.setPosition(position);
        //v.setLabel("sommet");

        this.controller.executeCommand(new CreateVertexCommand(v));

        VertexView vv = new VertexView(v, this.vertexMesh, this.labelShader);
        vv.setShader(this.vertexEdgeShader);

        this.vertexViews.put(v, vv);
        this.vertexViewsOrdonned.add(vv);
    }

    ////////////////////////////// CREATION D'UNE ARETE //////////////////////////////////////////////////////
    private void createEdge(VertexView source, VertexView dest) {

        Vertex srcVertex = source.getModel();
        Vertex dstVertex = dest.getModel();

        Edge edge = new Edge(graph, srcVertex, dstVertex);

        EdgeView edgeView = new EdgeView(edge, this.vertexMesh, this.labelShader);
        edgeView.setShader(this.vertexEdgeShader);

        //il faut trouver comment ajouter les edges aux attribyt de la classe Vertex (sans doute dans constructeur edge)

        this.edgeViews.put(edge, edgeView);
        this.edgeViewsOrdonned.add(edgeView);
    }

    /////////////////////////////// RETOURNE VERTEX VIEW INTERSECTED //////////////////////////////////////////
    private VertexView getIntersectedVertexView(int x, int y) {
        y = GraphCanvas.this.getHeight() - y; //car y swing et y canvas sont inversés
        GLRay ray = GraphCanvas.this.camera.getCursorRay(new Vector2f(x, y));

        VertexView intersectedVertex = null;
        for (int i = 0; i < vertexViewsOrdonned.size() && intersectedVertex==null; i++) {
            if (vertexViewsOrdonned.get(i).isIntersected(ray))
                intersectedVertex = vertexViewsOrdonned.get(i);
            }
        return intersectedVertex;
    }

    ////////////////////////////////////////// GENERATION DES POPUPS //////////////////////////////////////////
    private JPopupMenu getPopupOnVertex(VertexView vertexView){
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.add(new EditVertexNowAction(null, vertexView));    // passer le vertexview en question en param
        contextMenu.add(new JPopupMenu.Separator());
        contextMenu.add(new UndoAction());
        contextMenu.add(new RedoAction());
        contextMenu.add(new ZoomPlusAction());
        contextMenu.add(new ZoomLessAction());
        return contextMenu;
    }

    private void loadGraph() {
        super.lockDraw();

        this.vertexViews = new HashMap<Vertex, VertexView>();
        this.edgeViews = new HashMap<Edge, EdgeView>();
        this.vertexViewsOrdonned = new ArrayList<VertexView>();
        this.edgeViewsOrdonned = new ArrayList<EdgeView>();

        for (Vertex vertex : this.graph.getVertices()) {
            VertexView vertexView = new VertexView(vertex, this.vertexMesh, this.labelShader);
            vertexView.setShader(this.vertexEdgeShader);
            this.vertexViews.put(vertex, vertexView);
            this.vertexViewsOrdonned.add(vertexView);
        }

        for (Edge edge : this.graph.getEdges()) {
            EdgeView edgeView = new EdgeView(edge, this.edgeMesh, this.labelShader);
            edgeView.setShader(this.vertexEdgeShader);
            this.edgeViews.put(edge, edgeView);
            this.edgeViewsOrdonned.add(edgeView);
        }

        super.unlockDraw();
    }

    private static Graph getSampleGraph() {
        Graph g = new Graph();
        g.setName("graphe test");
        Vertex v0 = new Vertex();
        v0.setPosition(new Vector3f(5f, 5f, 0f));
        v0.setLabel("Coucou");
        Vertex v1 = new Vertex();
        v1.setPosition(new Vector3f(4f,-3f,0f));
        v1.setLabel("Tranquille ?");
        Vertex v2 = new Vertex();
        v2.setPosition(new Vector3f(-4f, 3f, 0f));
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

        for (int i = 0; i < 5; i++) {
            Vertex v = new Vertex();
            v.setPosition(new Vector3f(0f,0f,0f));
            v.setLabel("Noeud "+(i+4));
            g.addVertex(v);
        }
        DispoRandomAlgorithm algorithm = new DispoRandomAlgorithm();

        for (Pair<Vertex, Vector3f> p : algorithm.execute(g)){
            p.getValue0().setPosition(p.getValue1());
        }
        return g;
    }
}
