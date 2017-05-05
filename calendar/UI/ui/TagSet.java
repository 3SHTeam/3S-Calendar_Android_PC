package ui;
//
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.commons.codec.binary.Hex;

import Data.TagData;



public class TagSet {

	private ArrayList<TagData> tags = new ArrayList<TagData>();
	public TagSet(){

	}
	
	//String 문자->color
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
		String rgbToHex="0x"+Integer.toHexString(c.getRed())
						+Integer.toHexString(c.getGreen())
						+Integer.toHexString(c.getBlue());
		
		return rgbToHex;
	}

	public String[] checkTagids() {
		String[] tagIds = new String[tags.size()];
		for(int i=0;i<tags.size();i++)
			if(tags.get(i).checkStatus())
				tagIds[i] = tags.get(i).getData(0);
		
		return tagIds;
	}
	
	
	/*addSchedule할 때 태크체크박스에집어넣어줌*/
	public String[] checkTagName() {
		String[] tagNames = new String[tags.size()];
		for(int i=0;i<tags.size();i++)
			if(tags.get(i).checkStatus())
				tagNames[i] = tags.get(i).getData(1);
		
		return tagNames;
	}
	public void setTags(ArrayList<TagData> tags) {
		this.tags=tags;
		
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
