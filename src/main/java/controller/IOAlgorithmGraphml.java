package controller;

import model.Edge;
import model.Graph;
import model.Vertex;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
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

	}

	@Override
	public boolean isConform(String filname) {
		// TODO Auto-generated method stub
		return false;
	}

}
