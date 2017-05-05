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
   private String date;//"년/월/일"
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
      setTitle("스케줄 추가");
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
      DayLabel.setFont(new Font("굴림", Font.BOLD, 14));
      DayLabel.setBounds(162, 21, 116, 15);
      contentPane.add(DayLabel);
      
      ScheduleNameLabel = new JLabel("스케줄명 :");
      ScheduleNameLabel.setBounds(29, 62, 75, 15);
      contentPane.add(ScheduleNameLabel);
      
      ScheduleName = new JTextField();
      ScheduleName.setBounds(119, 59, 221, 21);
      contentPane.add(ScheduleName);
      ScheduleName.setColumns(10);

      timeLabel = new JLabel("시간 :");
      timeLabel.setBounds(29, 154, 57, 15);
      contentPane.add(timeLabel);
      
      StartHour= new JTextField(String.valueOf(now_hour));
      StartHour.setBounds(119, 151, 30, 21);
      contentPane.add(StartHour);
      
      JLabel label=new JLabel("시");
      label.setFont( new Font("고딕", Font.BOLD, 12));
      label.setBounds(150, 152, 16, 18);
      contentPane.add(label);
      
      /*Default로 현재 시간을 받아온다*/
      StartMin=new JTextField(String.valueOf(now_min));
      StartMin.setBounds(170, 151, 30, 21);
      contentPane.add(StartMin);
      
      JLabel label1=new JLabel("분");
      label1.setFont( new Font("고딕", Font.BOLD, 12));
      label1.setBounds(205, 152, 16, 18);
      contentPane.add(label1);
      
      JLabel label2 = new JLabel("-");
      label2.setFont( new Font("고딕", Font.BOLD, 20));
      label2.setBounds(228, 152, 14, 18);
      contentPane.add(label2);
      
      EndHour= new JTextField(String.valueOf(now_hour));
      EndHour.setBounds(250, 151, 30, 21);
      contentPane.add(EndHour);

      JLabel label3=new JLabel("시");
      label3.setFont( new Font("고딕", Font.BOLD, 12));
      label3.setBounds(285, 152, 16, 18);
      contentPane.add(label3);
      
      
      EndMin=new JTextField(String.valueOf(now_min));
      EndMin.setBounds(301, 151, 30, 21);
      contentPane.add(EndMin);
      
      JLabel label4=new JLabel("분");
      label4.setFont( new Font("고딕", Font.BOLD, 12));
      label4.setBounds(335, 152, 16, 18);
      contentPane.add(label4);
      
      
      tagLabel = new JLabel("태그 :");
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
      
      LocationLabel = new JLabel("장소 :");
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
      
      ContextLabel = new JLabel("내용 :");
      ContextLabel.setBounds(29, 309, 57, 15);
      contentPane.add(ContextLabel);                                  
      
      context = new JTextField();
      context.setBounds(121, 306, 219, 109);
      contentPane.add(context);
      context.setColumns(10);
      
      
      //개인으로 참가자추가?
      GroupLabel = new JLabel("그룹 :");
      GroupLabel.setBounds(29, 451, 57, 15);
      contentPane.add(GroupLabel);
      
      
      groupBox = new JComboBox();//현재 있는 얻어온 그룹들을 저장시킨다.
      groupBox.setBounds(119, 448, 221, 21);
      
      String GroupName[]={"개인","3s","임의로 만들어준값들"};
      //임의로 만듦
       //나중에 디비에서
      for(int i=0;i<GroupName.length;i++){
//    	  GroupName[i]=fg.getGroupName(i); 	  
    	  groupBox.addItem(GroupName[i]);
      } 
      contentPane.add(groupBox);


        
      JButton addBtn = new JButton("add");
      addBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            //내용 저장 
        	 String S_hour=StartHour.getText();
        	 String S_min=StartMin.getText();
        	 start_hour= Integer.parseInt(S_hour);
        	 start_min=Integer.parseInt(S_min);
        	 
        	 String E_hour=EndHour.getText();
        	 String E_min=EndMin.getText();
        	 end_hour= Integer.parseInt(E_hour);
        	 end_min=Integer.parseInt(E_min);
        	 
        	 if((start_hour<24&&start_hour>0)&&(end_hour<24&&end_hour>0)&&(start_min<60&&start_min>0)&&(end_min<60||end_min>0)&&(start_hour<=end_hour)){
        		 //이벤트데이터를 새로 생성
        		 event = setScheduleData();
        		 //이벤트 데이터를 DB에 보냄
        		 //sendEventToAndroid(event.getSendSQLString());
        		 //DB에서 이벤트 데이터 최신화 하고 캘린더를 새로고침
        		 calendar.refreshSchedule();
        		 dispose();       	 
        	 }
        	 else
        	 {
        		 JOptionPane.showMessageDialog(null, "올바른 시간형식이 아닙니다", "Time Format Error", JOptionPane.ERROR_MESSAGE);
        	 }
         }
      });
      addBtn.setBounds(175, 513, 97, 23);
      contentPane.add(addBtn);
     
  
   }

       
     private EventData setScheduleData(){//ScheduleData를 set
    	 System.out.println("스케줄이름:"+ScheduleName.getText()); 
    	 EventData event=new EventData();
    	 event.setData(1,calendar.getUser().getData(0));//smaster - user id 가져옴 
    	 event.setData(2,ScheduleName.getText());
    	 event.setData(3,location.getText());
    	 event.setData(4,strYear+strMonth+strDay+StartHour.getText()+StartMin.getText());//"yyyyMMddHHmm"
    	 event.setData(5,strYear+strMonth+strDay+EndHour.getText()+":"+EndHour.getText());//"yyyyMMddHHmm"
    	 return event;
     }
     /*스케줄추가->Android에 보내기*/////////
     private void sendEventToAndroid(String sql){
    	 /* Android에 이벤트 정보 보내기 */
			String url = "http://113.198.84.66/Calendar3S/FirebaseJavaToAnd.php";
			String token = calendar.getUser().getData(7);

			//토큰과 메세지를 파이어베이스에 보냄
			SendToFirebase stFB = new SendToFirebase(url, sql, token);
			stFB.start();// DB연결 스레드 시작
			try {
				stFB.join();// DB연결이 완료될때까지 대기
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			} 
     }
     
    
     
     public EventData getEventData(){
		return event;//set 한 값을 daypane scheduleArr에 얻어와 add  	 
     }
     
}