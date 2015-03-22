package controller;

import model.*;
import org.lwjgl.util.vector.Vector3f;
import utils.ColorUtils;
import utils.FontUtils;
import utils.LineStyleUtils;
import utils.ShapeUtils;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class IOAlgorithmDot implements IOAlgorithm {

    private HashMap<String, Vertex> vertexMapOpen = null;

	@Override
	public Graph open(String filename) {
        vertexMapOpen = new HashMap<String, Vertex>();
		Graph graph = new Graph();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                System.out.println(line);
                parseDotLine(line, graph);
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return graph;
	}

    private void parseDotLine(String line, Graph graph){
        StringTokenizer tokenizer = new StringTokenizer(line, "[");
        String token = "";
        String nodeToken = null;
        String edgeToken = null;
        while (tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            if(nodeToken != null){
                System.out.println("Vertex style line");
                Vertex vertex = new Vertex(graph);
                VertexStyle vertexStyle = new VertexStyle();
                Vector3f vertexPos = new Vector3f(0,0,0);
                vertexMapOpen.put(nodeToken, vertex);
                String[] styleValues = token.split(";");
                for(String s : styleValues){
                    if(s.split("=").length == 2){
                        String value = s.split("=")[1].replace("]", "").replace(" ","");
                        String key = s.split("=")[0].replace(" ","");
                        if (key.contains("shape")){
                            vertexStyle.setShape(ShapeUtils.stringToShape(value));
                        }
                        if (key.contains( "font")){
                            vertexStyle.setFont(FontUtils.stringToFont(value));
                        }
                        if (key.contains("size")){
                            vertexStyle.setSize(Float.parseFloat(value));
                        }
                        if (key.contains("border-thickness")){
                            vertexStyle.setBorderThickness(Float.parseFloat(value));

                        }
                        if (key.contains("background-color")){
                            vertexStyle.setBackgroundColor(ColorUtils.RGBStringToColor(value));
                        }
                        if (key.contains("text-color")){
                            vertexStyle.setTextColor(ColorUtils.RGBStringToColor(value));
                        }
                        if (key.contains("border-color")){
                            vertexStyle.setBorderColor(ColorUtils.RGBStringToColor(value));
                        }
                        if (key.contains("label")){
                            vertex.setLabel(value);
                        }
                        if (key.contains("pos_x")){
                            vertexPos.setX(Float.parseFloat(value));
                        }
                        if (key.contains("pos_y")){
                            vertexPos.setY(Float.parseFloat(value));
                        }
                        if (key.contains("pos_z")) {
                            vertexPos.setZ(Float.parseFloat(value));
                        }
                    }
                }
                if (vertexStyle.isEquals(graph.getStyleManager().getDefaultVertexStyle())){
                    vertex.setStyle(graph.getStyleManager().getDefaultVertexStyle());
                } else {
                    vertex.setStyle(vertexStyle);
                    graph.getStyleManager().addStyle(vertexStyle);
                }
                vertex.setPosition(vertexPos);
                graph.addVertex(vertex);
            } else if (edgeToken != null){
                System.out.println("Edge Style line");
                Edge edge = new Edge(graph);
                edge.setSrcVertex(vertexMapOpen.get(edgeToken.split(" .. ")[0]));
                vertexMapOpen.get(edgeToken.split(" .. ")[0]).addEdge(edge);
                edge.setDstVertex(vertexMapOpen.get(edgeToken.split(" .. ")[1]));
                vertexMapOpen.get(edgeToken.split(" .. ")[1]).addEdge(edge);
                EdgeStyle edgeStyle = new EdgeStyle();
                String[] styleValues = token.split(";");
                for(String s : styleValues){
                    if(s.split("=").length == 2){
                        String value = s.split("=")[1].replace("]", "").replace(" ","");
                        String key = s.split("=")[0].replace(" ","");
                        if (key.contains( "font")){
                            edgeStyle.setFont(FontUtils.stringToFont(value));
                        }
                        if (key.contains("thickness")){
                            edgeStyle.setThickness(Float.parseFloat(value));
                        }
                        if (key.contains("color")){
                            edgeStyle.setColor(ColorUtils.RGBStringToColor(value));
                        }
                        if (key.contains("text-color")){
                            edgeStyle.setTextColor(ColorUtils.RGBStringToColor(value));
                        }
                        if (key.contains("label")){
                            edge.setLabel(value);
                        }
                        if (key.contains("style")){
                            edgeStyle.setStyle(LineStyleUtils.stringToLineStyle(value));
                        }
                    }
                }
                if (edgeStyle.isEquals(graph.getStyleManager().getDefaultEdgeStyle())){
                    edge.setStyle(graph.getStyleManager().getDefaultEdgeStyle());
                } else {
                    edge.setStyle(edgeStyle);
                    graph.getStyleManager().addStyle(edgeStyle);
                }
                graph.addEdge(edge);
            } else if (token.matches("^[a-zA-Z0-9]*$")){
                System.out.println("Node ?");
                nodeToken = token;
                System.out.println(vertexMapOpen.size());
                edgeToken = null;
            } else if (token.matches("^[a-zA-Z0-9]* -- [a-zA-Z0-9]*$") || token.matches("[a-zA-Z0-9] -> [a-zA-Z0-9]")){
                System.out.println("Edge ?");
                edgeToken = token;
                nodeToken = null;
            } else if (token.matches("^[a-z]* [a-zA-Z0-9]* .*")){
                if (token.split(" ").length >= 2){
                    if (token.split(" ")[0].contains("digraph")){
                        graph.setOriented(true);
                    }else {
                        graph.setOriented(false);
                    }
                    graph.setName(token.split(" ")[1]);
                }
                System.out.println("Graph name : "+ token);
            }
        }
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
		return true;
	}

}
