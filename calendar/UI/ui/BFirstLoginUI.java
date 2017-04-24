package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BFirstLoginUI extends JFrame {

   private JPanel contentPane;
   private JTextField GoogleidField;
   private JPasswordField GooglepwField;
   private JTextField NameField;
   private JTextField GenderField;
   private JTextField BirthField;
   private JTextField NicknameField;
   private JTextField NumberField;
   private JTextArea CommentField;
   private JoinData joinData;//Join할때 받을 변수들

   /**
    * Create the frame.
    */
   public BFirstLoginUI() {
	  
	     setTitle("Join");
	      setResizable(false);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      setBounds(100, 100, 700, 800);
	      
	      setLocationRelativeTo(null);
	      contentPane = new JPanel();
	      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	      setContentPane(contentPane);
	      contentPane.setLayout(null);
	            
	      JLabel lblNewLabel = new JLabel("처음 오셨군요?");
	      lblNewLabel.setFont(new Font("HY산B", Font.BOLD, 40));
	      lblNewLabel.setBounds(201, 52, 293, 49);
	      contentPane.add(lblNewLabel);
	      
	      JLabel lblNewLabel_1 = new JLabel("아래의 정보를 기입해주세요.");
	      lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
	      lblNewLabel_1.setBounds(243, 131, 201, 20);
	      contentPane.add(lblNewLabel_1);
	      
	      JLabel GoogleidLabel = new JLabel("구글 아이디 : ");
	      GoogleidLabel.setBounds(201, 190, 88, 15);
	      contentPane.add(GoogleidLabel);
	      
	      JLabel GooglepwLabel = new JLabel("구글 패스워드 : ");
	      GooglepwLabel.setBounds(201, 240, 104, 15);
	      contentPane.add(GooglepwLabel);
	      
	      JLabel NameLabel = new JLabel("이름 :");
	      NameLabel.setBounds(201, 290, 57, 15);
	      contentPane.add(NameLabel);
	      
	      JLabel GenderLabel = new JLabel("성별 : ");
	      GenderLabel.setBounds(201, 390, 57, 15);
	      contentPane.add(GenderLabel);
	      
	      JLabel BirthLabel = new JLabel("생일 : ");
	      BirthLabel.setBounds(201, 340, 57, 15);
	      contentPane.add(BirthLabel);
	            
	      JLabel NicknameLabel = new JLabel("닉네임 :");
	      NicknameLabel.setBounds(201, 440, 57, 15);
	      contentPane.add(NicknameLabel);
	      
	      JLabel NumberLabel =new JLabel("전화번호 :");
	      NumberLabel.setBounds(201, 490, 67, 15);
	      contentPane.add(NumberLabel);
	      
	      JLabel CommentLabel = new JLabel("한마디 :");
	      CommentLabel.setBounds(201, 540, 48, 15);
	      contentPane.add(CommentLabel);
	      
	      GoogleidField= new JTextField();
	      GoogleidField.setBounds(317, 190, 158, 21);
	      contentPane.add(GoogleidField);
	      GoogleidField.setColumns(10);
	      
	      GooglepwField=new JPasswordField();
	      GooglepwField.setBounds(317, 240, 158, 21);
	      contentPane.add(GooglepwField);
	      GooglepwField.setColumns(10);
	      
		  NameField= new JTextField();
		  NameField.setBounds(317, 290, 158, 21);
	      contentPane.add(NameField);
	      NameField.setColumns(10);
	
		  
		  GenderField= new JTextField();
		  GenderField.setBounds(317, 340, 158, 21);
	      contentPane.add(GenderField);
	      GenderField.setColumns(10);
		  
		  
		  BirthField= new JTextField();
		  BirthField.setBounds(317, 390, 158, 21);
	      contentPane.add(BirthField);
	      BirthField.setColumns(10);
	      
	      NicknameField = new JTextField();
	      NicknameField.setBounds(317, 440, 158, 21);
	      contentPane.add(NicknameField);
	      NicknameField.setColumns(10);
	      
	      NumberField = new JTextField();
	      NumberField.setColumns(10);
	      NumberField.setBounds(317, 490, 158, 21);
	      contentPane.add(NumberField);
	      
	      CommentField = new JTextArea();
	      
	      CommentField.setLineWrap(true); //한줄이 너무 길면 자동으로 개행할지 설정
	      CommentField.setText("(반갑습니다.)");
	      CommentField.setBounds(317, 530, 158, 60);
	      contentPane.add(CommentField);
	      
	      JButton startBtn = new JButton("시작하기");
	      startBtn.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            if(e.getSource()==startBtn){
	            	
	            	setJoinData();
	            	 CTreeView treeView=(CTreeView)CTreeView.getInstanace();// CTreeView(); 
	                  dispose();
	                  treeView.setVisible(true);
	            }
	         }

		
	      });
	      startBtn.setBounds(320, 610, 97, 23);
	      contentPane.add(startBtn);
	   }
   public void setJoinData(){
	   joinData=new JoinData();   
	   joinData.setGoogleId(GoogleidField.getText());
	   joinData.setGooglePw(GooglepwField.getText());
	   joinData.setName(NameField.getText());
	   joinData.setGender(GenderField.getText());//콤보나 탭이용
	   //joinData.setBirth()BirthField.getText());int로형변환
	   joinData.setNickname(NicknameField.getText());
	   //joinData.setPhoneNum(NumberField.getText());
	   joinData.setComment(CommentField.getText());
	   
   }
  
}
