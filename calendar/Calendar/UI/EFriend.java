package Calendar.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.FriendData;

public class EFriend  extends JFrame{
	
	private EGroup eGroup;
	
	private JPanel FriendPanel;	
	private JTable FriendTable;
	private JScrollPane scrollPane;
	
	private Vector<FriendData> FriendVec = new Vector<FriendData>();
	private EAdd_Group MakeGroup;
	
	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;
	
	public EFriend(EGroup eGroup){
		
		this.eGroup=eGroup;
		
		FriendPanel = new JPanel();
		FriendPanel.setLayout(null);

//		setFriendData("������", "Google1@gmail.com", 'M', 000000, 00000000000, "scheduleM", "�ȳ� �ݰ���~~~!!");
//		FriendVec.add(fd);
//		setFriendData("�ռ�ȯ", "Google2@gmail.com", 'M', 000000, 00000000000, "ssh", "�ȳ� �ݰ���~~~!!");
//		FriendVec.add(fd);
//		setFriendData("������", "Google3@gmail.com", 'M', 000000, 00000000000, "yuj", "�ȳ� �ݰ���~~~!!");
//		FriendVec.add(fd);
//		setFriendData("���Ӱ�", "Google4@gmail.com", 'W', 000000, 00000000000, "llk", "�ȳ� �ݰ���~~~!!");
//		FriendVec.add(fd);
//		setFriendData("�ֺ���", "Google5@gmail.com", 'W', 000000, 00000000000, "cby", "�ȳ� �ݰ���~~~!!");
//		FriendVec.add(fd);

		String[] columnNames = { "�̸�", "���۾��̵�", "����", "�������", "�ڵ�����ȣ", "�г���", "�Ѹ���" };// ���̺������������

		DefaultTableModel friend_model = new DefaultTableModel(columnNames, 0);// ������ù��°��

//		for (int i = 0; i < FriendVec.size(); i++) {
//
//			friend_model.addRow(new Object[] { FriendVec.get(i).getName(), FriendVec.get(i).getGoogleId(),
//					FriendVec.get(i).getSex(), FriendVec.get(i).getBirth(), FriendVec.get(i).getCellPhoneNum(),
//					FriendVec.get(i).getNickName(), FriendVec.get(i).getComment() });
//
//		}

		FriendTable = new JTable(friend_model);// ģ������Ʈ���̺�
		FriendTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));
		FriendTable.getColumnModel().getColumn(0).setPreferredWidth(100);// �� �÷��̸�����������
		FriendTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		FriendTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		FriendTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		FriendTable.getColumnModel().getColumn(6).setPreferredWidth(400);
		scrollPane = new JScrollPane(FriendTable);
		scrollPane.setBounds(0, 53, 1210, 628);
		FriendPanel.add(scrollPane);

		JButton inviteButton = new JButton("ģ���ʴ�");// ģ���ʴ��ư
		inviteButton.setBounds(28, 20, 97, 23);
		FriendPanel.add(inviteButton);

	}	
	
	public JPanel getFriendPanel() {return FriendPanel;}
	
	//	public void setFriendData(String name, String GoogleId, char sex, int birth, int cellPhoneNum, String nickName,
	//	String comment) {// ģ�� �߰�(�ӽ�)
	//fd = new FriendData(name, GoogleId, sex, birth, cellPhoneNum, nickName, comment);
	//}
}
