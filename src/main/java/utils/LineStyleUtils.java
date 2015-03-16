package utils;

import model.LineStyle;

public class LineStyleUtils {

	public static LineStyle stringToLineStyle(String value)
	{
		LineStyle style = LineStyle.SOLID;

        String testable = value.toLowerCase();

        if (testable ==  "solid") {
            style = LineStyle.SOLID;
        }
        if (testable == "dotted") {
            style = LineStyle.DOTTED;
        }
        if (testable == "dashed"){
			style = LineStyle.DASHED;
		}
		
		return style;
	}
}
