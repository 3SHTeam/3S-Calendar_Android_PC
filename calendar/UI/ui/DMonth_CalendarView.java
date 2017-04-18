package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DMonth_CalendarView extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JPanel YearPanel;//년
	private JLabel YearLabel;
	
	private JPanel MonthPanel;//월
	private JLabel MonthLabel;
	private JComboBox MonthCombo;
	
	DMonth_dayPane days[];//42개의 day설정
	private int year,month,today,lastday,startYoil;//년,월,일,달의 마지막일 ,시작요일
	
	private JButton TreeViewIcon;
	private JButton weekBtn;
	private JButton todayBtn;
	
	private static EFriend_GroupUI group;


	public static final DMonth_CalendarView instance =new DMonth_CalendarView(group);
	
	private Calendar now= Calendar.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DMonth_CalendarView frame = new DMonth_CalendarView(group);
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
	public DMonth_CalendarView(EFriend_GroupUI group) {
		setTitle("CalendarView");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
//현재 년,월
		year=now.get(Calendar.YEAR);
		month=now.get(Calendar.MONTH);
		
//년
		YearPanel = new JPanel();
		YearPanel.setBounds(34, 44, 132, 21);
		contentPane.add(YearPanel);
		YearPanel.setLayout(new BorderLayout(0, 0));
				
		YearLabel = new JLabel(year+"");
		YearLabel.setBorder(new LineBorder(Color.GRAY));
		YearLabel.setBackground(Color.WHITE);
		YearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		YearPanel.add(YearLabel, BorderLayout.CENTER);		
		
		JLabel DownLabel = new JLabel("  <  ");
		DownLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		DownLabel.setOpaque(true);
		DownLabel.setBackground(SystemColor.inactiveCaption);
		DownLabel.setForeground(new Color(0, 0, 0));
		DownLabel.addMouseListener(new MouseListener() {		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				DownLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));		
			}			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
					DownLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}	
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub	
			}		
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				YearLabel.setText("");
				year--;
				YearLabel.setText(year+"");
				changeDate();
			}
		});
		YearPanel.add(DownLabel, BorderLayout.WEST);

		JLabel UpLabel = new JLabel("  >  ");
		UpLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		UpLabel.setBackground(SystemColor.inactiveCaption);
		UpLabel.setOpaque(true);
		UpLabel.addMouseListener(new MouseListener() {		
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				UpLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			}			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
					UpLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}	
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub		
			}		
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				YearLabel.setText("");
				year++;
				YearLabel.setText(year+"");
				changeDate();
			}
		});
		YearPanel.add(UpLabel, BorderLayout.EAST);

//월
		String[] DAY_OF_MONTH= {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
		
		MonthCombo = new JComboBox(DAY_OF_MONTH);
		MonthCombo.setSelectedIndex(month);
		MonthCombo.setBounds(199, 44, 69, 21);
		MonthCombo.addActionListener(new ActionListener() {	
				@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb=(JComboBox)e.getSource();
				month=cb.getSelectedIndex();	
				MonthLabel.setText(DAY_OF_MONTH[month]);
				
				changeDate();
			}
		});
		contentPane.add(MonthCombo);	
	
		MonthPanel = new JPanel();
		MonthPanel.setLayout(new BorderLayout());
		MonthLabel=new JLabel(DAY_OF_MONTH[month]);
		MonthLabel.setBounds(27, 0, 73, 51);
		MonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MonthLabel.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 35));
		MonthPanel.setBounds(595, 11, 132, 54);
		MonthPanel.setLayout(null);	
		MonthPanel.add(MonthLabel);
		contentPane.add(MonthPanel);
		
//주
		JPanel WeekPanel = new JPanel();
		JLabel[] WeekLabels;
		WeekPanel.setBackground(Color.LIGHT_GRAY);
		WeekPanel.setBounds(137, 75, 1023, 30);
		WeekPanel.setLayout(new GridLayout(1, 7));
		contentPane.add(WeekPanel);
			
		WeekLabels=new JLabel[7];
		String []DAYS_OF_WEEK={"일","월","화","수","목","금","토"};
		
		for(int i=0;i<WeekLabels.length;i++){
		WeekLabels[i] = new JLabel(DAYS_OF_WEEK[i]);
		WeekLabels[i].setPreferredSize(new Dimension(140, 25));
		WeekLabels[i].setHorizontalAlignment(JLabel.CENTER);
		if(i==0)
			WeekLabels[i].setForeground(Color.RED);
		if(i==6)
			WeekLabels[i].setForeground(Color.BLUE);
		WeekPanel.add(WeekLabels[i]);  
		}
		
