package controller;

import model.*;
import utils.ColorUtils;
import utils.FontUtils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class IOAlgorithmDot implements IOAlgorithm {

	@Override
	public Graph open(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(String filename, Graph graph) {

        HashMap<Vertex, Integer> nodeToId = new HashMap<Vertex, Integer>();

        if (!filename.endsWith(".dot")){
            filename += ".dot";
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String link_symbol = " -- ";

        if (graph.isOriented()){
            link_symbol = " -> ";
            writer.print("digraph");
        } else {
            writer.print("graph");
        }

        writer.println(" "+graph.getName()+ " {\n");

        int vertexCount = 0;
        for(Vertex vertex : graph.getVertices()){
            nodeToId.put(vertex, vertexCount);

            writer.print(constructDotLineFromVertex(vertex, vertexCount)+"\n");
            vertexCount++;
        }
        //writer.println("The first line");
        //writer.println("The second line");

        for(Edge edge : graph.getEdges()){
            writer.print("n"+nodeToId.get(edge.getSrcVertex()) + link_symbol + "n"+nodeToId.get(edge.getDstVertex()) + "[");

            if(edge.getLabel() != null && !edge.getLabel().isEmpty()){
                writer.print("; label=" + edge.getLabel());
            }

            if (edge.getStyle() != null){
                EdgeStyle edgeStyle = edge.getStyle();
                if(edgeStyle != null){
                    writer.print("; font="+ FontUtils.fontToString(edgeStyle.getFont()));
                    writer.print("; thickness="+ edgeStyle.getThickness());
                    writer.print("; text-color="+ ColorUtils.colorToString(edgeStyle.getTextColor()));
                    writer.print("; color="+ ColorUtils.colorToString(edgeStyle.getColor()));
                    writer.print("; style="+ edgeStyle.getStyle().toString());
                }
            }

            writer.println("]\n");
        }

        writer.println("}");
        writer.close();
		
	}

    private String constructDotLineFromVertex(Vertex vertex, int vertexCount){
        String line = "n" + vertexCount + "[pos_x=" + vertex.getPosition().getX() + "; pos_y=" + vertex.getPosition().getY() + "; pos_z=" + vertex.getPosition().getZ();

        if(vertex.getLabel() != null && !vertex.getLabel().isEmpty()){
            line += "; label=" + vertex.getLabel();
        }

        VertexStyle vertexStyle = vertex.getStyle();
        if (vertexStyle != null){
            line += "; background-color="+ ColorUtils.colorToString(vertexStyle.getBackgroundColor());
            line += "; border-color="+ ColorUtils.colorToString(vertexStyle.getBorderColor());
            line += "; text-color="+ ColorUtils.colorToString(vertexStyle.getTextColor());
            line += "; font="+ FontUtils.fontToString(vertexStyle.getFont());
            line += "; size="+ vertexStyle.getSize();
            line += "; border-thickness="+ vertexStyle.getBorderThickness();
            line += "; shape="+ vertexStyle.getShape().toString();
        }

        line += "]";
        return line;
    }


	@Override
	public boolean isConform(String filname) {
		// TODO Auto-generated method stub
		return false;
	}

}
