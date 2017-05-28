package MonthCalendar;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Calendar.Data.EventData;
import Calendar.Data.TagData;
import Calendar.UI.TagColorSet;
import GroupCalendar.GroupMain;


public class CalendarDayPane extends JPanel implements ActionListener{
	
	private JPanel dayInfoPanel;//하루 전체 정보판넬 
	private JPanel dayNumPanel;
	private JPanel schedulePanel;
	
	private JLabel dayLabel;//day라벨
	private String strDay;
	private String strMonth;
	private JLabel[] ScheduleLabel = new JLabel[4];
	
	private String scheduleName;
	
	private int year,month,day;
	private JPopupMenu popup = new JPopupMenu();//오른쪽 마우스 클릭시 popupmenu
	private JMenuItem addSchedule;
	private JMenuItem modifySchedule;
	private JMenuItem freezeDate;
	private JMenuItem deleteSchedule;
	
	int position;
	
	private CalendarMain calendar;
	Show_DayEvents showDayEvents;//상세보기 새창
	
	private EventData event;
	
	//해당 날짜의 데이터들만 받아와저장
	private Vector <EventData>dayScheduleVec=new Vector<EventData>();
	private ArrayList<TagData> tags = new ArrayList<TagData>();
	
	public ArrayList<TagData> getTags() {return tags;}
	public Vector<EventData> getVec() {return dayScheduleVec;}
	public void setVec(Vector<EventData> vec) {dayScheduleVec = vec;}
	
	public CalendarDayPane(CalendarMain calendar,GroupMain group) {
		//GetGoogle gg = new GetGoogle();
		dayLabel=new JLabel();
		dayLabel.setFont(new Font("맑은 고딕",Font.BOLD,16));
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);
		for(int i=0;i<4;i++){
			ScheduleLabel[i] = new JLabel();
			ScheduleLabel[i].setFont(new Font("맑은 고딕",Font.BOLD,16));
		}
		this.calendar=calendar;
		tags=calendar.getTags();
		this.setOpaque(false);
		
		dayInfoPanel=new JPanel();
		dayNumPanel=new JPanel();
		dayNumPanel.setOpaque(false);
		
		schedulePanel=new JPanel();
		schedulePanel.setLayout(new GridLayout(4,1));
		schedulePanel.setOpaque(false);
		for(int i = 0 ;i<4;i++){
			schedulePanel.add(ScheduleLabel[i]);
		}
		
		dayInfoPanel.setLayout(new BorderLayout());
		dayInfoPanel.add(dayNumPanel,BorderLayout.NORTH);
		dayInfoPanel.add(schedulePanel, BorderLayout.CENTER);
		
		dayInfoPanel.setBackground(Color.WHITE);
		dayInfoPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!strDay.equals("")){
					if(e.getButton()==3){//오른쪽 마우스 클릭
						popup.show((Component)e.getSource(), e.getX(), e.getY());
					}
					else
					{
						showDayEvents=new Show_DayEvents(getDate(),dayScheduleVec);
						showDayEvents.setVisible(true);
					}
				}		
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(1)
					.addComponent(dayInfoPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
					.addGap(1))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(1)
					.addComponent(dayInfoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(1))
		);
		setLayout(groupLayout);
		
	
	
		
		addSchedule = new JMenuItem("일정 추가");
		addSchedule.addActionListener(this);
		popup.add(addSchedule);

		freezeDate = new JMenuItem("일정 동결");
		popup.add(freezeDate);
		freezeDate.addActionListener(this);
		
	}
	public void initInfo(){
		dayLabel.setText("");
		
		for(int i=0;i<ScheduleLabel.length;i++){
			ScheduleLabel[i].setHorizontalAlignment(JLabel.CENTER);
			ScheduleLabel[i].setText("");
			ScheduleLabel[i].setBackground(Color.WHITE);
		}
		
		dayScheduleVec.clear();
	}
	/*각각의 day판넬에 날짜 지정하기*/
	public void setValue(int year,int month,int day, int position){
		int num=position%7;
		this.position=position;
			
		this.year=year;
		this.month=month;
		this.day=day;
		
		//날짜 설정
		if(day==0){ 
			strDay="";
		}
		else{
			if(month>=0&&month<9){
				strMonth="0"+(month+1);
			}
			else{
				strMonth=String.valueOf(month+1);
			}

			if(day>0&&day<10){
				strDay="0"+day;
			}
			else{
				strDay=String.valueOf(day);
			}
		
		}
		dayLabel.setText(strDay);


		//토요일 파란 일요일 빨간 설정
		if(num==0)
			dayLabel.setForeground(Color.RED);
		else if(num==6)
			dayLabel.setForeground(Color.BLUE);
		
		dayNumPanel.add(dayLabel);
		
	}
	
	
	public void setSchedule(){// 추가된 스케줄
	//맨처음 array기존에 있는 데이터는 set JLabel에 4개전까지 크기[]
		if (dayScheduleVec.size() < 4) {// 스케줄이 4이하일때
			for (int i = 0; i < dayScheduleVec.size(); i++) {
				for (int j = 0; j < tags.size(); j++) {
					event = dayScheduleVec.get(i);
					TagData tag = tags.get(j);
				ScheduleLabel[i].setText(dayScheduleVec.get(i).getData(2));			
										
					if (event.getData(6).equals(tag.getData(0))) {
						ScheduleLabel[i].setBackground(TagColorSet.hex2Rgb(tag.getData(2)));
						ScheduleLabel[i].setOpaque(true);
						}
				}
			}
		}
		else{
			for(int i=0;i<3;i++){
				for (int j = 0; j < tags.size(); j++) {	
					event = dayScheduleVec.get(i);
					TagData tag = tags.get(j);
					ScheduleLabel[i].setText(dayScheduleVec.get(i).getData(2));//스케줄명	
					if (event.getData(6).equals(tag.getData(0))) {
						ScheduleLabel[i].setBackground(TagColorSet.hex2Rgb(tag.getData(2)));
						}
				}			
				}
			ScheduleLabel[3].setText("...");		
		}
		
		schedulePanel.repaint();
	}
	
	public String getDate(){
		return year+"/"+strMonth+"/"+strDay;
	}

	/*popupmenu 클릭시*/
	@Override
	public void actionPerformed(ActionEvent e) {
		   if(e.getSource() == addSchedule)
		   {		   
			   Add_Event addSchedule=new Add_Event(this.getDate());
			   addSchedule.setVisible(true);
	        }
	}
	public JLabel[] getScheduleLabel() {
		return ScheduleLabel;
	}
	public void setScheduleLabel(JLabel[] scheduleLabel) {
		ScheduleLabel = scheduleLabel;
	}
	
}


