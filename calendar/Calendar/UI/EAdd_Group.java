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

		JLabel PrefaceLabel = new JLabel("�׷� �����");
		PrefaceLabel.setFont(new Font("���� ���", Font.BOLD, 25));
		PrefaceLabel.setBounds(150, 10, 200, 50);
		contentPane.add(PrefaceLabel);

		JLabel GroupNameLabel = new JLabel("�׷��̸� :");
		GroupNameLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		GroupNameLabel.setBounds(30, 100, 100, 40);
		contentPane.add(GroupNameLabel);

		JLabel GroupIntroLabel = new JLabel("�׷�Ұ� :");
		GroupIntroLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		GroupIntroLabel.setBounds(30, 150, 100, 40);
		contentPane.add(GroupIntroLabel);

		GroupNameField = new JTextField();
		GroupNameField.setBounds(140, 100, 260, 40);
		GroupNameField.setFont(new Font("MD����ü", Font.BOLD, 18));
		contentPane.add(GroupNameField);
		GroupNameField.setColumns(10);

		GroupIntroField = new JTextField();
		GroupIntroField.setColumns(10);
		GroupIntroField.setFont(new Font("MD����ü", Font.BOLD, 20));
		GroupIntroField.setBounds(140, 150, 260, 200);
		contentPane.add(GroupIntroField);

		JButton createGBtn = new JButton(Images.OKIcon);
		createGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���� ����
				if(GroupNameField.getText()!=null&&GroupIntroField.getText()!=null){
				makeGroup();
				// DB���� group ������ �ֽ�ȭ �ϰ� ���� �׷��̸����̺� �ٽ� ������
				eGroup.refreshLeftNameList();
				dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "��ĭ�� ä���ּ���.", "Time Format Error", JOptionPane.ERROR_MESSAGE);
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
		
		//���ο� �׷��� DB�� �߰�
		addGroupToDB(group.getSendSQLString());
		
		//���ο� �׷쿡 �����ϰ� ���ΰ�ħ
		eGroup.makeGroup(GroupNameField.getText());
	}

	// �׷��߰�->DB
	private void addGroupToDB(String sql) {
		/* DB�� �׷� �����ϱ� */
		String url = "http://113.198.84.67/Calendar3S/InsertGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
}
