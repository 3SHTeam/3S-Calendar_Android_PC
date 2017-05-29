package Calendar.Data;

import java.util.ArrayList;

public class RequestGroupData {
	 private String groupName;
	 private String groupInviteMsg;
	 private String sender;
	 private MessageData request_MsgData;
	public RequestGroupData(MessageData requestMsg) {
		super();
		this.request_MsgData=requestMsg;
		groupName=requestMsg.getData(6);
		groupInviteMsg=requestMsg.getData(4);
		sender=requestMsg.getData(1);
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupInviteMsg() {
		return groupInviteMsg;
	}
	public void setGroupInviteMsg(String groupInvite_Msg) {
		this.groupInviteMsg = groupInvite_Msg;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public MessageData getRequest_MsgData() {
		return request_MsgData;
	}
	public void setRequest_MsgData(MessageData requestMsg) {
		this.request_MsgData = requestMsg;
	}

}
