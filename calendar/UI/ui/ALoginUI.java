package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DB.JsonMaster;
import DB.SendToDB;
import Data.UserData;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import javax.swing.JPasswordField;

public class ALoginUI extends JFrame {
	private EFriend_GroupUI fgUI = new EFriend_GroupUI();
	private DMonth_CalendarView calendar;
	private JPanel contentPane;
	private JTextField Googleid;
	private JButton OKBtn;
	private JPasswordField Googlepw;

	private static SendToDB stDB;
	private static JsonMaster jm;
	private static UserData user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ALoginUI frame = new ALoginUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ALoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setResizable(false);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);

		JLabel lblGoogle = new JLabel("Google");
		lblGoogle.setFont(new Font("Agency FB", Font.BOLD, 51));
		lblGoogle.setBounds(290, 138, 122, 67);
		contentPane.add(lblGoogle);

		Googleid = new JTextField();
		Googleid.setBounds(285, 279, 145, 21);
		contentPane.add(Googleid);
		Googleid.setColumns(10);

		JLabel IDLabel = new JLabel("ID");
		IDLabel.setFont(new Font("굴림", Font.BOLD, 13));
		IDLabel.setBounds(198, 280, 41, 18);
		contentPane.add(IDLabel);

		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setFont(new Font("굴림", Font.BOLD, 13));
		PasswordLabel.setBounds(177, 321, 85, 15);
		contentPane.add(PasswordLabel);

		Googlepw = new JPasswordField();
		Googlepw.setBounds(285, 318, 145, 21);
		contentPane.add(Googlepw);

		JLabel label = new JLabel("구글 아이디가 없으신가요?");
		label.setForeground(Color.BLUE);
		label.setBounds(285, 396, 159, 15);
		contentPane.add(label);

		OKBtn = new JButton("OK");
		OKBtn.setBounds(453, 318, 63, 21);
		OKBtn.addActionListener(new ActionListener() {
			private Object user;

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == OKBtn) {
					// BFirstLoginUI login=new BFirstLoginUI();
					if (setLoginData()) {// Login data확인 및 저장
						dispose();
						calendar = (DMonth_CalendarView) DMonth_CalendarView.getInstanace();
						calendar.setGroup(fgUI); // DMonth_CalendarView에
													// EFriend_GroupUI 보내기(그룹 정보
													// 공유)
						fgUI.setDMonth_CalendarView(calendar); // EFriend_GroupUI에
																// DMonth_CalendarView
																// 보내기(스캐줄 공유)
						calendar.setVisible(true);
						// login.setVisible(true);
					}
					else{
						//로그인창 유지시키기
					}

				}
			}
		});
		contentPane.add(OKBtn);

	}

	public boolean setLoginData() {
		String id = Googleid.getText();
		String pw = Googlepw.getText();

		System.out.println("id: " + id + " password: " + pw);

		String url = "http://113.198.84.66/Calendar3S/SelectMyProfile.php";
		String message = "where Googleid = '" + id + "' and Googlepw = '" + pw + "'";

		stDB = new SendToDB(url, message);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		String result = stDB.getResult();// Json형식의 String값 가져옴
		System.out.println(result);

		jm = new JsonMaster();
		jm.onPostExecute("SelectMyProfile", result);

		if (jm.getUser() == null) {
			// 로그인 실패
			// 로그인 실패 창이 뜬다.
			// 비밀번호 텍스트 필드만 초기화
			return false;
		} else {
			// 로그인 성공
			// 로그인 성공 창이 뜬다.
			// 달력으로 이동하기
			this.setUser(jm.getUser());
			return true;
		}
	}

	public EFriend_GroupUI geteFGUi() {
		return fgUI;
	}

	public static UserData getUser() {
		return user;
	}

	public static void setUser(UserData user) {
		ALoginUI.user = user;
	}
}