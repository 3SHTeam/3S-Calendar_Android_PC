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

//		setFriendData("가가가", "Google1@gmail.com", 'M', 000000, 00000000000, "scheduleM", "안녕 반가워~~~!!");
//		FriendVec.add(fd);
//		setFriendData("손성환", "Google2@gmail.com", 'M', 000000, 00000000000, "ssh", "안녕 반가워~~~!!");
//		FriendVec.add(fd);
//		setFriendData("유우재", "Google3@gmail.com", 'M', 000000, 00000000000, "yuj", "안녕 반가워~~~!!");
//		FriendVec.add(fd);
//		setFriendData("이임경", "Google4@gmail.com", 'W', 000000, 00000000000, "llk", "안녕 반가워~~~!!");
//		FriendVec.add(fd);
//		setFriendData("최보윤", "Google5@gmail.com", 'W', 000000, 00000000000, "cby", "안녕 반가워~~~!!");
//		FriendVec.add(fd);

		String[] columnNames = { "이름", "구글아이디", "성별", "생년월일", "핸드폰번호", "닉네임", "한마디" };// 테이블에저장될정보들

		DefaultTableModel friend_model = new DefaultTableModel(columnNames, 0);// 고정된첫번째줄

//		for (int i = 0; i < FriendVec.size(); i++) {
//
//			friend_model.addRow(new Object[] { FriendVec.get(i).getName(), FriendVec.get(i).getGoogleId(),
//					FriendVec.get(i).getSex(), FriendVec.get(i).getBirth(), FriendVec.get(i).getCellPhoneNum(),
//					FriendVec.get(i).getNickName(), FriendVec.get(i).getComment() });
//
//		}

		FriendTable = new JTable(friend_model);// 친구리스트테이블
		FriendTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));
		FriendTable.getColumnModel().getColumn(0).setPreferredWidth(100);// 각 컬럼이름사이즈조정
		FriendTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		FriendTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		FriendTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		FriendTable.getColumnModel().getColumn(6).setPreferredWidth(400);
		scrollPane = new JScrollPane(FriendTable);
		scrollPane.setBounds(0, 53, 1210, 628);
		FriendPanel.add(scrollPane);

		JButton inviteButton = new JButton("친구초대");// 친구초대버튼
		inviteButton.setBounds(28, 20, 97, 23);
		FriendPanel.add(inviteButton);

	}	
	
	public JPanel getFriendPanel() {return FriendPanel;}
	
	//	public void setFriendData(String name, String GoogleId, char sex, int birth, int cellPhoneNum, String nickName,
	//	String comment) {// 친구 추가(임시)
	//fd = new FriendData(name, GoogleId, sex, birth, cellPhoneNum, nickName, comment);
	//}
}
