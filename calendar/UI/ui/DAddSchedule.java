package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Calendar;

import DB.SendToFirebase;
import Data.EventData;



public class DAddSchedule extends JFrame {
   private JPanel contentPane;
   private JLabel DayLabel;
   private String date;//"��/��/��"
   private JLabel ScheduleNameLabel;

   private JLabel LocationLabel;
   private JLabel tagLabel;
   private JLabel ContextLabel;
   private JLabel timeLabel;
   private JLabel GroupLabel;
   

   private JTextField ScheduleName;
   private JTextField location;
   private JTextField StartHour;
   private JTextField StartMin;
   private JTextField EndHour;
   private JTextField EndMin;
   private JComboBox groupBox;

   private DMonth_CalendarMain calendar;
   private EventData event;
   private String strYear,strMonth,strDay;
   private JTextField context;
   private int now_hour,now_min,start_hour,start_min,end_hour,end_min;
   private TagSet tagSet;

   public DAddSchedule(DMonth_CalendarMain calendar,String date) {
	
	   Calendar c=Calendar.getInstance();
	
	   //currentTime
      this.calendar=calendar;
      setTitle("������ �߰�");
      setResizable(false);
      setLocationRelativeTo(null);
      now_hour=c.get(Calendar.HOUR_OF_DAY);
      now_min=c.get(Calendar.MINUTE);
      

      
      setBounds(100, 100, 450, 619);
      contentPane = new JPanel();
      setResizable(false);
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
 
      this.date=date;//"xxxx/xx/xx"
		 String []tok=date.split("/");
		 strYear=tok[0];
		 strMonth=tok[1];
		 strDay=tok[2];
		 
		 
      DayLabel = new JLabel(date);
      DayLabel.setFont(new Font("����", Font.BOLD, 14));
      DayLabel.setBounds(162, 21, 116, 15);
      contentPane.add(DayLabel);
      
      ScheduleNameLabel = new JLabel("�����ٸ� :");
      ScheduleNameLabel.setBounds(29, 62, 75, 15);
      contentPane.add(ScheduleNameLabel);
      
      ScheduleName = new JTextField();
      ScheduleName.setBounds(119, 59, 221, 21);
      contentPane.add(ScheduleName);
      ScheduleName.setColumns(10);

      timeLabel = new JLabel("�ð� :");
      timeLabel.setBounds(29, 154, 57, 15);
      contentPane.add(timeLabel);
      
      StartHour= new JTextField(String.valueOf(now_hour));
      StartHour.setBounds(119, 151, 30, 21);
      contentPane.add(StartHour);
      
      JLabel label=new JLabel("��");
      label.setFont( new Font("���", Font.BOLD, 12));
      label.setBounds(150, 152, 16, 18);
      contentPane.add(label);
      
      /*Default�� ���� �ð��� �޾ƿ´�*/
      StartMin=new JTextField(String.valueOf(now_min));
      StartMin.setBounds(170, 151, 30, 21);
      contentPane.add(StartMin);
      
      JLabel label1=new JLabel("��");
      label1.setFont( new Font("���", Font.BOLD, 12));
      label1.setBounds(205, 152, 16, 18);
      contentPane.add(label1);
      
      JLabel label2 = new JLabel("-");
      label2.setFont( new Font("���", Font.BOLD, 20));
      label2.setBounds(228, 152, 14, 18);
      contentPane.add(label2);
      
      EndHour= new JTextField(String.valueOf(now_hour));
      EndHour.setBounds(250, 151, 30, 21);
      contentPane.add(EndHour);

      JLabel label3=new JLabel("��");
      label3.setFont( new Font("���", Font.BOLD, 12));
      label3.setBounds(285, 152, 16, 18);
      contentPane.add(label3);
      
      
      EndMin=new JTextField(String.valueOf(now_min));
      EndMin.setBounds(301, 151, 30, 21);
      contentPane.add(EndMin);
      
      JLabel label4=new JLabel("��");
      label4.setFont( new Font("���", Font.BOLD, 12));
      label4.setBounds(335, 152, 16, 18);
      contentPane.add(label4);
      
      
      tagLabel = new JLabel("�±� :");
      tagLabel.setBounds(29, 206, 75, 15);
      contentPane.add(tagLabel);
      
      JComboBox tagBox = new JComboBox();
      tagSet=new TagSet();
      tagSet.setTags(calendar.getTags());
      String[] tagNames=tagSet.checkTagName();
      System.out.println(tagNames);
      for(int i=0;i<tagNames.length;i++){
    	  tagBox.addItem(tagNames[i]);
      } 
      tagBox.setBounds(119, 203, 221, 21);
      contentPane.add(tagBox);
      
      LocationLabel = new JLabel("��� :");
      LocationLabel.setBounds(29, 259, 57, 15);
      contentPane.add(LocationLabel);
      
      location = new JTextField();
      location.setColumns(10);
      location.setBounds(121, 256, 199, 21);
      contentPane.add(location); 
      
//      ImageIcon LocationIcon=new ImageIcon("image/ic_room_black_23dp_1x.png");
//      JButton locationBtn = new JButton(LocationIcon);
//      locationBtn.setBounds(326, 254, 23, 23);
//      contentPane.add(locationBtn);
      
      ContextLabel = new JLabel("���� :");
      ContextLabel.setBounds(29, 309, 57, 15);
      contentPane.add(ContextLabel);                                  
      
      context = new JTextField();
      context.setBounds(121, 306, 219, 109);
      contentPane.add(context);
      context.setColumns(10);
      
      
      //�������� �������߰�?
      GroupLabel = new JLabel("�׷� :");
      GroupLabel.setBounds(29, 451, 57, 15);
      contentPane.add(GroupLabel);
      
      
      groupBox = new JComboBox();//���� �ִ� ���� �׷���� �����Ų��.
      groupBox.setBounds(119, 448, 221, 21);
      
      String GroupName[]={"����","3s","���Ƿ� ������ذ���"};
      //���Ƿ� ����
       //���߿� ��񿡼�
      for(int i=0;i<GroupName.length;i++){
//    	  GroupName[i]=fg.getGroupName(i); 	  
    	  groupBox.addItem(GroupName[i]);
      } 
      contentPane.add(groupBox);


        
      JButton addBtn = new JButton("add");
      addBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //���� ���� 
        	 String S_hour=StartHour.getText();
        	 String S_min=StartMin.getText();
        	 start_hour= Integer.parseInt(S_hour);
        	 start_min=Integer.parseInt(S_min);
        	 
        	 String E_hour=EndHour.getText();
        	 String E_min=EndMin.getText();
        	 end_hour= Integer.parseInt(E_hour);
        	 end_min=Integer.parseInt(E_min);
        	 
        	 if((start_hour<24&&start_hour>0)&&(end_hour<24&&end_hour>0)&&(start_min<60&&start_min>0)&&(end_min<60||end_min>0)&&(start_hour<=end_hour)){
        		 //�̺�Ʈ�����͸� ���� ����
        		 event = setScheduleData();
        		 //�̺�Ʈ �����͸� DB�� ����
        		 //sendEventToAndroid(event.getSendSQLString());
        		 //DB���� �̺�Ʈ ������ �ֽ�ȭ �ϰ� Ķ������ ���ΰ�ħ
        		 calendar.refreshSchedule();
        		 dispose();       	 
        	 }
        	 else
        	 {
        		 JOptionPane.showMessageDialog(null, "�ùٸ� �ð������� �ƴմϴ�", "Time Format Error", JOptionPane.ERROR_MESSAGE);
        	 }
         }
      });
      addBtn.setBounds(175, 513, 97, 23);
      contentPane.add(addBtn);
     
  
   }

       
     private EventData setScheduleData(){//ScheduleData�� set
    	 System.out.println("�������̸�:"+ScheduleName.getText()); 
    	 EventData event=new EventData();
    	 event.setData(1,calendar.getUser().getData(0));//smaster - user id ������ 
    	 event.setData(2,ScheduleName.getText());
    	 event.setData(3,location.getText());
    	 event.setData(4,strYear+strMonth+strDay+StartHour.getText()+StartMin.getText());//"yyyyMMddHHmm"
    	 event.setData(5,strYear+strMonth+strDay+EndHour.getText()+":"+EndHour.getText());//"yyyyMMddHHmm"
    	 return event;
     }
     /*�������߰�->Android�� ������*/////////
     private void sendEventToAndroid(String sql){
    	 /* Android�� �̺�Ʈ ���� ������ */
			String url = "http://113.198.84.66/Calendar3S/FirebaseJavaToAnd.php";
			String token = calendar.getUser().getData(7);

			//��ū�� �޼����� ���̾�̽��� ����
			SendToFirebase stFB = new SendToFirebase(url, sql, token);
			stFB.start();// DB���� ������ ����
			try {
				stFB.join();// DB������ �Ϸ�ɶ����� ���
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			} 
     }
     
    
     
     public EventData getEventData(){
		return event;//set �� ���� daypane scheduleArr�� ���� add  	 
     }
     
}