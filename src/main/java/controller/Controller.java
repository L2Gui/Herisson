package controller;

import model.*;
import view.GraphCanvas;
import view.GraphWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    private Collection<Graph> graphs;
    private Graph currentGraph;

    // View
    private GraphWindow window;
    private GraphCanvas canvas;
    private State state;

    public Controller() {
        this.setupIOAlgorithm();
        this.setupDispoAlgorithm();
        this.setupColorAlgorithms();
        this.setupSizeAlgorithms();

        this.graphs = new ArrayList<Graph>();
        this.state=State.VERTEX_CREATION;
    }

    public void executeCommand(ICommand command) {
        if (this.currentGraph != null) {
            this.currentGraph.getCommandHandler().executeCommand(command);
        }
    }

    public void setGraphWindow(GraphWindow window) {
        this.window = window;
        this.canvas = window.getCanvas();

        for (Graph graph : this.graphs) {
            this.window.addGraph(graph);
        }
        this.window.setAlgorithms(this.dispoAlgorithms, this.colorAlgorithms, this.sizeAlgorithms);
        this.window.setController(this);
    }

    public void addGraph(Graph graph) {
        this.graphs.add(graph);
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
