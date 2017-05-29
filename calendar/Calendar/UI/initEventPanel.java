package Calendar.UI;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public abstract class initEventPanel extends JDialog{
	
	 	public JTextField ScheduleNameField;
	 	public JTextField locationField;
	 	public Font font=new Font("맑은 고딕", Font.BOLD, 18);
	 	public Font f=new Font("MD개성체",Font.BOLD,18);
	 	public JSpinner StartHour;
	 	public JSpinner StartMin;
	 	public JSpinner EndHour;
	 	public JSpinner EndMin;
	 	public JPanel timePanel ;
	 	public int now_hour;
	 	public int now_min;
	 	public abstract void timePane();
		public abstract void btnPane();
		public abstract void tagPane();
		
	public void init() {
		 
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 550);
		
		JLabel ScheduleNameLabel = new JLabel("스케줄명 :");
		ScheduleNameLabel.setFont(font);
		ScheduleNameLabel.setBounds(30, 90, 98, 48);
		add(ScheduleNameLabel);

		JLabel timeLabel = new JLabel("시간 :");
		timeLabel.setBounds(56, 169, 57, 48);
		timeLabel.setFont(font);
		add(timeLabel);

		JLabel tagLabel = new JLabel("태그 :");
		tagLabel.setBounds(56, 253, 57, 48);
		tagLabel.setFont(font);
		add(tagLabel);

		JLabel LocationLabel = new JLabel("장소 :");
		LocationLabel.setBounds(56, 338, 57, 48);
		LocationLabel.setFont(font);
		add(LocationLabel);
		
	}
	
 	public void sNamePane(){
 		ScheduleNameField = new JTextField();
		ScheduleNameField.setBounds(120, 90, 270, 48);
		ScheduleNameField.setFont(f);
		add(ScheduleNameField);
 	}
 		
	public void locationPane(){
		locationField= new JTextField();
		locationField.setBounds(120, 340, 270, 48);
		locationField.setFont(f);
		add(locationField);
	}

}
