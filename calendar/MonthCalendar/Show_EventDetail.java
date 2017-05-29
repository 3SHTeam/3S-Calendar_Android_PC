package MonthCalendar;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import Calendar.DB.SendToFirebase;
import Calendar.Data.EventData;
import Calendar.Data.TagData;
import Calendar.UI.Images;
import Calendar.UI.initEventPanel;

public class Show_EventDetail extends initEventPanel {
	private JPanel contentPane;
	private EventData event = new EventData();
	private JDateChooser dateChooser;
	private ArrayList<TagData> tags = new ArrayList<TagData>();
	
	private JSpinner StartHour;
	private JSpinner StartMin;
	private JSpinner EndHour;
	private JSpinner EndMin;

	private int year;
	private String Sid, SMaster, SName, Place, StartTime, EndTime, TagId;
	private JComboBox<String> tagBox;
	
	private Font font=new Font("���� ���", Font.BOLD, 25);


	public Show_EventDetail(EventData event, ArrayList<TagData> tagArr) throws ParseException {
		setTitle("���� ���� �� �󼼺���");
		this.event = event;
		tags = tagArr;
		
		year=Integer.valueOf(event.getYear());
		setResizable(false);
		setLocationRelativeTo(null);

		ImageIcon main = Images.smainIcon;
		Image img = main.getImage();
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, 450, 560, null);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		String dateStr = event.getYear()+event.getMonth()+event.getDay();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		Date to = transFormat.parse(dateStr);
		
		dateChooser = new JDateChooser();
		dateChooser.setDate(to);
		dateChooser.setBounds(30, 20, 250, 40);
		dateChooser.setDateFormatString("yyyy/MM/dd");
		
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setOpaque(false);
		editor.setEditable(false);
		dateChooser.setFont(new Font("MD����ü",Font.BOLD,20));
		contentPane.add(dateChooser);
		
		SName = event.getData(1);
		Place = event.getData(3);

		super.init();
		super.sNamePane();
		ScheduleNameField.setText(SName);
		super.locationPane();
		locationField.setText(Place);
		timePane();
		tagPane();
		btnPane();

