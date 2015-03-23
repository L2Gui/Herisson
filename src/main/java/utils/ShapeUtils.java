package utils;

import model.VertexShape;

public class ShapeUtils {
	public static VertexShape stringToShape(String value){
		
		VertexShape shape = VertexShape.SQUARE;

        String testable = value.toLowerCase();
		if (testable.contains("carré")) {
            shape = VertexShape.SQUARE;
        }
        if (testable.contains("cercle")) {
            shape = VertexShape.CIRCLE;
        }
        if (testable.contains("losange")){
			shape = VertexShape.DIAMOND;
        }
		
		return shape;
	}
}
