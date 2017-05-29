package Calendar.Data;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestEventData {
	private String groupName;
	private String eventName;
	private String eventDate;
	private String location;
	private MessageData request_MsgData;
	
	public RequestEventData(MessageData request_MsgData) {
		super();
		this.request_MsgData=request_MsgData;
		String msg = request_MsgData.getData(4);
		
		//메세지에서 정보를 나누기
		String []tok=msg.split("/");
		eventName=tok[0];
		eventDate=tok[2] + " - " + tok[3];
		location=tok[1];

		groupName=request_MsgData.getData(6);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public MessageData getRequest_MsgData() {
		return request_MsgData;
	}

	public void setRequest_MsgData(MessageData request_MsgData) {
		this.request_MsgData = request_MsgData;
	}
}
