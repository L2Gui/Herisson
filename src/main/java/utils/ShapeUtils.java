package utils;

import model.VertexShape;

public class ShapeUtils {
	public static VertexShape stringToShape(String value){
		
		VertexShape shape = VertexShape.SQUARE;
		
		switch (value.toLowerCase())
		{
			case "square":
				shape = VertexShape.SQUARE;
				break;
			case "circle":
				shape = VertexShape.CIRCLE;
				break;
			case "diamond":
				shape = VertexShape.DIAMOND;
				break;
			default :
				break;
		}
		
		return shape;
	}
}
