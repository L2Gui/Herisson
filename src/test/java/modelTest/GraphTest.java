package modelTest;

import model.Edge;
import model.Graph;
import model.Vertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class GraphTest {

    @Test
    public void GraphTest(){
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
    public void addEdgeTest(){
        Graph graph = new Graph();

        Edge edge = new Edge();

        graph.addEdge(edge);
        Assert.assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void removeEdgeTest(){
        Graph graph = new Graph();

        Edge edge = new Edge();

        graph.addEdge(edge);
        Assert.assertTrue(graph.getEdges().contains(edge));

        graph.removeEdge(edge);
        Assert.assertFalse(graph.getEdges().contains(edge));

    }

    @Test
    public void addVertexTest(){
        Graph graph = new Graph();

        Vertex vertex = new Vertex();

        graph.addVertex(vertex);
        Assert.assertTrue(graph.getVertices().contains(vertex));
    }

    @Test
    public void removeVertexTest(){
        Graph graph = new Graph();

        Vertex vertex = new Vertex();

        graph.addVertex(vertex);
        Assert.assertTrue(graph.getVertices().contains(vertex));

        graph.removeVertex(vertex);
        Assert.assertFalse(graph.getVertices().contains(vertex));
    }
}
