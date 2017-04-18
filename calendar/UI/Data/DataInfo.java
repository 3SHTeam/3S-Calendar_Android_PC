package Data;

public interface DataInfo {
	public String[] getData();
	public String getData(int index);
	public void setData(int index,String data);
	public void setData(String[] uData);
	
	public String getSendSQLString();
}
