package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
//setWeek(Calendar c)로 입력 받은 주의 날짜를 얻어 weekArr를 설정해준다
//각 Week_dayPane [i]객체에 weekArr[i]를 보내서 맨위에 저장
public class DWeek_CalendarView extends JFrame implements ActionListener{
	DWeek_dayPane[] days;
	private JPanel contentPane;
	private Calendar now=Calendar.getInstance();//현재 날짜
	private Calendar c=Calendar.getInstance();//이동할 날짜
    private ArrayList<String> weekArr;//7일 날짜 배열
    private JPanel DaysPanel;
    private JLabel titleLabel;
    private JLabel[] WeekLabels;
    String []DAYS_OF_WEEK={"일","월","화","수","목","금","토"};
    private int year,month,today,value,weekNum;//몇째주
    private JButton monthBtn;
    private JButton treeBtn;
	public static final DWeek_CalendarView instance =new DWeek_CalendarView();


	/**
	 * Create the frame.
	 */
	public DWeek_CalendarView() {
		setTitle("WeekCalendarView");
		setResizable(false);
		setBounds(100, 100, 1250, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(99, 10, 293, 55);
		contentPane.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));
	
		
		JButton DownBtn = new JButton("<");
		DownBtn.setOpaque(true);
		DownBtn.setBackground(new Color(224, 255, 255));
		DownBtn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		DownBtn.setHorizontalAlignment(SwingConstants.CENTER);
		DownBtn.setPreferredSize(new Dimension(35, 35));
		DownBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				weekArr.clear();
                c.add(Calendar.DATE,-7);
				setWeek(c);
				c.add(Calendar.DATE,-7);
				setDateLabel();
			}
		});
		titlePanel.add(DownBtn, BorderLayout.WEST);
		
		
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("나눔고딕코딩", Font.BOLD, 13));
		titleLabel.setOpaque(false);
		titleLabel.setBackground(new Color(255, 255, 255));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanel.add(titleLabel, BorderLayout.CENTER);
		
		JButton UpBtn = new JButton(">");
		UpBtn.setOpaque(true);
		UpBtn.setBackground(new Color(224, 255, 255));
		UpBtn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		UpBtn.setHorizontalAlignment(SwingConstants.CENTER);
		UpBtn.setPreferredSize(new Dimension(35, 15));
		UpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	
				c.add(Calendar.DATE,7);
				weekArr.clear();
				setWeek(c);
				 c.add(Calendar.DATE,-7);
				 setDateLabel();
			}
		});
		titlePanel.add(UpBtn, BorderLayout.EAST);
		
		ImageIcon treeIcon=new ImageIcon("image/ic_treeview_black_48dp_1x.png");
		treeBtn = new JButton(treeIcon);
		treeBtn.addActionListener(this);
		treeBtn.setBounds(1163, 29, 48, 48);
		contentPane.add(treeBtn);
		JLabel[] WeekLabels;
		
/*주 요일과날짜 표시 판넬*/	
	

/*일별 스케줄*/
		JPanel DaysPanel = new JPanel();//day별  판넬
		DaysPanel.setBackground(Color.LIGHT_GRAY);
		DaysPanel.setBounds(141, 75, 1019, 616);
		DaysPanel.setLayout(new GridLayout(1,8));
		days=new DWeek_dayPane[8];
		setWeek(now);
		for(int i=0;i<days.length;i++){
		//오늘 날짜기준으로 정렬하는 함수
				if(i==0){
					days[i]=new DWeek_dayPane(this,"시간");
				}
				else
					days[i]=new DWeek_dayPane(this,weekArr.get(i-1));
			
			DaysPanel.add(days[i]);
		}
			setDateLabel();	
			contentPane.add(DaysPanel);

		
/*그룹 단위 스케줄*/
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(0, 251, 137, 217);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("그룹별 스케줄");
		lblNewLabel.setBounds(12, 10, 113, 15);
		panel.add(lblNewLabel);
		
		monthBtn = new JButton("month");
		monthBtn.addActionListener(this);
		monthBtn.setBounds(1054, 42, 97, 23);
		contentPane.add(monthBtn);
	}
	public void setWeek(Calendar calendar){
		weekArr= new ArrayList<String>();
	        //주 캘린더 날짜 기준 시작 일요일 구하기
			year=calendar.get(Calendar.YEAR);
			month=calendar.get(Calendar.MONTH);
			weekNum=calendar.get(Calendar.WEEK_OF_MONTH);
	        calendar.add(Calendar.DATE, 1 - calendar.get(Calendar.DAY_OF_WEEK) );

	        //기준일 정하고 기준일로부터 7일씩 표시
	        for(int i=0;i<7;i++){//시작 일요일부터 7일 표시
	        	weekArr.add(calendar.get(Calendar.DATE)+"일 ("+DAYS_OF_WEEK[i]+")");
	            calendar.add(Calendar.DATE,1);//하루씩 총 7일

	        }		

		
	}
	public void setDateLabel(){
		titleLabel.setText("");
		titleLabel.setText(year+"년 "+(month+1)+"월 "+weekNum+"째주");
		for(int i=1;i<8;i++){
			    days[i].initInfo();
	            days[i].setValue(year,month,weekArr.get(i-1),i);//각 판넬에 day지정          
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		// TODO Auto-generated method stub
	
		if(e.getSource()==treeBtn){
			CTreeView treeView=(CTreeView)CTreeView.getInstanace();
			treeView.setVisible(true);
		}
		if(e.getSource()==monthBtn){
			DMonth_CalendarView monthCalendar=(DMonth_CalendarView)DMonth_CalendarView.getInstanace();
			monthCalendar.setVisible(true);
		}
	}
	
	
	public static DWeek_CalendarView getInstanace() {
			// TODO Auto-generated method stub
			return instance;
		}


	
}