		super.ScheduleNameField.setText(event.getData(2));
		super.locationField.setText(event.getData(3));

	}

	@Override
	public void timePane() {
		String startHour = event.getData(4).substring(8, 10);
		String startMin = event.getData(4).substring(10, 12);
		String endHour = event.getData(5).substring(8, 10);
		String endMin = event.getData(5).substring(10, 12);

		
			timePanel = new JPanel();
			timePanel.setOpaque(false);
			timePanel.setBounds(117, 175, 280, 39);
			add(timePanel);
			timePanel.setLayout(null);
			
			StartHour= new JSpinner();
			StartHour.setBounds(0, 4, 47, 28);
			timePanel.add(StartHour);
			StartHour.setModel(new SpinnerNumberModel(Integer.parseInt(startHour), 0, 23, 1));
			
			JLabel label=new JLabel("��");
			label.setBounds(46, 8, 16, 22);
			label.setFont( new Font("���", Font.BOLD, 15));
			timePanel.add(label);

			StartMin=new JSpinner();
			StartMin.setBounds(63, 4, 47, 28);
			timePanel.add(StartMin);
			StartMin.setModel(new SpinnerNumberModel(Integer.parseInt(startMin), 0, 60, 1));
			
			JLabel label1 = new JLabel("��");
			label1.setBounds(109, 8, 16, 22);
			timePanel.add(label1);
			label1.setFont(new Font("���", Font.BOLD, 15));
					
			JLabel label2 = new JLabel("-",JLabel.CENTER);
			label2.setBounds(127, 3, 7, 29);
			label2.setFont(new Font("���", Font.BOLD, 20));
			timePanel.add(label2);

			
			EndHour= new JSpinner();
			EndHour.setBounds(140, 4, 47, 28);
			EndHour.setModel(new SpinnerNumberModel(Integer.parseInt(endHour), 0, 24, 1));
		    timePanel.add(EndHour);
									
							
			JLabel label3 = new JLabel("��");
			label3.setBounds(187, 8, 16, 22);
			label3.setFont(new Font("���", Font.BOLD, 15));
			timePanel.add(label3);

			EndMin=new JSpinner();
			EndMin.setBounds(202, 4, 47, 28);
			EndMin.setModel(new SpinnerNumberModel(Integer.parseInt(endMin), 0, 60, 1));
			timePanel.add(EndMin);
											
			JLabel label4 = new JLabel("��");
			label4.setBounds(252, 8, 16, 22);
			label4.setFont(new Font("���", Font.BOLD, 15));
			timePanel.add(label4);
			
	}

	@Override
	public void tagPane() {
		tagBox = new JComboBox<String>();
		tagBox.setFont(f);
		String[] tagNames = new String[tags.size()];

		for (int i = 0; i < tags.size(); i++) {
			tagNames[i] = tags.get(i).getData(1);
			tagBox.addItem(tagNames[i]);
		}
		tagBox.setBounds(120, 255, 270, 42);
		contentPane.add(tagBox);
	}
	
	@Override
	public void btnPane() {

		JButton deleteBtn = new JButton(Images.DeleteIcon);
		deleteBtn.setBounds(25, 423, 100, 45);
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "Delete Schedule",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					JSONObject message = new JSONObject();
					try {
						message.put("type", "deleteSchedule");
						message.put("Sid", event.getData(0));
						message.put("SMaster", event.getData(1));
						message.put("Tagid", event.getData(6));
						message.put("GoogleSid", event.getData(7));
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
					System.out.println(message.toString());
					sendEventToAndroid(message.toString());

					// DB���� �̺�Ʈ ������ �ֽ�ȭ �ϰ� Ķ������ ���ΰ�ħ
					CalendarMain.getInstanace().refreshSchedule();
					dispose();
				}
			}

		});
		contentPane.add(deleteBtn);

		JButton editBtn = new JButton(Images.UpdateIcon);
		editBtn.setBounds(170, 423, 100, 45);
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EditEvent();// ������ ������ �Է¹��� ���� �޾ƿ´�.
				JSONObject message = new JSONObject();
				try {
					message.put("type", "updateSchedule");
					message.put("Sid", Sid);
					message.put("SMaster", SMaster);
					message.put("Sname", SName);
					message.put("Place", Place);
					message.put("StartTime", StartTime);
					message.put("EndTime", EndTime);
					message.put("Tagid", TagId);
					message.put("GoogleSid", event.getData(7));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				System.out.println("message : " + message.toString());
				sendEventToAndroid(message.toString());
 
				// DB���� �̺�Ʈ ������ �ֽ�ȭ �ϰ� Ķ������ ���ΰ�ħ
				CalendarMain.getInstanace().refreshSchedule();
				dispose();
			}
		});
		contentPane.add(editBtn);

		JButton closeBtn = new JButton(Images.CancelIcon);
		closeBtn.setBounds(315, 423, 100, 45);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(closeBtn);
	}

	private void EditEvent() {
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String date=dateFormat.format(dateChooser.getDate());

		int starthour=(Integer)StartHour.getValue();
		int startmin=(Integer)StartMin.getValue();
		int endhour=(Integer)EndHour.getValue();
		int endmin=(Integer)EndMin.getValue();
		
		Sid  = event.getData(0);
		SMaster=event.getData(1);
		SName = ScheduleNameField.getText();
		Place = locationField.getText();
		//StartTime = date+ StartHour.getText() + StartMin.getText();
		//EndTime = date+ EndHour.getText() + EndMin.getText();

		String tagname = (String) tagBox.getSelectedItem();

		for (int i = 0; i < tags.size(); i++) {
			if (tagname.equals(tags.get(i).getData(1))) {
				TagId = tags.get(i).getData(0);
			}
		}
	}
	

	private void sendEventToAndroid(String sql){
	   	 /* Android�� �̺�Ʈ ���� ������ */
				String url = "http://113.198.84.67/Calendar3S/FCMsendMyself.php";
				String token = CalendarMain.getInstanace().getUser().getData(8);
				System.out.println("token : " + token);
				
				//��ū�� �޼����� ���̾�̽��� ����
				SendToFirebase stFB = new SendToFirebase(url, sql, token);
				stFB.start();// DB���� ������ ����
				try {
					stFB.join();// DB������ �Ϸ�ɶ����� ���
				} catch (InterruptedException e) {
					System.out.println(e.toString());
				} 			
				System.out.println("result : " + stFB.getResult());
	    }
}
