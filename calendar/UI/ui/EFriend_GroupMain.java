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
	private JSplitPane GroupPanel; // �׷� ����(���� �׷��̸�, ������ �ش� �׷��� ��ĳ��)
	private JPanel RequestPanel;

	private EFriend eFriend;
	private EGroup eGroup;
	private ERequest eRequest;

	private DMonth_CalendarMain calendar; // ��ĳ�� ������ �������� ����

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
		
		//tab�� group�κ�
		eGroup=new EGroup(this);
		GroupPanel=eGroup.getGroupSpiltPanel();

		//tab�� Friend�κ�
		EFriend friend=new EFriend(eGroup);
		FriendPanel=friend.getFriendPanel();

		//tab�� request�κ�
		ERequest eRequest=new ERequest(this);
		RequestPanel=eRequest.getRequestPanel();

		tab.addTab("ģ��", FriendPanel);
		tab.addTab("�׷�", GroupPanel);	
		tab.addTab("��û�޼���", RequestPanel);
		
		getContentPane().add(tab);

			calendarBtn = new JButton("�޷�");
			calendarBtn.setBounds(321, 752, 115, 23);
			calendarBtn.addActionListener(this);
			getContentPane().add(calendarBtn);
			
			mypageBtn = new JButton("����������");
			mypageBtn.setBounds(469, 752, 115, 23);
			mypageBtn.addActionListener(this);
			getContentPane().add(mypageBtn);


			settingBtn = new JButton("ȯ�漳��");
			settingBtn.setBounds(615, 752, 115, 23);
			settingBtn.addActionListener(this);
			getContentPane().add(settingBtn);

			logoutBtn = new JButton("�α׾ƿ�");
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
