package controller;

import model.*;
import model.algorithm.*;
import view.GraphCanvas;
import view.GraphView;
import view.GraphWindow;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Clement on 16/03/2015.
 */
public class Controller {
    // Algorithms
    private Map<String, IOAlgorithm> ioAlgorithms;
    private Collection<IDispoAlgorithm> dispoAlgorithms;
    private Collection<IColorAlgorithm> colorAlgorithms;
    private Collection<ISizeAlgorithm> sizeAlgorithms;

    // Model
    private List<Graph> graphs;
    private Map<Graph, GraphView> graphViews;
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
        this.graphViews = new HashMap<Graph, GraphView>();
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
        GraphView graphView = new GraphView();
        graphView.setGraph(graph);
        graphView.setController(this);
        graph.addObserver(graphView);
        this.canvas.setGraphView(graphView);
        this.graphs.add(graph);
        this.graphViews.put(graph, graphView);

        if (this.window != null) {
            this.window.addGraph(graph.getName());
        }
    }
    public void removeGraphe(Graph graph){
        graphs.remove(graph);
    }

    public void selectGraph(int graphID) {
        this.setCurrentGraph(this.graphs.get(graphID));
    }

    public Graph getCurrentGraph() {
        return this.currentGraph;
    }

    public void setCurrentGraph(Graph graph) {
        if (!this.graphs.contains(graph)) {
            this.addGraph(graph);
        }
        this.currentGraph = graph;
        this.canvas.setGraphView(graphViews.get(graph));
        this.getCanvas().setBackgroundColor(graph.getBackgroundColor());
        this.getCanvas().setBackground(graph.getBackgroundColor());
    }
    public void setCurrentGraphBackgroundColor(Color color){
        this.currentGraph.setBackgroundColor(color);
        this.getCanvas().setBackgroundColor(currentGraph.getBackgroundColor());
        this.getCanvas().setBackground(currentGraph.getBackgroundColor());
    }

    private void setupIOAlgorithm() {
        this.ioAlgorithms = new HashMap<String, IOAlgorithm>();
        this.ioAlgorithms.put("Dot", new IOAlgorithmDot());
        this.ioAlgorithms.put("GraphML", new IOAlgorithmGraphml());
    }

    private void setupDispoAlgorithm() {
        this.dispoAlgorithms = new ArrayList<IDispoAlgorithm>();
        this.dispoAlgorithms.add(new DispoRandomAlgorithm());
        this.dispoAlgorithms.add(new DispoCircleAlgorithm());
    }

    private void setupColorAlgorithms() {
        this.colorAlgorithms = new ArrayList<IColorAlgorithm>();
        this.colorAlgorithms.add(new ColorWithEdgesAlgorithm());
    }

    private void setupSizeAlgorithms() {
        this.sizeAlgorithms = new ArrayList<ISizeAlgorithm>();
        this.sizeAlgorithms.add(new SizeWithEdgesAlgorithm());
    }

    public ControllerState getState() {
        return state;
    }

    public void setState(ControllerState state) {
        this.state = state;
    }

    public GraphCanvas getCanvas() {
        return canvas;
    }


    public Map<String, IOAlgorithm> getIoAlgorithms() {
        return ioAlgorithms;
    }

    public GraphWindow getWindow() {
        return window;
    }
}
