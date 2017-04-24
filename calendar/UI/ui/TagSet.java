package ui;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import Data.TagData;



public class TagSet {

	private ArrayList<TagData> tags = new ArrayList<TagData>();
	public TagSet(){

	}
	public static Color get_Color(String c) {//String->Color
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color").getField(c);
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		return color;
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
}
