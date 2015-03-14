package modelTest;

import model.EdgeStyle;
import model.StyleManager;
import model.VertexShape;
import model.VertexStyle;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class StyleManagerTest {

    @Test
    public void StyleManagerTest(){
        StyleManager styleManager = new StyleManager();

        Assert.assertEquals(styleManager.getDefaultEdgeStyle().getColor(), Color.BLACK);
        Assert.assertEquals(styleManager.getDefaultEdgeStyle().getTextColor(), Color.BLACK);
        Assert.assertEquals(styleManager.getDefaultEdgeStyle().getThickness(), 2.0, 0.0);

        Assert.assertEquals(styleManager.getDefaultVertexStyle().getBackgroundColor(), Color.GRAY);
        Assert.assertEquals(styleManager.getDefaultVertexStyle().getTextColor(), Color.BLACK);
        Assert.assertEquals(styleManager.getDefaultVertexStyle().getBorderColor(), Color.BLACK);
        Assert.assertEquals(styleManager.getDefaultVertexStyle().getBorderThickness(), 1.0, 0.0);
        Assert.assertEquals(styleManager.getDefaultVertexStyle().getSize(), 4.0, 0.0);
        Assert.assertEquals(styleManager.getDefaultVertexStyle().getShape(), VertexShape.SQUARE);
    }

    @Test
    public void addStyleTest(){
        StyleManager styleManager = new StyleManager();

        Assert.assertTrue(styleManager.getEdgeStyles().size() == 0);
        Assert.assertTrue(styleManager.getVertexStyles().size() == 0);

        VertexStyle vertexStyle = new VertexStyle();
        EdgeStyle edgeStyle = new EdgeStyle();

        vertexStyle.incrementUsageCount();
        edgeStyle.incrementUsageCount();

        styleManager.addStyle(vertexStyle);
        Assert.assertTrue(styleManager.getVertexStyles().size() == 1);
        Assert.assertTrue(styleManager.getVertexStyles().contains(vertexStyle));

        styleManager.addStyle(edgeStyle);
        Assert.assertTrue(styleManager.getEdgeStyles().size() == 1);
        Assert.assertTrue(styleManager.getEdgeStyles().contains(edgeStyle));

    }

    @Test
    public void removeStyleTest(){
        StyleManager styleManager = new StyleManager();

        Assert.assertTrue(styleManager.getEdgeStyles().size() == 0);
        Assert.assertTrue(styleManager.getVertexStyles().size() == 0);

        VertexStyle vertexStyle = new VertexStyle();
        EdgeStyle edgeStyle = new EdgeStyle();

        vertexStyle.incrementUsageCount();
        edgeStyle.incrementUsageCount();

        styleManager.addStyle(vertexStyle);
        styleManager.addStyle(edgeStyle);



        styleManager.removeStyle(vertexStyle);
        Assert.assertTrue(styleManager.getVertexStyles().size() == 0);
        Assert.assertFalse(styleManager.getVertexStyles().contains(vertexStyle));

        styleManager.removeStyle(edgeStyle);
        Assert.assertTrue(styleManager.getEdgeStyles().size() == 0);
        Assert.assertFalse(styleManager.getEdgeStyles().contains(edgeStyle));

    }

    @Test
    public void updateTest()
    {
        StyleManager styleManager = new StyleManager();

        Assert.assertTrue(styleManager.getEdgeStyles().size() == 0);
        Assert.assertTrue(styleManager.getVertexStyles().size() == 0);

        VertexStyle vertexStyle = new VertexStyle();
        EdgeStyle edgeStyle = new EdgeStyle();

        vertexStyle.incrementUsageCount();
        edgeStyle.incrementUsageCount();

        styleManager.addStyle(vertexStyle);
        styleManager.addStyle(edgeStyle);

        Assert.assertTrue(styleManager.getVertexStyles().size() == 1);
        Assert.assertTrue(styleManager.getEdgeStyles().size() == 1);

        vertexStyle.setUsageCount(0);
        edgeStyle.setUsageCount(0);

        styleManager.update();

        Assert.assertTrue(styleManager.getVertexStyles().size() == 0);
        Assert.assertTrue(styleManager.getEdgeStyles().size() == 0);
    }
}
