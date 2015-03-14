package model;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Arnaud on 14/03/2015.
 */
public class EdgeStyleTest {

    @Test
    public void EdgeStyle_Test(){
        EdgeStyle edgeStyle = new EdgeStyle();

        Assert.assertEquals(edgeStyle.getColor(), Color.BLACK);
        Assert.assertEquals(edgeStyle.getTextColor(), Color.BLACK);
        Assert.assertEquals(edgeStyle.getThickness(), 1.0, 0.0);
        Assert.assertEquals(edgeStyle.getStyle(), LineStyle.SOLID);
        Assert.assertEquals(edgeStyle.getUsageCount(), 0);
        Assert.assertNull(edgeStyle.getFont());
    }

    @Test
    public void EdgeStyleWithOneArg_Test(){
        EdgeStyle model = new EdgeStyle().setColor(Color.RED)
                                            .setTextColor(Color.GREEN)
                                            .setThickness(4)
                                            .setStyle(LineStyle.DOTTED)
                                            .setUsageCount(5);

        EdgeStyle edgeStyle = new EdgeStyle(model);


        Assert.assertEquals(edgeStyle.getColor(), Color.RED);
        Assert.assertEquals(edgeStyle.getTextColor(), Color.GREEN);
        Assert.assertEquals(edgeStyle.getThickness(), 4.0, 0.0);
        Assert.assertEquals(edgeStyle.getStyle(), LineStyle.DOTTED);
        Assert.assertEquals(edgeStyle.getUsageCount(), 0);
        Assert.assertNull(edgeStyle.getFont());
    }
}
