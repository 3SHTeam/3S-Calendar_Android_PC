package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Data.EventData;
import Data.TagData;

public class DMonth_dayPane extends JPanel implements MouseListener,ActionListener{
	
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
	
	private DMonth_CalendarMain calendarMain;
	ShowDayEvents showDayEvents;//상세보기 새창
	
	private EventData event;
	
	//해당 날짜의 데이터들만 받아와저장
	private Vector <EventData>dayScheduleVec=new Vector<EventData>();
	private ArrayList<TagData> tags = new ArrayList<TagData>();
	
	public Vector<EventData> getVec() {
		return dayScheduleVec;
	}
	public void setVec(Vector<EventData> vec) {
		dayScheduleVec = vec;
	}
	
	public DMonth_dayPane(DMonth_CalendarMain calendar, EFriend_GroupMain group) {
		
		super();
		GetGoogle gg = new GetGoogle();
		dayLabel=new JLabel();
		dayLabel.setHorizontalAlignment(JLabel.RIGHT);
		for(int i=0;i<4;i++){
			ScheduleLabel[i] = new JLabel();
		}
		this.calendarMain=calendar;
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
		dayInfoPanel.addMouseListener(this);
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
			if(month>0&&month<10){
				strMonth="0"+(month+1);
			}
			else{
				strMonth=String.valueOf(month);
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
						ScheduleLabel[i].setForeground(TagSet.hex2Rgb(tag.getData(2)));
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
						ScheduleLabel[i].setForeground(TagSet.hex2Rgb(tag.getData(2)));
						}
				}			
				}
			ScheduleLabel[3].setText("...");		
		}
		
		schedulePanel.repaint();
	}
	
	public String getDate(){
		return year+"/"+(month+1)+"/"+day;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!strDay.equals("")){
			if(e.getButton()==3){//오른쪽 마우스 클릭
				popup.show((Component)e.getSource(), e.getX(), e.getY());
			}
			else
			{
				showDayEvents=new ShowDayEvents(this, getDate(),dayScheduleVec);
				showDayEvents.setVisible(true);
			}
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}//하루 판넬
	
	/*popupmenu 클릭시*/
	@Override
	public void actionPerformed(ActionEvent e) {
		   if(e.getSource() == addSchedule)
		   {		   
			   DAddSchedule addSchedule=new DAddSchedule(calendarMain,this.getDate());
			   addSchedule.setVisible(true);
	        }
		   
	        else if(e.getSource() == freezeDate)
	        {
	        
	        } 


	}
	public JLabel[] getScheduleLabel() {
		return ScheduleLabel;
	}
	public void setScheduleLabel(JLabel[] scheduleLabel) {
		ScheduleLabel = scheduleLabel;
	}
	
}


/*dayPane의 내용상세내용 창*/
class ShowDayEvents extends JDialog{
	private JPanel contentPane;
	private JLabel DateLabel;
	private String date;
	private  JTable table;
	private JPopupMenu popup1 = new JPopupMenu();
	private JMenuItem modifySchedule;
	private JMenuItem deleteSchedule;
	// 1번 판넬 클릭하면 2번판넬(상세정보있는 페이지로)로 전환
	public ShowDayEvents(DMonth_dayPane dMonth_dayPane,String date, Vector<EventData> dayScheduleVec){
		
		this.date=date;
		setTitle("스케줄 상세보기");
	    setResizable(false);
	    setLocationRelativeTo(null);
		setBounds(100, 100, 450, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel DateLabel = new JLabel(date);
		DateLabel.setBounds(50, 28, 57, 15);
		contentPane.add(DateLabel);
		
		//schedule 시간과 내용들 정보
		JPanel panel = new JPanel(); 
		
		panel.setBounds(0, 53, 450, 479);
		contentPane.add(panel);
		
		  String colNames[] = { "시간", "스케줄명"};  // Jtable 헤더는 1차원 배열
		  DefaultTableModel model=new DefaultTableModel(colNames,0){ // 셀 수정 못하게 하는 부분
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		  model.setNumRows(0);
		  
		  for(int i=0;i<dayScheduleVec.size();i++){ 
			   Vector<String> VecInfo=new Vector<String>();//Vec에서 시간과 스케줄명 가져와 row 벡터에 저장 시키고 model.addRow
			   VecInfo.add(dayScheduleVec.get(i).getData(4)+"-"+dayScheduleVec.get(i).getData(5));//시작시간 데이터
			   VecInfo.add(dayScheduleVec.get(i).getData(2));//스켖
			   model.addRow(VecInfo);
			
		  }
		
		 
		   table = new JTable(model);   // 테이블 생성    
	      
	        JScrollPane scrollPane = new JScrollPane(table);  // 스크롤 기능 별도로 넣어줘야만 작동함
	        table.setShowVerticalLines(false);
	        panel.add(scrollPane, BorderLayout.CENTER); // contentPane에 테이블 적용
	 
	        
	        modifySchedule = new JMenuItem("일정 수정");
	        modifySchedule.addActionListener((ActionListener) this);
	        popup1.add(modifySchedule);
			
	        deleteSchedule = new JMenuItem("일정 삭제");
	        deleteSchedule.addActionListener((ActionListener) this);
			popup1.add(deleteSchedule);
	}


}
	
