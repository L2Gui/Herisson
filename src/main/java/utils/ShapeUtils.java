package utils;

import model.VertexShape;

public class ShapeUtils {
	public static VertexShape stringToShape(String value){
		
		VertexShape shape = VertexShape.SQUARE;

        String testable = value.toLowerCase();
		if (testable == "square") {
            shape = VertexShape.SQUARE;
        }
        if (testable == "circle") {
            shape = VertexShape.CIRCLE;
        }
        if (testable == "diamond"){
			shape = VertexShape.DIAMOND;
        }
		
		return shape;
	}
}
