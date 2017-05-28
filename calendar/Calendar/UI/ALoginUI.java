package Calendar.UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.UserData;

import java.awt.*;
import java.awt.event.*;

public class ALoginUI extends JFrame {
	private DMonth_CalendarMain calendar;
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
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblLogo = new JLabel(Images.calendar_mainIcon);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(74, 15, 535, 367);
		contentPane.add(lblLogo);
		
		JLabel lbLogin=new JLabel(Images.calendar_LoginIcon);
		lbLogin.setBounds(74, 315, 535, 230);
		contentPane.add(lbLogin);
		
		Googleid = new JTextField("boyoon456123@gmail.com");
		Googleid.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Googleid.setBounds(235, 371, 259, 46);
		contentPane.add(Googleid);
		Googleid.setColumns(10);

		Googlepw = new JPasswordField("111111");
		Googlepw.setBounds(235, 441, 260, 46);
		contentPane.add(Googlepw);
		
		JLabel label = new JLabel("구글 아이디가 없으신가요?");
		label.setForeground(Color.BLUE);
		label.setBounds(250, 457, 269, 21);
		contentPane.add(label);
		
		OKBtn = new JButton(Images.LoginIcon);
		OKBtn.setBounds(430, 60, 63, 110);		
		OKBtn.setFont(new Font("Axure Handwriting", Font.BOLD, 16));
		OKBtn.requestFocus();
		OKBtn.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == OKBtn) {
					// BFirstLoginUI login=new BFirstLoginUI();
					if (setLoginData()) {// Login data확인 및 저장
						dispose();
						calendar = (DMonth_CalendarMain) DMonth_CalendarMain.getInstanace();
						calendar.setVisible(true);
					}
					else{
						Googlepw.setText("");
					}

				}
			}
		});
		lbLogin.add(OKBtn);

	}

	public boolean setLoginData() {
		String id = Googleid.getText();
		String pw = Googlepw.getText();

		System.out.println("id: " + id + " password: " + pw);

		String url = "http://113.198.84.67/Calendar3S/SelectMyProfile.php";
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
			JOptionPane.showMessageDialog(null, "로그인을 실패했습니다.", "Login Failed", JOptionPane.ERROR_MESSAGE);
			Googlepw.setText("");		
			return false;
		} else {

			this.setUser(jm.getUser());
			return true;
		}
	}


	public static UserData getUser() {
		return user;
	}

	public static void setUser(UserData user) {
		ALoginUI.user = user;
	}
}