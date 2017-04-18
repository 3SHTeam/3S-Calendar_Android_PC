package Data;

public class EventData implements DataInfo{
	private String[] uData;
	
	private String date;
	
	public EventData() {
		// TODO Auto-generated constructor stub
	}

	
	public EventData(String Sid, String SMaster, String Sname, String Place,
							String StartTime, String EndTime, String date){
		setData(0,Sid);
		setData(1,SMaster);
		setData(2,Sname);
		setData(3,Place);
		setData(4,StartTime);
		setData(5,EndTime);
		setData(6,date);//외부에서 date를 보기 위해서 event.getData(6);
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
		String sql = "schedules(SMaster, Sname, Place, StartTime, EndTime) Values ";

        sql += "('" + uData[1] + "','" + uData[2] + "','"+ uData[3] + "','"
                + uData[4] + "','" + uData[5] + "')";
        return sql;
	}

}
