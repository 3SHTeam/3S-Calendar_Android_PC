package GroupCalendar;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Calendar.DB.SendToDB;
import Calendar.Data.GroupData;
import Calendar.UI.Images;
import MonthCalendar.CalendarMain;

public class Add_Member extends JDialog{
	private JPanel contentPane;
	private GroupData groupData;
	private String Gid;
	private String Gname;
	private JLabel GroupIntroLabel;

	private JTextField emailField;
	private JTextField messageField;
	private GroupPane eGroup;
	
	public Add_Member(GroupPane eGroup, String Gid, String Gname){
		this.eGroup = eGroup;
		this.Gid = Gid;
		this.Gname = Gname;
		init();
	}
	
	private void init() {
		setTitle("["+Gname+"] Member Invitation");
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 500);
		Image img=Images.smainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    //super.paintComponents(g);
				    g.drawImage(img, 0, 0, 600, 500, null);
				   }
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Label = new JLabel("∏‚πˆ √ ¥Î¿Â");
		Label.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 25));
		Label.setBounds(200, 10, 200, 50);
		contentPane.add(Label);

		JLabel emailLabel = new JLabel("√ ¥Î«“ ¿Ã∏ﬁ¿œ:");
		emailLabel.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 18));
		emailLabel.setBounds(30, 100, 200, 40);
		contentPane.add(emailLabel);

		JLabel messageLabel = new JLabel("√ ¥Î ∏ﬁºº¡ˆ :");
		messageLabel.setFont(new Font("∏º¿∫ ∞ÌµÒ", Font.BOLD, 18));
		messageLabel.setBounds(30, 150, 200, 40);
		contentPane.add(messageLabel);

		emailField = new JTextField();
		emailField.setBounds(180, 100, 300, 40);
		emailField.setFont(new Font("MD∞≥º∫√º", Font.BOLD, 18));
		contentPane.add(emailField);
		emailField.setColumns(10);

		messageField = new JTextField();
		messageField.setColumns(10);
		messageField.setFont(new Font("MD∞≥º∫√º", Font.BOLD, 18));
		messageField.setBounds(180, 150, 300, 200);
		contentPane.add(messageField);

		JButton createGBtn = new JButton(Images.OKIcon);
		createGBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String receiver=emailField.getText();
				String message=messageField.getText();
				String type = "groupInvite";
				String sender;
				if(receiver!=null&&message!=null){
				sender = CalendarMain.getInstanace().getUser().getData(0);
				
				
				//sender,receiver,type,message,Gid;
				String sql = "'"+sender + "','" + receiver + "','" + type + "','" + message + "','" 
									+ Gid+"','"+Gname+"'";
				System.out.println(sql);
				addInviteToDB(sql);
				dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "∫Ûƒ≠¿ª √§øˆ¡÷ººø‰.", "Format Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		createGBtn.setBounds(180,370, 110, 50);
		contentPane.add(createGBtn);

		JButton cancelBtn = new JButton(Images.CancelIcon);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelBtn.setBounds(365, 370, 110, 50);
		contentPane.add(cancelBtn);
	}


	  private void addInviteToDB(String sql) {
	      String url = "http://113.198.84.67/Calendar3S/InsertMessage.php";

	      SendToDB stDB = new SendToDB(url, sql);
	      stDB.start();
	      try {
	         stDB.join();
	      } catch (InterruptedException e) {
	         System.out.println(e.toString());
	      }
	   }
	
	
	
	
}
