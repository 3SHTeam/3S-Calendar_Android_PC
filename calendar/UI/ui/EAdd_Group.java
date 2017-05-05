package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.api.services.calendar.Calendar;

import DB.SendToDB;
import Data.EventData;
import Data.GroupData;

public class EAdd_Group extends JFrame {

	private JPanel contentPane;
	private GroupData groupData;

	private JLabel GroupIntroLabel;

	private JTextField GroupNameField;
	private JTextField GroupIntroField;
	private EGroup eGroup;

	public EAdd_Group(EGroup eGroup) {
		this.eGroup = eGroup;
		init();
	}

	private void init() {
		setTitle("Make Group");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel PrefaceLabel = new JLabel("�׷� ����");
		PrefaceLabel.setFont(new Font("����", Font.BOLD, 18));
		PrefaceLabel.setBounds(150, 0, 100, 50);
		contentPane.add(PrefaceLabel);

		JLabel GroupNameLabel = new JLabel("�׷��̸� :");
		GroupNameLabel.setBounds(70, 100, 65, 15);
		contentPane.add(GroupNameLabel);

		JLabel GroupIntroLabel = new JLabel("�׷�Ұ� :");
		GroupIntroLabel.setBounds(70, 150, 65, 15);
		contentPane.add(GroupIntroLabel);

		GroupNameField = new JTextField();
		GroupNameField.setBounds(140, 100, 158, 21);
		contentPane.add(GroupNameField);
		GroupNameField.setColumns(10);

		GroupIntroField = new JTextField();
		GroupIntroField.setColumns(10);
		GroupIntroField.setBounds(140, 150, 158, 150);
		contentPane.add(GroupIntroField);

		JButton createGBtn = new JButton("����");
		createGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���� ����
				makeGroup();
				// addGroupToDB(groupData.getSendSQLString());
				// DB���� group ������ �ֽ�ȭ �ϰ� ���� �׷��̸����̺� �ٽ� ������
				eGroup.refreshLeftNameList();
				dispose();

			}
		});

		createGBtn.setBounds(70, 320, 97, 23);
		contentPane.add(createGBtn);

		JButton cancleBtn = new JButton("���");
		cancleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancleBtn.setBounds(220, 320, 97, 23);
		contentPane.add(cancleBtn);
	}

	private void makeGroup() {// GroupData Set
		GroupData group = new GroupData();

		group.setData(1, GroupNameField.getText());
		group.setData(2, GroupIntroField.getText());
		group.setData(3, eGroup.getMyid());
		
		//���ο� �׷��� DB�� �߰�
		addGroupToDB(group.getSendSQLString());
		
		//���ο� �׷쿡 �����ϰ� ���ΰ�ħ
		eGroup.makeGroup(GroupNameField.getText());
	}

	// �׷��߰�->DB
	private void addGroupToDB(String sql) {
		/* DB�� �׷� �����ϱ� */
		String url = "http://113.198.84.66/Calendar3S/InsertGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
}
