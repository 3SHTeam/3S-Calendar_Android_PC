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
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Data.EventData;

public class DMonth_dayPane extends JPanel implements MouseListener,ActionListener{
	
	private JPanel dayInfoPanel;//�Ϸ� ��ü �����ǳ� 
	private JPanel dayNumPanel;
	private JPanel schedulePanel;
	
	private JLabel dayLabel=new JLabel();//day��
	private String strDay;
	private String strMonth;
	private JLabel[] ScheduleLabel=new JLabel[4];
	
	private String scheduleName;
	
	private int year,month,day;
	private JPopupMenu popup = new JPopupMenu();//������ ���콺 Ŭ���� popupmenu
	private JMenuItem addSchedule;
	private JMenuItem modifySchedule;
	private JMenuItem freezeDate;
	private JMenuItem deleteSchedule;
	
	int position;
	
	private DMonth_CalendarView calendar;
	ShowDetail showDetail;//�󼼺��� ��â
	private EventData event;
	//�ش� ��¥�� �����͵鸸 �޾ƿ�����
	private Vector <EventData>dayScheduleVec=new Vector<EventData>();
	

	
	public Vector<EventData> getVec() {
		return dayScheduleVec;
	}
	public void setVec(Vector<EventData> vec) {
		dayScheduleVec = vec;
	}
	
	public DMonth_dayPane(DMonth_CalendarView calendar, EFriend_GroupUI group) {
		
		super();
		GetGoogle gg=new GetGoogle();
		
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
		
	
	
		
		addSchedule = new JMenuItem("���� �߰�");
		addSchedule.addActionListener(this);
		popup.add(addSchedule);
		
		
		modifySchedule = new JMenuItem("���� ����");
		popup.add(modifySchedule);
		modifySchedule.addActionListener(this);
		
		freezeDate = new JMenuItem("���� ����");
		popup.add(freezeDate);
		freezeDate.addActionListener(this);
		
		deleteSchedule = new JMenuItem("���� ����");
		popup.add(deleteSchedule);
		deleteSchedule.addActionListener(this);


	}
	public void initInfo(){
		dayLabel.setText("");

		for(int i=0;i<ScheduleLabel.length;i++){
		ScheduleLabel[i] . setHorizontalAlignment(JLabel.CENTER);
		ScheduleLabel[i].setText("");
		}
	
	}
	/*������ day�ǳڿ� ��¥ �����ϱ�*/
	public void setValue(int year,int month,int day, int position){
		int num=position%7;
		this.position=position;
			
		this.year=year;
		this.month=month;
		this.day=day;
		
		//��¥ ����
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

		//����� �Ķ� �Ͽ��� ���� ����
		if(num==0)
			dayLabel.setForeground(Color.RED);
		else if(num==6)
		dayNumPanel.add(dayLabel,BorderLayout.WEST);
		
	}
	
	/*DB���� ������ ��������*/
	public void setSchedule(){// �߰��� ������
	//��ó�� array������ �ִ� �����ʹ� set JLabel�� 4�������� ũ��[]
		//DB���� ������ ��������
		
		String today= String.valueOf(year)+ strMonth+ strDay+"0000"; //201704140000
		System.out.println(today);
		
		//�������� �޾ƿ� dayScheduleVec�� ����
		
		
		if(dayScheduleVec.size()<4)	{//�������� 4�����϶�
			for(int i=0;i<dayScheduleVec.size();i++){
				ScheduleLabel[i].setText(dayScheduleVec.get(i).getData(2));	
				schedulePanel.add(ScheduleLabel[i]);		
				}
		  }
		else{
			for(int i=0;i<3;i++){
				ScheduleLabel[i].setText(dayScheduleVec.get(i).getData(2));//�����ٸ�	
				schedulePanel.add(ScheduleLabel[i]);
			}
			ScheduleLabel[3].setText("...");
			schedulePanel.add(ScheduleLabel[3]);	
		}
	}
	
	public void addSchedule(EventData event){
		dayScheduleVec.add(event);
		initInfo();//�ʱ�ȭ
		setValue(year, month, day, position);
		setSchedule();
		
	}
	public String getDate(){
		return year+"/"+(month+1)+"/"+day;
	}
	
	/*���� ��¥�� �´� ��*/
	public void setIsCurrentDate(boolean b) {
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!strDay.equals("")){
			if(e.getButton()==3){//������ ���콺 Ŭ��
				popup.show((Component)e.getSource(), e.getX(), e.getY());
			}
			else
			{
				showDetail=new ShowDetail(this, getDate(),dayScheduleVec);
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
		
	}//�Ϸ� �ǳ�
	
	/*popupmenu Ŭ����*/
	@Override
	public void actionPerformed(ActionEvent e) {
		   if(e.getSource() == addSchedule)
		   {		   
			   DAddSchedule addSchedule=new DAddSchedule(this,this.getDate());
			   addSchedule.setVisible(true);
	        }
	        else if(e.getSource() == modifySchedule)
	        {
	            
	        }
		   
	        else if(e.getSource() == freezeDate)
	        {
	        
	        } 
	
	        else if(e.getSource()==deleteSchedule){
	        	
	        }

	}
	
}


/*dayPane�� ����󼼳��� â*/
class ShowDetail extends JDialog{
	private JPanel contentPane;
	private JLabel DateLabel;
	private String date;
	private  JTable table;
	
	// 1�� �ǳ� Ŭ���ϸ� 2���ǳ�(�������ִ� ��������)�� ��ȯ
	public ShowDetail(DMonth_dayPane dMonth_dayPane,String date, Vector<EventData> dayScheduleVec){
		
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
		
		//schedule �ð��� ����� ����
		JPanel panel = new JPanel(); 
		
		panel.setBounds(0, 53, 450, 479);
		contentPane.add(panel);
		
		  String colNames[] = { "�ð�", "�����ٸ�"};  // Jtable ����� 1���� �迭
		  DefaultTableModel model=new DefaultTableModel(colNames,0);
		 
		
		  model.setNumRows(0);
		  for(int i=0;i<dayScheduleVec.size();i++){ 
			   Vector<String> VecInfo=new Vector<String>();//Vec���� �ð��� �����ٸ� ������ row ���Ϳ� ���� ��Ű�� model.addRow
			   VecInfo.add(dayScheduleVec.get(i).getData(4)+"-"+dayScheduleVec.get(i).getData(5));//���۽ð� ������
			   VecInfo.add(dayScheduleVec.get(i).getData(2));//���O
			   model.addRow(VecInfo);
			
		  }
		
		 
		   table = new JTable(model);   // ���̺� ����    
	      
	        JScrollPane scrollPane = new JScrollPane(table);  // ��ũ�� ��� ������ �־���߸� �۵���
	        table.setShowVerticalLines(false);
	        panel.add(scrollPane, BorderLayout.CENTER); // contentPane�� ���̺� ����
	 
	}
	

}
	
