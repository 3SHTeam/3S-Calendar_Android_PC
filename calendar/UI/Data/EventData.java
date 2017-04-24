package Data;

public class EventData implements DataInfo{
	private String[] uData = new String[9];
	private String Sid,SMaster=null,Sname=null,Place=null;
	private String StartTime="0000",EndTime="0000",TagId,GoogleSId,GId;
	
	private int year,month,day;

	public EventData(){
		
	}
	
	public EventData(String Sid, String SMaster, String Sname, String Place,
							String StartTime, String EndTime,String TagId,String GoogleSId,String GId){
		this.Sid=Sid;
		this.SMaster=SMaster;
		this.Sname=Sname;
		this.Place=Place;
		this.StartTime=StartTime;
		this.EndTime=EndTime;
		this.TagId=TagId;
		this.GoogleSId=GoogleSId;
		this.GId=GId;
		
		
		setData(0,Sid);
		setData(1,SMaster);
		setData(2,Sname);
		setData(3,Place);
		setData(4,StartTime);
		setData(5,EndTime);
		setData(6,TagId);
		setData(7,GoogleSId);
		setData(8,GId);
		splitDate();
	}
	

	private void splitDate() {
		//yyyyMMddhhmm
		year = Integer.valueOf(uData[4].substring(0, 4));
		month = Integer.valueOf(uData[4].substring(4, 6));
		day = Integer.valueOf(uData[4].substring(6, 8));
	}

	public Integer getYear() {
		return year;
	}


	public Integer getMonth() {
		return month;
	}


	public Integer getDay() {
		return day;
	}

	@Override
	public String[] getData() {
		return uData;
	}

	@Override
	public String getData(int index) {
		return uData[index];
	}

	@Override
	public void setData(int index, String data) {
		uData[index] = data;
	}

	@Override
	public void setData(String[] uData) {
		this.uData = uData;
	}

	@Override
	public String getSendSQLString() {
		String sql = "schedules(SMaster, Sname, Place, StartTime, EndTime, TagId, GoogleSId, GId) Values ";

        sql += "('" + uData[1] + "','" + uData[2] + "','"+ uData[3] + "','"
                + uData[4] + "','" + uData[5]+ "','" + uData[6]+ "','" + uData[7]+ "','" + uData[8] + "')";
        return sql;
	}


	@Override
	public void showData() {
		System.out.println("Sid : " + uData[0]);
		System.out.println("SMaster : " + uData[1]);
		System.out.println("Sname : " + uData[2]);
		System.out.println("Place : " + uData[3]);
		System.out.println("StartTime : " + uData[4]);
		System.out.println("EndTime : " + uData[5]);
		System.out.println("TagId : " + uData[6]);
		System.out.println("GoogleSId : " + uData[7]);
		System.out.println("GId : " + uData[8]);
		System.out.println("Date : " + year + "/" +  + month + "/" + day);
	}

}
