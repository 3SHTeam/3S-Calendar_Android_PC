package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DMonth_dayPane extends JPanel implements MouseListener,ActionListener{
	private JPanel dayInfoPanel;//하루 전체 정보판넬 
	private JPanel dayNumPanel;
	private JPanel schedulePanel;
	
	private JLabel dayLabel=new JLabel();//day라벨
	private String strDay;
	
	private JLabel[] ScheduleLabel=new JLabel[4];
	
	private String scheduleName;
	
	private int year,month,day;
	private JPopupMenu popup = new JPopupMenu();//오른쪽 마우스 클릭시 popupmenu
	private JMenuItem addSchedule;
	private JMenuItem modifySchedule;
	private JMenuItem freezeDate;
	private JMenuItem deleteSchedule;
	
	private DMonth_CalendarView calendar;
	ShowDetail showDetail;//상세보기 새창
	
	//해당 날짜의 데이터들만 받아와저장
	private Vector <ScheduleData>Vec=new Vector<ScheduleData>();
	

	
	public Vector<ScheduleData> getVec() {
		return Vec;
	}
	public void setVec(Vector<ScheduleData> vec) {
		Vec = vec;
	}
	
	public DMonth_dayPane(DMonth_CalendarView calendar, EFriend_GroupUI group) {
		
		super();
		for(int i=0;i<4;i++){
			ScheduleLabel[i] = new JLabel();
			}
		this.calendar=calendar;
		this.setOpaque(false);
		
		dayInfoPanel=new JPanel();
		dayNumPanel=new JPanel();
		dayNumPanel.setOpaque(false);
		
		schedulePanel=new JPanel();
		schedulePanel.setLayout(new GridLayout(4,1));
		schedulePanel.setOpaque(false);
		
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
		
		
		modifySchedule = new JMenuItem("일정 수정");
		popup.add(modifySchedule);
		modifySchedule.addActionListener(this);
		
		freezeDate = new JMenuItem("일정 동결");
		popup.add(freezeDate);
		freezeDate.addActionListener(this);
		
		deleteSchedule = new JMenuItem("일정 삭제");
		popup.add(deleteSchedule);
		deleteSchedule.addActionListener(this);

//		for(int i=0;i<scheduleArr.size();i++){
//			scheduleName[i]=scheduleArr.get(i).getScheduleName();
//			ScheduleLabel[i].setText(scheduleName[i]);
//			AdayPanel.add(ScheduleLabel[i]);
//		}
	//	AdayPanel.add(ScheduleLabel);
	}
	public void initInfo(){
		dayLabel.setText("");

		for(int i=0;i<ScheduleLabel.length;i++){
		ScheduleLabel[i] . setHorizontalAlignment(JLabel.CENTER);
		ScheduleLabel[i].setText("");
		}
	
	}
	/*각각의 day판넬에 날짜 지정하기*/
	public void setValue(int year,int month,int day, int position){
		int num=position%7;
			
		this.year=year;
		this.month=month;
		this.day=day;
		
		//날짜 설정
		if(day==0){ strDay="";}
		else{strDay=day+"";}

		//토요일 파란 일요일 빨간 설정
		if(num==0)
			dayLabel.setForeground(Color.RED);
		else if(num==6)
			dayLabel.setForeground(Color.BLUE);
		
		dayLabel.setText(strDay);
		dayNumPanel.setLayout(new BorderLayout());
		dayNumPanel.add(dayLabel,BorderLayout.WEST);
	}
	
	public void setSchedule(ScheduleData sd){// 추가된 스케줄
	//맨처음 array기존에 있는 데이터는 set JLabel에 4개전까지 크기[]
			
		Vec.add(sd);
		if(Vec.size()<4)	{//스케줄이 4이하일때
			for(int i=0;i<Vec.size();i++){
				ScheduleLabel[i].setText(Vec.get(i).getScheduleName());	
				schedulePanel.add(ScheduleLabel[i]);		
				}
		  }
		else{
			for(int i=0;i<3;i++){
				ScheduleLabel[i].setText(Vec.get(i).getScheduleName());	
				schedulePanel.add(ScheduleLabel[i]);
			}
			ScheduleLabel[3].setText("...");
			schedulePanel.add(ScheduleLabel[3]);	
		}
		
		
		
		}
	
	public String getDate(){
		return year+"/"+(month+1)+"/"+day;
	}
	
	/*지금 날짜와 맞는 비교*/
	public void setIsCurrentDate(boolean b) {
		// TODO Auto-generated method stub
	
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
				showDetail=new ShowDetail(this, getDate(),Vec);
				showDetail.setVisible(true);
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
			   DAddSchedule addSchedule=new DAddSchedule(calendar,this.getDate());
			   addSchedule.setVisible(true);
	        }
	        else if(e.getSource() == modifySchedule)
	        {
	            
	        }
		   
	        else if(e.getSource() == freezeDate)
	        {
	        
	        } 
	

	}
	

	
	
	
	
}


/*dayPane의 내용상세내용 창*/
class ShowDetail extends JDialog{
	private JPanel contentPane;
	private JLabel DateLabel;
	private String date;
	private String[] data;
	private  JTable table;
	
	// 1번 판넬 클릭하면 2번판넬(상세정보있는 페이지로)로 전환
	public ShowDetail(DMonth_dayPane dMonth_dayPane,String date, Vector<ScheduleData> Vec){
		
		this.date=date;
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
		  DefaultTableModel model=new DefaultTableModel(colNames,0);
		 
		
		  model.setNumRows(0);
		  for(int i=0;i<Vec.size();i++){ 
			   Vector<String> VecInfo=new Vector<String>();//Vec에서 시간과 스케줄명 가져와 row 벡터에 저장 시키고 model.addRow
			   VecInfo.add(Vec.get(i).getTime());//시간 데이터
			   VecInfo.add(Vec.get(i).getScheduleName());//스켖
			   model.addRow(VecInfo);
			
		  }
		
		 
		   table = new JTable(model);   // 테이블 생성    
	      
	        JScrollPane scrollPane = new JScrollPane(table);  // 스크롤 기능 별도로 넣어줘야만 작동함
	        table.setShowVerticalLines(false);
	        panel.add(scrollPane, BorderLayout.CENTER); // contentPane에 테이블 적용
	 
	}
	

}
	
