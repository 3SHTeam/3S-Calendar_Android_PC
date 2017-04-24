package ui;

public class GroupData {
	private String GroupName=null;	//그룹명
	private String GroupIntro=null;	//그룹소개
	
	public GroupData(String GroupName, String GroupIntro){
		setGroupName(GroupName);
		setGroupIntro(GroupIntro);
		
	}
	
	public String getGroupName() {
		return GroupName;
	}
	public void setGroupName(String groupName) {
		GroupName = groupName;
	}
	public String getGroupIntro() {
		return GroupIntro;
	}
	public void setGroupIntro(String groupintro) {
		GroupIntro = groupintro;
	}
	
	
	
}
