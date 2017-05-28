package Calendar.UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

//import com.google.api.services.calendar.Calendar;

import Calendar.DB.SendToDB;
import Calendar.Data.GroupData;
public class EAdd_Group extends JDialog {

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
		setBounds(100, 100, 450, 500);
		Image img=Images.smainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    //super.paintComponents(g);
				    g.drawImage(img, 0, 0, 450, 500, null);
				   }
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel PrefaceLabel = new JLabel("그룹 만들기");
		PrefaceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		PrefaceLabel.setBounds(150, 10, 200, 50);
		contentPane.add(PrefaceLabel);

		JLabel GroupNameLabel = new JLabel("그룹이름 :");
		GroupNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		GroupNameLabel.setBounds(30, 100, 100, 40);
		contentPane.add(GroupNameLabel);

		JLabel GroupIntroLabel = new JLabel("그룹소개 :");
		GroupIntroLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		GroupIntroLabel.setBounds(30, 150, 100, 40);
		contentPane.add(GroupIntroLabel);

		GroupNameField = new JTextField();
		GroupNameField.setBounds(140, 100, 260, 40);
		GroupNameField.setFont(new Font("MD개성체", Font.BOLD, 18));
		contentPane.add(GroupNameField);
		GroupNameField.setColumns(10);

		GroupIntroField = new JTextField();
		GroupIntroField.setColumns(10);
		GroupIntroField.setFont(new Font("MD개성체", Font.BOLD, 20));
		GroupIntroField.setBounds(140, 150, 260, 200);
		contentPane.add(GroupIntroField);

		JButton createGBtn = new JButton(Images.OKIcon);
		createGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 내용 저장
				if(GroupNameField.getText()!=null&&GroupIntroField.getText()!=null){
				makeGroup();
				// DB에서 group 데이터 최신화 하고 왼쪽 그룹이름테이블 다시 보여줌
				eGroup.refreshLeftNameList();
				dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "빈칸을 채워주세요.", "Time Format Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		createGBtn.setBounds(140,370, 110, 50);
		contentPane.add(createGBtn);

		JButton cancelBtn = new JButton(Images.CancelIcon);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelBtn.setBounds(290, 370, 110, 50);
		contentPane.add(cancelBtn);
	}

	private void makeGroup() {// GroupData Set
		GroupData group = new GroupData();
		
		group.setData(1, GroupNameField.getText());
		group.setData(2, GroupIntroField.getText());
		group.setData(3, DMonth_CalendarMain.getInstanace().getUser().getData(0));
		
		//새로운 그룹을 DB에 추가
		addGroupToDB(group.getSendSQLString());
		
		//새로운 그룹에 가입하고 새로고침
		eGroup.makeGroup(GroupNameField.getText());
	}

	// 그룹추가->DB
	private void addGroupToDB(String sql) {
		/* DB에 그룹 삽입하기 */
		String url = "http://113.198.84.67/Calendar3S/InsertGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
}
