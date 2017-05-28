package Calendar.UI;
//
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.*;

//import org.apache.commons.codec.binary.Hex;

import Calendar.Data.TagData;



public class TagColorSet {

	public TagColorSet(){

	}
	
	//String ¹®ÀÚ->color
	public static Color strToColor(String c) {
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color").getField(c);
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		return color;
	}
	
	//Color->Hex
	public static String colorToHex(Color c){
		String rgbToHex="#"+Integer.toHexString(c.getRed())
						+Integer.toHexString(c.getGreen())
						+Integer.toHexString(c.getBlue());
		
		return rgbToHex;
	}
	
	//String(Hex)->Color
	public static Color hex2Rgb(String colorStr) {
		int hex = Integer.decode(colorStr);
	    int r = (hex & 0xFF0000) >> 16;
	    int g = (hex & 0xFF00) >> 8;
	    int b = (hex & 0xFF);
	    return new Color(r,g,b);
	}
}
