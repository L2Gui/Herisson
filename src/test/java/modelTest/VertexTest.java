package modelTest;

import model.Graph;
import model.Vertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class VertexTest {

    @Test
    public void VertexTest(){
        Vertex vertex = new Vertex();

        Assert.assertEquals(vertex.getLabel(), "");
        Assert.assertNotNull(vertex.getEdges());
        Assert.assertTrue(vertex.getEdges().isEmpty());
        Assert.assertNull(vertex.getStyle());
        Assert.assertNull(vertex.getGraph());
        Assert.assertNull(vertex.getPosition());
    }

    @Test
    public void VertexWithOneArgTest(){
        Graph graph = new Graph();
        Vertex vertex = new Vertex(graph);

        Assert.assertEquals(vertex.getLabel(), "");
        Assert.assertNotNull(vertex.getEdges());
        Assert.assertTrue(vertex.getEdges().isEmpty());
        Assert.assertNull(vertex.getPosition());

        Assert.assertEquals(graph, vertex.getGraph());
        Assert.assertEquals(graph.getStyleManager().getDefaultVertexStyle(), vertex.getStyle());
    }
}
