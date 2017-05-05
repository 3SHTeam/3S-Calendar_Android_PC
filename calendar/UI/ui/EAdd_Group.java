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

		JLabel PrefaceLabel = new JLabel("그룹 생성");
		PrefaceLabel.setFont(new Font("굴림", Font.BOLD, 18));
		PrefaceLabel.setBounds(150, 0, 100, 50);
		contentPane.add(PrefaceLabel);

		JLabel GroupNameLabel = new JLabel("그룹이름 :");
		GroupNameLabel.setBounds(70, 100, 65, 15);
		contentPane.add(GroupNameLabel);

		JLabel GroupIntroLabel = new JLabel("그룹소개 :");
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

		JButton createGBtn = new JButton("생성");
		createGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 내용 저장
				makeGroup();
				// addGroupToDB(groupData.getSendSQLString());
				// DB에서 group 데이터 최신화 하고 왼쪽 그룹이름테이블 다시 보여줌
				eGroup.refreshLeftNameList();
				dispose();

			}
		});

		createGBtn.setBounds(70, 320, 97, 23);
		contentPane.add(createGBtn);

		JButton cancleBtn = new JButton("취소");
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
		
		//새로운 그룹을 DB에 추가
		addGroupToDB(group.getSendSQLString());
		
		//새로운 그룹에 가입하고 새로고침
		eGroup.makeGroup(GroupNameField.getText());
	}

	// 그룹추가->DB
	private void addGroupToDB(String sql) {
		/* DB에 그룹 삽입하기 */
		String url = "http://113.198.84.66/Calendar3S/InsertGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
}
