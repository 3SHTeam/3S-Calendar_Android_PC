package ui;

public class FriendData {
	private String GoogleId;
	private String name;
	private char sex;
	private int birth;
	private int cellPhoneNum;
	private String nickName;
	private String comment;
	
	public FriendData(String name, String GoogleId,  char sex, int birth, int cellPhoneNum, String nickName, String comment){
		this.GoogleId = GoogleId;
		this.name = name;
		this.sex = sex;
		this.birth = birth;
		this.cellPhoneNum = cellPhoneNum;
		this.nickName = nickName;
		this.comment = comment;
	}
	
	public String getGoogleId() {
		return GoogleId;
	}
	public void setGoogleId(String googleId) {
		GoogleId = googleId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public int getBirth() {
		return birth;
	}
	public void setBirth(int birth) {
		this.birth = birth;
	}
	public int getCellPhoneNum() {
		return cellPhoneNum;
	}
	public void setCellPhoneNum(int cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
