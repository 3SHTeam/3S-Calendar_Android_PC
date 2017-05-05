package ui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.JsonMaster;
import DB.SendToDB;

public class ERequest extends JFrame{

	private EFriend_GroupMain fgMain;
	
	private JPanel RequestPanel;
	private JTable RequestTable;
	private JScrollPane scrollPane;
	
	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;	
	
	public ERequest(EFriend_GroupMain fgMain) {
		this.fgMain=fgMain;
		
		RequestPanel = new JPanel();
		RequestPanel.setLayout(null);
		String[] columnNames2 = { "소제목 명", "그룹", "날짜", "장소", "참석여부" };
		DefaultTableModel request_model = new DefaultTableModel(columnNames2, 0);
		RequestTable = new JTable(request_model);// 요청리스트테이블
		RequestTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));
		RequestTable.getColumnModel().getColumn(0).setPreferredWidth(300);// 각 컬럼이름 사이즈 조정
		RequestTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		RequestTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(4).setPreferredWidth(300);

		scrollPane = new JScrollPane(RequestTable);
		scrollPane.setBounds(0, 53, 1189, 628);
		RequestPanel.add(scrollPane);
	}

	public JPanel getRequestPanel() {return RequestPanel;}
}
