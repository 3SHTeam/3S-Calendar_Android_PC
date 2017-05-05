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
	
	private JPanel dayInfoPanel;//�Ϸ� ��ü �����ǳ� 
	private JPanel dayNumPanel;
	private JPanel schedulePanel;
	
	private JLabel dayLabel;//day��
	private String strDay;
	private String strMonth;
	private JLabel[] ScheduleLabel = new JLabel[4];
	
	private String scheduleName;
	
	private int year,month,day;
	private JPopupMenu popup = new JPopupMenu();//������ ���콺 Ŭ���� popupmenu
	private JMenuItem addSchedule;
	private JMenuItem modifySchedule;
	private JMenuItem freezeDate;
	private JMenuItem deleteSchedule;
	
	int position;
	
	private DMonth_CalendarMain calendarMain;
	ShowDayEvents showDayEvents;//�󼼺��� ��â
	
	private EventData event;
	
	//�ش� ��¥�� �����͵鸸 �޾ƿ�����
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
		
	
	
		
		addSchedule = new JMenuItem("���� �߰�");
		addSchedule.addActionListener(this);
		popup.add(addSchedule);

		freezeDate = new JMenuItem("���� ����");
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
		dayLabel.setText(strDay);


		//����� �Ķ� �Ͽ��� ���� ����
		if(num==0)
			dayLabel.setForeground(Color.RED);
		else if(num==6)
			dayLabel.setForeground(Color.BLUE);
		
		dayNumPanel.add(dayLabel);
		
	}
	
	
	public void setSchedule(){// �߰��� ������
	//��ó�� array������ �ִ� �����ʹ� set JLabel�� 4�������� ũ��[]
		if (dayScheduleVec.size() < 4) {// �������� 4�����϶�
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
					ScheduleLabel[i].setText(dayScheduleVec.get(i).getData(2));//�����ٸ�	
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
			if(e.getButton()==3){//������ ���콺 Ŭ��
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
		
	}//�Ϸ� �ǳ�
	
	/*popupmenu Ŭ����*/
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


/*dayPane�� ����󼼳��� â*/
class ShowDayEvents extends JDialog{
	private JPanel contentPane;
	private JLabel DateLabel;
	private String date;
	private  JTable table;
	private JPopupMenu popup1 = new JPopupMenu();
	private JMenuItem modifySchedule;
	private JMenuItem deleteSchedule;
	// 1�� �ǳ� Ŭ���ϸ� 2���ǳ�(�������ִ� ��������)�� ��ȯ
	public ShowDayEvents(DMonth_dayPane dMonth_dayPane,String date, Vector<EventData> dayScheduleVec){
		
		this.date=date;
		setTitle("������ �󼼺���");
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
		  DefaultTableModel model=new DefaultTableModel(colNames,0){ // �� ���� ���ϰ� �ϴ� �κ�
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
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
	 
	        
	        modifySchedule = new JMenuItem("���� ����");
	        modifySchedule.addActionListener((ActionListener) this);
	        popup1.add(modifySchedule);
			
	        deleteSchedule = new JMenuItem("���� ����");
	        deleteSchedule.addActionListener((ActionListener) this);
			popup1.add(deleteSchedule);
	}


}
	
