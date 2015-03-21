package controller;

import model.*;
import org.javatuples.Pair;
import org.lwjgl.util.vector.Vector3f;
import view.GraphCanvas;
import view.GraphView;
import view.GraphWindow;

import java.util.*;

/**
 * Created by Clement on 16/03/2015.
 */
public class Controller {
    // Algorithms
    private Map<String, IOAlgorithm> ioAlgorithms;
    private Map<String, IDispoAlgorithm> dispoAlgorithms;
    private Map<String, IColorAlgorithm> colorAlgorithms;
    private Map<String, ISizeAlgorithm> sizeAlgorithms;

    // Model
    private List<Graph> graphs;
    private Graph currentGraph;

    // View
    private GraphWindow window;
    private GraphCanvas canvas;
    private ControllerState state;

    public Controller() {
        this.setupIOAlgorithm();
        this.setupDispoAlgorithm();
        this.setupColorAlgorithms();
        this.setupSizeAlgorithms();

        this.graphs = new ArrayList<Graph>();
        this.state = ControllerState.VERTEX_CREATION;
    }

    public void executeCommand(ICommand command) {
        if (this.currentGraph != null) {
            this.currentGraph.getCommandHandler().executeCommand(command);
        }
    }

    public void setGraphWindow(GraphWindow window) {
        this.window = window;
        this.canvas = window.getCanvas();
        this.canvas.setController(this);
        this.window.setController(this);

        for (Graph graph : this.graphs) {
            this.window.addGraph(graph.getName());
        }
        this.window.setAlgorithms(this.dispoAlgorithms, this.colorAlgorithms, this.sizeAlgorithms);
    }

    public void addGraph(Graph graph) {
        this.graphs.add(graph);
    }

    public void selectGraph(int graphID) {
        this.setCurrentGraph(this.graphs.get(graphID));
    }

    public Graph getCurrentGraph() {
        return this.currentGraph;
    }

    public void setCurrentGraph(Graph graph) {
        if (!this.graphs.contains(graph)) {
            this.graphs.add(graph);
        }
        this.currentGraph = graph;
    }

    private void setupIOAlgorithm() {
        this.ioAlgorithms = new HashMap<String, IOAlgorithm>();
        this.ioAlgorithms.put("Dot", new IOAlgorithmDot());
        this.ioAlgorithms.put("GraphML", new IOAlgorithmGraphml());
    }

    private void setupDispoAlgorithm() {
        this.dispoAlgorithms = new HashMap<String, IDispoAlgorithm>();
        this.dispoAlgorithms.put("Disposition aléatoire", new DispoRandomAlgorithm());
        this.dispoAlgorithms.put("Disposition en cercle", new DispoCircleAlgorithm());
    }

    private void setupColorAlgorithms() {
        this.colorAlgorithms = new HashMap<String, IColorAlgorithm>();
    }

    private void setupSizeAlgorithms() {
        this.sizeAlgorithms = new HashMap<String, ISizeAlgorithm>();
    }

    public ControllerState getState() {
        return state;
    }

    public void setState(ControllerState state) {
        this.state = state;
    }

    public GraphView createSampleGraph() {
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

        this.graphs.add(g);
        GraphView view = new GraphView();
        view.setController(this);
        g.addObserver(view);
        return view;
    }
}
