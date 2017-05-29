package Calendar.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalendarTimeFormat {
	static SimpleDateFormat df_hour=new SimpleDateFormat("HH");
	static SimpleDateFormat df_min=new SimpleDateFormat("mm");
	public CalendarTimeFormat(){

	}
	
	
	public static String intHour_DateformatStr(int time) throws ParseException{
		return df_hour.format(df_hour.parse(String.valueOf(time)));
	}
	
	public static String intMin_DateformatStr(int time) throws ParseException{
		return df_min.format(df_min.parse(String.valueOf(time)));
	}
}