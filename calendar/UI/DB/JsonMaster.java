package DB;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.EventData;
import Data.TagData;
import Data.UserData;

public class JsonMaster {
	private ArrayList<EventData> events = new ArrayList<EventData>();
	private String result = "";
	private UserData user;
	private ArrayList<TagData> tags = new ArrayList<TagData>();

	public String getResult() {return result;}
	public void setResult(String result) {this.result = result;}
	public ArrayList<EventData> getEvents() {return events;}
	public void setEvents(ArrayList<EventData> events) {this.events = events;}
	public UserData getUser() {return user;}
	public void setUser(UserData user) {this.user = user;}
	public ArrayList<TagData> getTags() {return tags;}
	public void setTags(ArrayList<TagData> tags) {this.tags = tags;}
	
	public void onPostExecute(String php, String str){
		switch(php){
		case "SelectMySchedule":
	         selectMySchedule(str);
	         break;
	         
		case "SelectMyProfile":
			selectMyProfile(str);
			break;
	      
		case "SelectMyTag":
			selectMyTag(str);
			break;
		}
     }
	
	
	private void selectMyTag(String str) {
		String Tagid;
        String Tag_name;
        String Color;
        String Font;
        String Size;
        String Gid;
        
        TagData tag;
        try{
            JSONObject root = new JSONObject(str);
            if(root.get("rownum").equals("0")) {
            	this.tags = null;
            	System.out.println("태그가 없음!");
            	return;
            }
            
            JSONArray ja = root.getJSONArray("result");

            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                Tagid = jo.getString("Tagid");
                Tag_name= jo.getString("Tag_name");
                Color = jo.getString("Color");
                Font = jo.getString("Font");
                Size = jo.getString("Size");
                Gid = jo.getString("Gid");
                
                System.out.println(Tagid + " , " + Tag_name + " , " + Color
                      + " , " + Font + " , " + Size + " , " + Gid);
                
                tag = new TagData(Tagid, Tag_name, Color, Font, Size, Gid);      
                this.tags.add(tag);   
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
		
	}
	private void selectMyProfile(String str) {
		String Googleid;
        String name;
        String gender;
        String nickname;
        String birth;
        String phone;
        String comment;
        String FBuId;
        
        EventData event;
        try{
            JSONObject root = new JSONObject(str);
            if(root.get("rownum").equals("0")) {
            	this.user = null;
            	System.out.println("로그인 실패!");
            	return;
            }
            
            JSONArray ja = root.getJSONArray("result");

            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                Googleid = jo.getString("Googleid");
                name= jo.getString("name");
                gender = jo.getString("gender");
                nickname = jo.getString("nickname");
                birth = jo.getString("birth");
                phone = jo.getString("phone");
                comment = jo.getString("comment");
                FBuId = jo.getString("FBuId");
                
                System.out.println(Googleid + " , " + name + " , " + gender
                      + " , " + nickname + " , " + birth + " , " + phone
                      + " , " + comment + " , " + FBuId );
                
                this.user = new UserData(Googleid, name, gender, nickname, birth, phone, comment, FBuId);           
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
		
	}
	
	
	private void selectMySchedule(String str) {
		String Sid;
        String SMaster;
        String Sname;
        String Place;
        String StartTime;
        String EndTime;
        String Gid;
        String GoogleSid;
        String TagId;
        
        EventData event;
        try{
            JSONObject root = new JSONObject(str);
            if(root.get("rownum").equals("0")) {
            	this.events = null;
            	System.out.println("태그가 없음!");
            	return;
            }
            
            JSONArray ja = root.getJSONArray("result");
            for(int i=0; i<ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                Sid = jo.getString("Sid");
                SMaster = jo.getString("SMaster");
                Sname = jo.getString("Sname");
                Place = jo.getString("Place");
                StartTime = jo.getString("StartTime");
                EndTime = jo.getString("EndTime");
                Gid = jo.getString("Gid");
                GoogleSid = jo.getString("GoogleSid");
                TagId = jo.getString("Tagid");
                
                System.out.println(Sid + " , " + SMaster + " , " + Sname
                      + " , " + Place + " , " + StartTime + " , " + EndTime
                      + " , " + Gid + " , " + GoogleSid + " , " + TagId);
                
                //String Sid, String SMaster, String Sname, String Place,
				 //String StartTime, String EndTime, String date
                event = new EventData(Sid,SMaster,Sname,Place,StartTime,EndTime,TagId,GoogleSid,Gid);
                this.events.add(event);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
	}
	
	 
	 
}
