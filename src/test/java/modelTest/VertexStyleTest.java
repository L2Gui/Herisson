package modelTest;

import model.VertexShape;
import model.VertexStyle;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class VertexStyleTest {
    @Test
    public void VertexStyleTest(){
        VertexStyle vertexStyle = new VertexStyle();

        Assert.assertEquals(vertexStyle.getBackgroundColor(), Color.GRAY);
        Assert.assertEquals(vertexStyle.getTextColor(), Color.BLACK);
        Assert.assertEquals(vertexStyle.getBorderColor(), Color.BLACK);
        Assert.assertEquals(vertexStyle.getBorderThickness(), 1.0, 0.0);
        Assert.assertEquals(vertexStyle.getSize(), 4.0, 0.0);
        Assert.assertEquals(vertexStyle.getShape(), VertexShape.SQUARE);
        Assert.assertNull(vertexStyle.getFont());
    }

    @Test
    public void VertexStyleWithOneArgTest(){
        VertexStyle model = new VertexStyle().setUsageCount(5)
                                            .setBackgroundColor(Color.GREEN)
                                            .setBorderColor(Color.YELLOW)
                                            .setBorderThickness(4)
                                            .setShape(VertexShape.CIRCLE)
                                            .setSize(8)
                                            .setTextColor(Color.BLUE);

        VertexStyle vertexStyle = new VertexStyle(model);

        Assert.assertEquals(vertexStyle.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(vertexStyle.getTextColor(), Color.BLUE);
        Assert.assertEquals(vertexStyle.getBorderColor(), Color.YELLOW);
        Assert.assertEquals(vertexStyle.getBorderThickness(), 4.0, 0.0);
        Assert.assertEquals(vertexStyle.getSize(), 8.0, 0.0);
        Assert.assertEquals(vertexStyle.getShape(), VertexShape.CIRCLE);
        Assert.assertNull(vertexStyle.getFont());

    }
}
