package ui;

import java.awt.*;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class DWeek_dayPane extends JPanel implements MouseListener{
	DWeek_CalendarView calendar;	
	private JPanel dayPanel;//day판넬
	private JLabel dayLabel=new JLabel();//day라벨
	private String strDay;
	private int year,month,day;
	private JPanel []timePanel; //하루 시간당 판넬
    private ArrayList<String> weekArr;//7일 날짜 배열
   String []DAYS_OF_WEEK={"일","월","화","수","목","금","토"};
    
	public DWeek_dayPane(DWeek_CalendarView dWeek_CalendarView,String strDay) {
		super();
		this.calendar=dWeek_CalendarView;
		this.strDay=strDay;
		this.setOpaque(true);
		dayPanel=new JPanel();
		dayPanel.setLayout(new GridLayout(25,1));
		dayPanel.setBackground(Color.PINK);
		dayLabel.setText(strDay);
		dayPanel.add(dayLabel);
		
		if(strDay.equals("시간")){
			this.setOpaque(false);
			this.setBackground(Color.LIGHT_GRAY);
		JLabel []timeLabel=new JLabel[24];
		String time[]={"00:00-01:00","01:00-02:00","02:00-03:00","03:00-04:00","04:00-05:00","05:00-06:00","06:00-07:00","07:00-08:00","08:00-09:00","09:00-10:00","10:00-11:00","11:00-12:00","12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00","16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00","20:00-21:00","21:00-22:00","22:00-23:00","23:00-24:00"};
		for(int i=0;i<timeLabel.length;i++){
			timeLabel[i] = new JLabel(time[i]);
			dayPanel.add(timeLabel[i]);
		}
		}
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1)
					.addComponent(dayPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
			
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(1)
					.addComponent(dayPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(1))
		);
		setLayout(groupLayout);
		
	
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) { 
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void initInfo(){
		dayLabel.setText("");
		//Aday.add(dayLabel);
	}
	/*각각의 day판넬에 날짜 지정하기*/
	public void setValue(int year,int month,String strDay, int position){
		int num=position%7;			
		this.year=year;
		this.month=month;
		this.strDay=strDay;
	
		
		//토요일 파란 일요일 빨간 설정
		if(num==1)
			dayLabel.setForeground(Color.RED);
		else if(num==0)
			dayLabel.setForeground(Color.BLUE);
		
		dayLabel.setText(strDay);
		dayPanel.add(dayLabel);

	}
	

		
}