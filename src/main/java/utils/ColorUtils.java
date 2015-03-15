/**
 * 
 * LibSparkline : a free Java sparkline chart library
 * 
 *
 * Project Info:  http://reporting.pentaho.org/libsparkline/
 *
 * (C) Copyright 2008, by Larry Ogrodnek, Pentaho Corporation and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the Apache License 2.0.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the Apache License 2.0 along with this library;
 * if not, a online version is available at http://www.apache.org/licenses/
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------
 * ColorUtils.java
 * ------------
 */

package utils;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * 
 * THIS IS THIRD PARTY CODE SOURCE
 * FOUND at : http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Mapcolorsintonamesandviceversa.htm
 *
 */


public class ColorUtils {

    public static String colorToString(Color color){
        return color.getBlue()+","+color.getGreen()+","+color.getRed();
    }

    public static Color RGBStringToColor(String rgbString) {
        String[] RGB = rgbString.split(",");

        if (RGB.length == 3)
            return new Color(Integer.parseInt(RGB[0]),Integer.parseInt(RGB[1]),Integer.parseInt(RGB[2]));
        else
            return Color.black;
    }

	 private static final HashMap knownColorNamesByColor;

	  private static final HashMap knownColorsByName;

	  static {
	    knownColorNamesByColor = new HashMap();
	    knownColorsByName = new HashMap();
	    try {
	      final Field[] fields = Color.class.getFields();
	      for (int i = 0; i < fields.length; i++) {
	        final Field f = fields[i];
	        if (Modifier.isPublic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())
	            && Modifier.isStatic(f.getModifiers())) {
	          final String name = f.getName();
	          final Object oColor = f.get(null);
	          if (oColor instanceof Color) {
	            knownColorNamesByColor.put(oColor, name.toLowerCase());
	            knownColorsByName.put(name.toLowerCase(), oColor);
	          }
	        }
	      }
	    } catch (Exception e) {
	      // ignore ..
	    }
	  }

	  /**
	   * Utility class constructor prevents object creation.
	   */
	  private ColorUtils() {
	  }

	  /**
	   * Parse a String into a Color. <p/> This method will accept either a color
	   * name (a field name from {@link Color}, case insensitive e.g. "red"), or a
	   * HTML hex color string (e.g. "#ff0000" for Color.RED).
	   * 
	   * @param value
	   *          String to parse for color name or color number.
	   * @return Color for s.
	   */
	  private static Color parseColor(final String value) {
	    if (value == null) {
	      return null;
	    }

	    final Object o = knownColorsByName.get(value.toLowerCase());
	    if (o != null) {
	      return (Color) o;
	    }

	    try {
	      // get color by hex or octal value
	      return Color.decode(value.trim());
	    } catch (NumberFormatException nfe) {
	      return null;
	    }
	  }

	  /**
	   * Parse a String into a Color, and returns the given default value if the
	   * color is not parsable. <p/> This method will accept either a color name (a
	   * field name from {@link Color}, case insensitive e.g. "red"), or a HTML hex
	   * color string (e.g. "#ff0000" for Color.RED).
	   * 
	   * @param colorText
	   *          String to parse for color name or color number.
	   * @param defValue
	   *          the default value that should be returned if the string is not
	   *          parseable or null.
	   * @return Color for the text.
	   */
	  public static Color convertColor(final String colorText, final Color defValue) {
	    if (colorText == null || colorText.isEmpty()) {
	      return defValue;
	    }

	    final Color retval = parseColor(colorText);
	    if (retval == null) {
	      return defValue;
	    }
	    return retval;
	  }
}
