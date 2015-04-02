package controllerTest;

import controller.IOAlgorithmGraphml;
import model.Graph;
import model.Vertex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Arnaud on 16/03/2015.
 */
public class IOAlgorithmGraphmlTest {

    @Test
    public void openTest()
    {
        IOAlgorithmGraphml io = new IOAlgorithmGraphml();
        Graph graph = new Graph();
        /*try {
            graph = io.open("test.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(graph.getEdges().size(), 1);
        Assert.assertEquals(graph.getVertices().size(), 2);
        Assert.assertEquals(graph.getVertices().get(0).getLabel(), "Vertex 1");
        Assert.assertEquals(graph.getVertices().get(0).getStyle(), graph.getStyleManager().getDefaultVertexStyle());*/
    }
}
