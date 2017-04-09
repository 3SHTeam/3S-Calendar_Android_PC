package ui;

public class ScheduleData {
	private String scheduleName=null;//스케줄이름
	private String subtitle=null;//소제목
	private String time=null;//시간
	private String priority=null;//우선순위
	private String location=null;//장소
	private String context=null;//내용
	private String share=null;//공유자
	private String date;//날짜
	private String group;//속한 그룹(기존 값은 개인)
	public int year,month,day;
	
	public ScheduleData(){}
	 public ScheduleData(String date){
		 this.date=date;
		 String []tok=date.split("/");
		 year=Integer.parseInt(tok[0]);
		 month=Integer.parseInt(tok[1]);
		 day=Integer.parseInt(tok[2]);
		 
	 }
	
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public void setDate(String date){
		this.date=date;
	}
	public String getDate() {
		return date;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}