//일
		JPanel DaysPanel = new JPanel();//day별  판넬
		DaysPanel.setBackground(Color.LIGHT_GRAY);
		DaysPanel.setBounds(137, 106, 1023, 585);
		DaysPanel.setLayout(new GridLayout(6,7));
		days=new DMonth_dayPane[42];
		for(int i=0;i<days.length;i++){
			days[i]=new DMonth_dayPane(this,group);
			DaysPanel.add(days[i]);
		}
		changeDate();
		contentPane.add(DaysPanel);
		
		ImageIcon treeIcon=new ImageIcon("image/ic_treeview_black_48dp_1x.png");
		TreeViewIcon = new JButton(treeIcon);
		TreeViewIcon.addActionListener(this);
		TreeViewIcon.setBounds(1163, 17, 48, 48);
		contentPane.add(TreeViewIcon);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(0, 251, 137, 217);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("그룹별 스케줄");
		lblNewLabel.setBounds(12, 10, 113, 15);
		panel.add(lblNewLabel);
		
		weekBtn = new JButton("Week");
		weekBtn.setBounds(980, 42, 97, 23);
		weekBtn.addActionListener(this);
		contentPane.add(weekBtn);
		
		JButton msgBtn = new JButton("message");
		msgBtn.setBounds(12, 618, 97, 23);
		contentPane.add(msgBtn);
		
		todayBtn=new JButton("t");
		todayBtn.setBounds(1100,17,48,48);
		todayBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				year=now.get(Calendar.YEAR);
				month=now.get(Calendar.MONTH);
				YearLabel.setText(year+"");
				MonthCombo.setSelectedIndex(month);
				MonthLabel.setText(DAY_OF_MONTH[month]);
				
				changeDate();
				
			}
		});
		contentPane.add(todayBtn);
		
	}

/*년 월 날짜 이동함수*/
	public void changeDate(){
		Calendar c= Calendar.getInstance();	
		
		c.set(Calendar.YEAR,year); //이동한 년으로 c 설정
		c.set(Calendar.MONTH, month);//이동한 월로 c 설정	

		
		//현재date를 1일로 변경->시작요일 구하기
		c.set(Calendar.DAY_OF_MONTH,1);
		startYoil=c.get(Calendar.DAY_OF_WEEK);

		//해당 달의 마지막일구하기	
		lastday = c.getActualMaximum(Calendar.DATE);
		setRange(startYoil,lastday,1);
	}
	  
/*42개의 판넬에 day설정 함수*/
	public void setRange(int startYoil,int lastday,int today){
		int cnt=1;
		int value;//판넬 day설정값
		for(int i=0;i<days.length;i++){
				if(i<(startYoil-1)||(i>(lastday+startYoil-2)))//42개중 day가 표시될 판넬 외에는 0으로 정보를 보내준다.
					value=0;
				else
					value=cnt++;	
				
				days[i].initInfo();
				days[i].setValue(year,month,value,i);//기본정보인 년/월/일 정보를 보내주고, 각 판넬에 day 위치 설정				
				days[i].getVec().clear();//기존에 저장된 스케줄 초기화 시킨후 다시 받는다
//				days[i].setSchedule();
				
				//for(int j=0;j<scheduleVec.size();j++){
				//	ScheduleData sd=scheduleVec.get(j);
				
				//	if((year==sd.year)&&(month==sd.month-1)&&(value==sd.day)){						
				if(value!=0)	{	
				days[i].setSchedule();
				}
				//	}
				//}
				if(value==today){
					days[i].setIsCurrentDate(true);//현재 날짜 표시 check
				}
		}
	}
	
	//addSchedule
//	public void addSchedule(ScheduleData scheduleData){
//		scheduleVec.add(scheduleData);
//		changeDate();//갱신
//	}
//	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		// TODO Auto-generated method stub
		if(e.getSource()==TreeViewIcon){
			 CTreeView treeView=(CTreeView)CTreeView.getInstanace();
			treeView.setVisible(true);
		}
		if(e.getSource()==weekBtn){
			DWeek_CalendarView weekCalendar=(DWeek_CalendarView)DWeek_CalendarView.getInstanace();
			weekCalendar.setVisible(true);
		}
	}
	
	public static DMonth_CalendarView getInstanace() {
			// TODO Auto-generated method stub
			return instance;
		}
	

	public void setGroup(EFriend_GroupUI group){
		this.group = group;
	}
	
	public EFriend_GroupUI getGroup(){
		return group;
	}
	
//	public String getScheduleGroupName(int index){
//		return scheduleVec.elementAt(index).getGroup();
//	}
//	
//	public String getScheduleName(int index){
//		return scheduleVec.elementAt(index).getScheduleName();
//	}
//	
//	public String getScheduleDate(int index){
//		return scheduleVec.elementAt(index).getDate() + " " + scheduleVec.elementAt(index).getStartTime();
//	}
}


