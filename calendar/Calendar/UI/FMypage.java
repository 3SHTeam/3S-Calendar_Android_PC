package Calendar.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Calendar.Data.UserData;

public class FMypage extends JDialog {

	private JPanel contentPane;
	private Calendar.Data.UserData user;
	private JLabel googleID;
	private JLabel name;
	private JTextField nickname;
	private JLabel gender;
	private JLabel birth;
	private JLabel phone;
	private JTextField comment;
	private Font font=new Font("맑은 고딕",Font.BOLD,18);
	private Font f=new Font("MD개성체",Font.BOLD,18);
	private Dimension d=new Dimension(273,45);
	
	public FMypage(UserData user) {
		setLocationRelativeTo(null);
		this.setUser(user);
		
		init();
		googleID.setText(user.getData(0));
		name.setText(user.getData(1));
		nickname.setText(user.getData(3));
		gender.setText(user.getData(2));
		birth.setText(user.getData(4));
		phone.setText(user.getData(5));
		comment.setText(user.getData(6));

	}

	public FMypage() {
		init();
	}

	private void init() {
		setTitle("MyPage");
		setBounds(100, 100, 741, 490);
		Image img=Images.smainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    g.drawImage(img, 0, 0, 741, 490, null);
				   }
				  };
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);


		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(46, 58, 200, 323);
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel picture = new JPanel();
		picture.setBackground(Color.GRAY);
		picture.setBounds(17, 40, 166, 172);
		panel.add(picture);
		
		JButton okButton = new JButton(Images.OKIcon);
		okButton.setBounds(38, 245, 110,50);
		okButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();		
			}
		});
		panel.add(okButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(289, 58, 373, 315);
		panel_1.setOpaque(false);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		JLabel googleIdLabel=new JLabel("아이디 :");
		googleIdLabel.setFont(font);
		panel_1.add(googleIdLabel);
		
		googleID = new JLabel();
		googleID.setFont(f);
		googleID.setSize(d);
		panel_1.add(googleID);
		
		JLabel nameLabel = new JLabel("이름 :");
		nameLabel.setFont(font);
		panel_1.add(nameLabel);
		
		name = new JLabel();
		name.setFont(f);
		name.setSize(d);
		panel_1.add(name);
	
		JLabel nickLabel = new JLabel("닉네임 :");
		nickLabel.setFont(font);
		panel_1.add(nickLabel);
		
		nickname = new JTextField();
		nickname.setFont(f);
		nickname.setSize(d);
		panel_1.add(nickname);
		
		JLabel genderLabel = new JLabel("성별 :");
		genderLabel.setFont(font);
		panel_1.add(genderLabel);
		
		gender = new JLabel();
		gender.setFont(f);
		gender.setSize(d);
		panel_1.add(gender);
		
		JLabel birthLabel = new JLabel("생일 :");
		birthLabel.setFont(font);
		panel_1.add(birthLabel);
		
		birth = new JLabel();
		birth.setFont(f);
		birth.setSize(d);
		panel_1.add(birth);
		
		JLabel phoneLabel = new JLabel("전화번호 :");
		phoneLabel.setFont(font);
		panel_1.add(phoneLabel);
		
		phone = new JLabel();
		phone.setFont(f);
		phone.setSize(d);
		panel_1.add(phone);
		
		JLabel commentLabel = new JLabel("한마디 :");
		commentLabel.setFont(font);
		commentLabel.setSize(d);
		panel_1.add(commentLabel);
		
		comment = new JTextField();
		comment.setFont(f);
		comment.setSize(d);
		panel_1.add(comment);
	}

	public void setUser(UserData user) {
		this.user = user;
	}
}