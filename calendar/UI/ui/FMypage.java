package ui;

import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Data.UserData;

public class FMypage extends JFrame {

	private JPanel contentPane;
	private UserData user;
	private JLabel googleID;
	private JLabel name;
	private JTextField nickname;
	private JLabel gender;
	private JLabel birth;
	private JLabel phone;
	private JTextField comment;

	
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(46, 58, 200, 323);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel picture = new JPanel();
		picture.setBackground(Color.GRAY);
		picture.setBounds(17, 40, 166, 172);
		panel.add(picture);
		
		JButton okButton = new JButton("È®ÀÎ");
		okButton.setBounds(38, 243, 125, 29);
		okButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();		
			}
		});
		panel.add(okButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(289, 58, 373, 323);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel googleIdLabel=new JLabel("¾ÆÀÌµð :");
		googleIdLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(googleIdLabel);
		
		googleID = new JLabel();
		panel_1.add(googleID);
		
		JLabel nameLabel = new JLabel("ÀÌ¸§ :");
		nameLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(nameLabel);
		
		name = new JLabel();
		panel_1.add(name);
	
		JLabel nickLabel = new JLabel("´Ð³×ÀÓ :");
		nickLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(nickLabel);
		
		nickname = new JTextField();
		panel_1.add(nickname);
		
		JLabel genderLabel = new JLabel("¼ºº° :");
		genderLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(genderLabel);
		
		gender = new JLabel();
		gender.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(gender);
		
		JLabel birthLabel = new JLabel("»ýÀÏ :");
		birthLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(birthLabel);
		
		birth = new JLabel();
		panel_1.add(birth);
		
		JLabel phoneLabel = new JLabel("ÀüÈ­¹øÈ£ :");
		phoneLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(phoneLabel);
		
		phone = new JLabel();
		panel_1.add(phone);
		
		JLabel commentLabel = new JLabel("ÇÑ¸¶µð :");
		commentLabel.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		panel_1.add(commentLabel);
		
		comment = new JTextField();
		panel_1.add(comment);
	}

	public void setUser(UserData user) {
		this.user = user;
	}
}