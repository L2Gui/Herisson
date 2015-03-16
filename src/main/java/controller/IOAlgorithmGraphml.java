package controller;

import model.*;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import utils.ColorUtils;
import utils.FontUtils;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class IOAlgorithmGraphml implements IOAlgorithm {

	@Override
	public Graph open(String filename) throws Exception {

		Graph graph = new Graph();
		
		SAXBuilder sxb = new SAXBuilder();
		
		org.jdom2.Document document = sxb.build(new File(filename));
		
		Element graphRacine;
		List<Element> keyRacine;
		HashMap<String, String> nodeKeyMap = new HashMap<String, String>();
		HashMap<String, String> edgeKeyMap = new HashMap<String, String>();
		
		graphRacine = document.getRootElement().getChild("graph");
		keyRacine = document.getRootElement().getChildren("key");
		
		HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
		HashMap<String, Edge> edges = new HashMap<String, Edge>();
		
		for (Element key : keyRacine){
			if (key.getAttribute("for").getValue() == "node"){
				nodeKeyMap.put(key.getAttributeValue("id"), key.getAttributeValue("attr.name"));
			}
			else if (key.getAttribute("for").getValue() == "edge"){
				edgeKeyMap.put(key.getAttributeValue("id"), key.getAttributeValue("attr.name"));
			}
		}
		
		for (Element node : graphRacine.getChildren("node")){
			String id = node.getAttributeValue("id");
			Vertex vertex = new Vertex();
			/*
			for (Element data : node.getChildren("data")){
				switch (nodeKeyMap.get(data.getAttributeValue("id"))){
					case "color":
						vertex.setBackgroundColor(ColorUtils.convertColor(data.getTextNormalize(), Color.black));
						break;
					case "border-thickness":
						vertex.setThickness(Float.parseFloat(data.getTextNormalize()));
						break;
					case "text-color":
						vertex.setTextColor(ColorUtils.convertColor(data.getTextNormalize(), Color.black));
						break;
					case "border-color":
						vertex.setBorderColor(ColorUtils.convertColor(data.getTextNormalize(), Color.black));
						break;
					case "shape":
						vertex.setShape(ShapeUtils.stringToShape(data.getTextNormalize()));
						break;
					case "label":
						vertex.setLabel(data.getTextNormalize());
						break;
				}
			}*/
			vertices.put(id, vertex);
		}
		
		for (Element edg : graphRacine.getChildren("node")){
			String id = edg.getAttributeValue("id");
			Edge edge = new Edge();
			
			String src = edg.getAttributeValue("source");
			String dst = edg.getAttributeValue("target");
			
			
			/*
			for (Element data : edg.getChildren("data")){
				switch (nodeKeyMap.get(data.getAttributeValue("id"))){
					case "color":
						edge.setColor(ColorUtils.convertColor(data.getTextNormalize(), Color.black));
						break;
					case "thickness":
						edge.setThickness(Float.parseFloat(data.getTextNormalize()));
						break;
					case "text-color":
						edge.setTextColor(ColorUtils.convertColor(data.getTextNormalize(), Color.black));
						break;
					case "line-style":
						edge.getStyle().setStyle(LineStyleUtils.stringToLineStyle(data.getTextNormalize()));
						break;
					case "label":
						edge.setLabel(data.getTextNormalize());
						break;
				}
			}*/
			
			if (src != null){
				edge.setSrcVertex(vertices.get(src));
				vertices.get(src).addEdge(edge);
			}
			if (dst != null){
				edge.setDstVertex(vertices.get(dst));
				vertices.get(src).addEdge(edge);
			}
			
			edges.put(id, edge);
		}
		
		for (Entry<String, Edge> e : edges.entrySet())
		{
			graph.addEdge(e.getValue());
		}
		for (Entry<String, Vertex> e : vertices.entrySet())
		{
			graph.addVertex(e.getValue());
		}
		
		return graph;
	}

	@Override
	public void save(String filename, Graph graph) {
		// TODO Auto-generated method stub
        Element graphml = new Element("graphml", "http://graphml.graphdrawing.org/xmlns");


        Namespace XSI = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        graphml.addNamespaceDeclaration(XSI);
        graphml.setAttribute("schemaLocation", "http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd", XSI);


        org.jdom2.Document gDoc = new org.jdom2.Document(graphml);

        HashMap<VertexStyle, String> vertexStylesMap = new HashMap<VertexStyle, String>();
        HashMap<EdgeStyle, String> edgeStylesMap = new HashMap<EdgeStyle, String>();

        int nodeStyleCount = 0;
        Element defaultNodeStyle = constructNodeStyleKey(null, graph.getStyleManager().getDefaultVertexStyle(), nodeStyleCount);
        graphml.addContent(defaultNodeStyle);
        vertexStylesMap.put(graph.getStyleManager().getDefaultVertexStyle(), defaultNodeStyle.getAttributeValue("id"));
        nodeStyleCount++;
        for(VertexStyle style : graph.getStyleManager().getVertexStyles()){
            Element nodeStyle = constructNodeStyleKey(null, style, nodeStyleCount);
            graphml.addContent(nodeStyle);
            vertexStylesMap.put(style, nodeStyle.getAttributeValue("id"));
            nodeStyleCount++;
        }

        int edgeStyleCount = 0;
        Element defaultEdgeStyle = constructEdgeStyleKey(null, graph.getStyleManager().getDefaultEdgeStyle(), edgeStyleCount);
        graphml.addContent(defaultEdgeStyle);
        edgeStylesMap.put(graph.getStyleManager().getDefaultEdgeStyle(), defaultEdgeStyle.getAttributeValue("id"));
        edgeStyleCount++;
        for(EdgeStyle style : graph.getStyleManager().getEdgeStyles()){
            Element nodeStyle = constructEdgeStyleKey(null, style, nodeStyleCount);
            graphml.addContent(nodeStyle);
            edgeStylesMap.put(style, nodeStyle.getAttributeValue("id"));
            edgeStyleCount++;
        }

        Element graphxml;
        if (graph.getName() != "" && graph.getName() != null) {
            graphxml = new Element("graph").setAttribute("id", graph.getName());
        }
        else {
            graphxml = new Element("graph").setAttribute("id", "g");
        }

        if (graph.isOriented()) {
            graphxml.setAttribute("edgedefault", "directed");
        }
        else{
            graphxml.setAttribute("edgedefault", "undirected");
        }


        HashMap<Vertex, String> vertexKeysMap = new HashMap<Vertex, String>();

        int nodeCount = 0;
        for (Vertex v: graph.getVertices())
        {
            Element node = new Element("node").setAttribute("id", "n"+nodeCount).setAttribute("style", vertexStylesMap.get(v.getStyle()));

            Element position  = new Element("position");
            Element x = new Element("posx");
            Element y = new Element("posy");
            Element z = new Element("posz");
            if (v.getPosition() != null) {
                x.setText(String.valueOf(v.getPosition().getX()));
                y.setText(String.valueOf(v.getPosition().getY()));
                z.setText(String.valueOf(v.getPosition().getZ()));
            } else{
                x.setText("0");
                y.setText("0");
                z.setText("0");
            }

            position.addContent(x).addContent(y).addContent(z);

            Element label = new Element("label");
            if (v.getLabel() != null){
                label.setText(v.getLabel());
            }
            else {
                label.setText("");
            }

            node.addContent(position).addContent(label);

            vertexKeysMap.put(v, node.getAttributeValue("id"));
            graphxml.addContent(node);
            nodeCount++;
        }

        int edgeCount = 0;
        for (Edge e : graph.getEdges())
        {
            Element edge = new Element("edge")
                                .setAttribute("id", "e"+edgeCount)
                                .setAttribute("style", edgeStylesMap.get(e.getStyle()))
                                .setAttribute("source", vertexKeysMap.get(e.getSrcVertex()))
                                .setAttribute("target", vertexKeysMap.get(e.getDstVertex()));

            Element label = new Element("label");
            if (e.getLabel() != null){
                label.setText(e.getLabel());
            }
            else {
                label.setText("");
            }

            edge.addContent(label);

            graphxml.addContent(edge);
            edgeCount++;
        }

        //génération des key

        //shape
        //size
        //border-thickness
        //background-color
        //border-color
        //text-color
        //font-name

        //nodes
        //pos x
        //pos y
        //label

        graphml.addContent(graphxml);

        XMLOutputter xmlOutput = new XMLOutputter();

        // display nice nice
        xmlOutput.setFormat(Format.getPrettyFormat());

        try {
            xmlOutput.output(gDoc, new FileWriter(filename+".xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element constructNodeStyleKey(Element base, VertexStyle vertexStyle, int count){
        if (base == null){
            base = new Element("node-style").setAttribute("id", "ns"+count);
            base.addContent(new Element("data")
                                    .setAttribute("key", "shape")
                                    .setText(vertexStyle.getShape().toString()));
            base.addContent(new Element("data")
                                    .setAttribute("key", "size")
                                    .setText(String.valueOf(vertexStyle.getSize())));
            base.addContent(new Element("data")
                                    .setAttribute("key", "border-thickness")
                    .setText(String.valueOf(vertexStyle.getBorderThickness())));
            base.addContent(new Element("data")
                                    .setAttribute("key", "background-color")
                                    .setText(ColorUtils.colorToString(vertexStyle.getBackgroundColor())));
            base.addContent(new Element("data")
                                    .setAttribute("key", "text-color")
                                    .setText(ColorUtils.colorToString(vertexStyle.getTextColor())));
            base.addContent(new Element("data")
                                    .setAttribute("key", "border-color")
                                    .setText(ColorUtils.colorToString(vertexStyle.getBorderColor())));
            base.addContent(new Element("data")
                                    .setAttribute("key", "font")
                                    .setText(FontUtils.fontToString(vertexStyle.getFont())));
        }

        return base;
    }

    private Element constructEdgeStyleKey(Element base, EdgeStyle edgeStyle, int count){
        if (base == null){
            base = new Element("edge-style").setAttribute("id", "es"+count);
        }

        base.addContent(new Element("data")
                .setAttribute("key", "thickness")
                .setText(String.valueOf(edgeStyle.getThickness())));
        base.addContent(new Element("data")
                .setAttribute("key", "color")
                .setText(ColorUtils.colorToString(edgeStyle.getColor())));
        base.addContent(new Element("data")
                .setAttribute("key", "font")
                .setText(FontUtils.fontToString(edgeStyle.getFont())));
        base.addContent(new Element("data")
                .setAttribute("key", "line-style")
                .setText(edgeStyle.getStyle().toString()));

        return base;
    }

	@Override
	public boolean isConform(String filname) {
		// TODO Auto-generated method stub
		return false;
	}

}
