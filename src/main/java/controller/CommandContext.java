package controller;

import model.EdgeStyle;
import model.Graph;
import model.VertexStyle;
import model.IVisuAlgorythm;

import java.util.Collection;

/**
 * Created by Clement on 13/03/2015.
 */
public class CommandContext {
    private Collection<IOAlgorithm> ioAlgorithms;
    private Collection<IVisuAlgorythm> visuAlgorythms;
    private Collection<VertexStyle> vertexStyles;
    private Collection<EdgeStyle> edgesStyles;
    private Graph currentGraph;
    private VertexStyle currentVertexStyle;
    private EdgeStyle currentEdgeStyle;

    public Collection<IOAlgorithm> getIoAlgorithms() {
        return ioAlgorithms;
    }

    public void setIoAlgorithms(Collection<IOAlgorithm> ioAlgorithms) {
        this.ioAlgorithms = ioAlgorithms;
    }

    public Collection<IVisuAlgorythm> getVisuAlgorythms() {
        return visuAlgorythms;
    }

    public void setVisuAlgorythms(Collection<IVisuAlgorythm> visuAlgorythms) {
        this.visuAlgorythms = visuAlgorythms;
    }

    public Graph getCurrentGraph() {
        return currentGraph;
    }

    public void setCurrentGraph(Graph currentGraph) {
        this.currentGraph = currentGraph;
    }

    public Collection<VertexStyle> getVertexStyles() {
        return vertexStyles;
    }

    public void setVertexStyles(Collection<VertexStyle> vertexStyles) {
        this.vertexStyles = vertexStyles;
    }

    public Collection<EdgeStyle> getEdgesStyles() {
        return edgesStyles;
    }

    public void setEdgesStyles(Collection<EdgeStyle> edgesStyles) {
        this.edgesStyles = edgesStyles;
    }

    public VertexStyle getCurrentVertexStyle() {
        return currentVertexStyle;
    }

    public void setCurrentVertexStyle(VertexStyle currentVertexStyle) {
        this.currentVertexStyle = currentVertexStyle;
    }

    public EdgeStyle getCurrentEdgeStyle() {
        return currentEdgeStyle;
    }

    public void setCurrentEdgeStyle(EdgeStyle currentEdgeStyle) {
        this.currentEdgeStyle = currentEdgeStyle;
    }
}
