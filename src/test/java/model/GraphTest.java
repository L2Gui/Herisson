package model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class GraphTest {

    @Test
    public void Graph_Test(){
        Graph graph = new Graph();

        Assert.assertEquals(graph.getName(), "");
        Assert.assertEquals(graph.getFilename(), "");
        Assert.assertFalse(graph.isOriented());
        Assert.assertNotNull(graph.getStyleManager());
        Assert.assertNotNull(graph.getEdges());
        Assert.assertTrue(graph.getEdges().isEmpty());
        Assert.assertNotNull(graph.getVertices());
        Assert.assertTrue(graph.getVertices().isEmpty());
    }

    @Test
    public void addEdge_Test(){
        Graph graph = new Graph();

        Edge edge = new Edge();

        graph.addEdge(edge);
        Assert.assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void removeEdge_Test(){
        Graph graph = new Graph();

        Edge edge = new Edge();

        graph.addEdge(edge);
        Assert.assertTrue(graph.getEdges().contains(edge));

        graph.removeEdge(edge);
        Assert.assertFalse(graph.getEdges().contains(edge));

    }

    @Test
    public void addVertex_Test(){
        Graph graph = new Graph();

        Vertex vertex = new Vertex();

        graph.addVertex(vertex);
        Assert.assertTrue(graph.getVertices().contains(vertex));
    }

    @Test
    public void removeVertex_Test(){
        Graph graph = new Graph();

        Vertex vertex = new Vertex();

        graph.addVertex(vertex);
        Assert.assertTrue(graph.getVertices().contains(vertex));

        graph.removeVertex(vertex);
        Assert.assertFalse(graph.getVertices().contains(vertex));
    }
}
