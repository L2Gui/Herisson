package utils;

import model.VertexShape;

public class ShapeUtils {
	public static VertexShape stringToShape(String value){
		
		VertexShape shape = VertexShape.SQUARE;

        String testable = value.toLowerCase();
        System.out.println(testable);
		if (testable.contains("carré") || testable == "carré") {
            shape = VertexShape.SQUARE;
        }
        if (testable.contains("cercle") || testable == "cercle") {
            shape = VertexShape.CIRCLE;
        }
        if (testable.contains("losange") || testable == "losange"){
			shape = VertexShape.DIAMOND;
        }
		
		return shape;
	}
}
