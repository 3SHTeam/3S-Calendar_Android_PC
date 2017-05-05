package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;

import DB.JsonMaster;
import DB.SendToDB;
import Data.FriendData;
import Data.GroupData;

public class EFriend_GroupMain extends JFrame implements ActionListener{
	private JTabbedPane tab;

	private JPanel FriendPanel;
	private JSplitPane GroupPanel; // 그룹 정보(왼쪽 그룹이름, 오른쪽 해당 그룹의 스캐줄)
	private JPanel RequestPanel;

	private EFriend eFriend;
	private EGroup eGroup;
	private ERequest eRequest;

	private DMonth_CalendarMain calendar; // 스캐줄 정보를 가져오기 위해

	private JButton mypageBtn;
	private JButton calendarBtn;
	private JButton settingBtn;
	private JButton logoutBtn;
	
	public EFriend_GroupMain(DMonth_CalendarMain calendar) {
		
		this.calendar = calendar;
		
		setTitle("Friend_Group");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1210, 850);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		tab = new JTabbedPane();
		tab.setBounds(0, 0, 1194, 710);
		
		//tab의 group부분
		eGroup=new EGroup(this);
		GroupPanel=eGroup.getGroupSpiltPanel();

		//tab의 Friend부분
		EFriend friend=new EFriend(eGroup);
		FriendPanel=friend.getFriendPanel();

		//tab의 request부분
		ERequest eRequest=new ERequest(this);
		RequestPanel=eRequest.getRequestPanel();

		tab.addTab("친구", FriendPanel);
		tab.addTab("그룹", GroupPanel);	
		tab.addTab("요청메세지", RequestPanel);
		
		getContentPane().add(tab);

			calendarBtn = new JButton("달력");
			calendarBtn.setBounds(321, 752, 115, 23);
			calendarBtn.addActionListener(this);
			getContentPane().add(calendarBtn);
			
			mypageBtn = new JButton("마이페이지");
			mypageBtn.setBounds(469, 752, 115, 23);
			mypageBtn.addActionListener(this);
			getContentPane().add(mypageBtn);


			settingBtn = new JButton("환경설정");
			settingBtn.setBounds(615, 752, 115, 23);
			settingBtn.addActionListener(this);
			getContentPane().add(settingBtn);

			logoutBtn = new JButton("로그아웃");
			logoutBtn.setBounds(758, 752, 115, 23);
			logoutBtn.addActionListener(this);
			getContentPane().add(logoutBtn);
	}
	

	public void setDMonth_CalendarView(DMonth_CalendarMain calendar) {
		this.calendar = calendar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mypageBtn) {
			CTreeView mypage = (CTreeView) CTreeView.getInstanace();
			mypage.setVisible(true);
			dispose();
		}
		
		if (e.getSource() == settingBtn) {
			FSetting setting=new FSetting();
			setting.setVisible(true);
			dispose();
		}
		
		if (e.getSource() == calendarBtn) {
			calendar.setVisible(true);
			dispose();
		}
		
		if (e.getSource() == logoutBtn) {

		}
	}


	public String getUserId() {
		return calendar.getUser().getData(0);
	}

}
