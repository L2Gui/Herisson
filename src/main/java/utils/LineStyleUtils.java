package utils;

import model.LineStyle;

public class LineStyleUtils {

	public static LineStyle stringToLineStyle(String value)
	{
		LineStyle style = LineStyle.SOLID;
		
		switch (value.toLowerCase()){
			case "solid" :
				style = LineStyle.SOLID;
				break;
			case "dotted":
				style = LineStyle.DOTTED;
				break;
			case "dashed":
				style = LineStyle.DASHED;
				break;
		}
		
		return style;
	}
}
