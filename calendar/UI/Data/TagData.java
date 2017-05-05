package Data;

import javax.swing.JCheckBox;

public class TagData implements DataInfo {
	private String[] uData = new String[8];

	private JCheckBox box;// 태그들을 체크박스로


	public TagData(String Tagid, String Tag_name, String Color, String Font, String Size, String Gid) {

		setData(0, Tagid);
		setData(1, Tag_name);
		setData(2, Color);
		setData(3, Font);
		setData(4, Size);
		setData(5, Gid);
		makebox();
	}

	private void makebox() {
		this.box = new JCheckBox(this.uData[1]);
		box.setSelected(true);
	}

	public boolean checkStatus() {
		return box.isSelected();
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
		String sql = "'" + uData[1] + "','" + uData[2] + "','" + uData[3] + "','" + uData[4] + "'";
		return sql;
	}

	@Override
	public void showData() {
		System.out.println("Tagid : " + uData[0]);
		System.out.println("Tag_name : " + uData[1]);
		System.out.println("Color : " + uData[2]);
		System.out.println("Font : " + uData[3]);
		System.out.println("Size : " + uData[4]);
		System.out.println("Gid : " + uData[5]);

	}

	public JCheckBox getBox() {
		return box;
	}

	public void setBox(JCheckBox box) {
		this.box = box;
	}

}
