package model;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Arnaud on 09/03/2015.
 */
public class StyleManager {

    private List<EdgeStyle> edgeStyles;
    private List<VertexStyle> vertexStyles;

    private EdgeStyle defaultEdgeStyle;
    private VertexStyle defaultVertexStyle;

    public StyleManager()
    {
        edgeStyles = new CopyOnWriteArrayList<EdgeStyle>();
        vertexStyles = new CopyOnWriteArrayList<VertexStyle>();

        defaultEdgeStyle = new EdgeStyle().setColor(Color.BLACK)
                                          .setTextColor(Color.BLACK)
                                          .setThickness(2)
                                          .setFont(new Font("Verdana", Font.PLAIN, 4));

        defaultVertexStyle = new VertexStyle().setBackgroundColor(Color.GRAY)
                                              .setBorderColor(Color.BLACK)
                                              .setBorderThickness(1)
                                              .setSize(2)
                                              .setTextColor(Color.BLACK)
                                              .setShape(VertexShape.SQUARE)
                                              .setFont(new Font("Verdana", Font.PLAIN, 4));
    }

    public VertexStyle getDefaultVertexStyle() {
        return defaultVertexStyle;
    }

    public void setDefaultVertexStyle(VertexStyle defaultVertexStyle) {
        this.defaultVertexStyle = defaultVertexStyle;
    }

    public EdgeStyle getDefaultEdgeStyle() {
        return defaultEdgeStyle;
    }

    public void setDefaultEdgeStyle(EdgeStyle defaultEdgeStyle) {
        this.defaultEdgeStyle = defaultEdgeStyle;
    }

    public void removeStyle(IStyle style){
        if (style instanceof EdgeStyle){
            if (!edgeStyles.isEmpty() && edgeStyles.contains(style)){
                edgeStyles.remove(style);
            }
        } else if (style instanceof VertexStyle){
            if (!vertexStyles.isEmpty() && vertexStyles.contains(style)){
                vertexStyles.remove(style);
            }
        }
    }

    public void addStyle(IStyle style){
        if (style instanceof EdgeStyle){
            edgeStyles.add((EdgeStyle)style);
        } else if (style instanceof VertexStyle){
            vertexStyles.add((VertexStyle)style);
        }
    }

    public List<EdgeStyle> getEdgeStyles() {
        return edgeStyles;
    }

    public void setEdgeStyles(List<EdgeStyle> edgeStyles) {
        this.edgeStyles = edgeStyles;
    }

    public List<VertexStyle> getVertexStyles() {
        return vertexStyles;
    }

    public void setVertices(List<VertexStyle> vertexStyles) {
        this.vertexStyles = vertexStyles;
    }

    public void update(){
        for (EdgeStyle e : edgeStyles){
            if (e.getUsageCount() <= 0){
                edgeStyles.remove(e);
            }
        }
        for (VertexStyle v : vertexStyles){
            if (v.getUsageCount() <= 0){
                vertexStyles.remove(v);
            }
        }
    }
}
