package utils;

import java.awt.*;

/**
 * Created by Arnaud on 15/03/2015.
 */
public class FontUtils {

    public static String fontToString(Font font){
        return font.getFontName()+","+font.getSize()+","+font.getStyle()+"";
    }

    public static Font stringToFont(String string){
        String[] fontAttrs = string.split(",");

        if (fontAttrs.length == 3)
            return new Font(fontAttrs[0], Integer.parseInt(fontAttrs[2]), Integer.parseInt(fontAttrs[1]));
        else
            return new Font("Verdana", Font.PLAIN, 4);
    }
}
