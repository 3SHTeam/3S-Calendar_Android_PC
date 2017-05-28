package Calendar.UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import Calendar.DB.SendToFirebase;
import Calendar.Data.EventData;
import Calendar.Data.TagData;

public class DAddSchedule extends initEventPanel {
   private JPanel contentPane;
   private JLabel DayLabel;
   
   private String date;//"년/월/일"

	private JSpinner StartHour;
	private JSpinner StartMin;
	private JSpinner EndHour;
	private JSpinner EndMin;
	private String starthour_Str;//HH int ->dateformat이용해 형식변환 문자열
	private String startmin_Str;
	private String endhour_Str;//mm형식으로
	private String endmin_Str;

    private JComboBox <String>tagBox;

    private DMonth_CalendarMain calendar;
    private EventData event=new EventData();
    private String tagId;

   private ArrayList <TagData>tags=new ArrayList <TagData>();


   
   public DAddSchedule(String date){
	   super();
       setTitle("일정 추가");
       setResizable(false);
       setLocationRelativeTo(null);
       setBounds(100, 100, 450, 550);      
       setResizable(false);
       
       Image img=Images.smainIcon.getImage();
		contentPane = new JPanel(){
		   public void paintComponent(Graphics g){
			    //super.paintComponents(g);
			    g.drawImage(img, 0, 0, 450, 550, null);
			   }
			  };
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);   

	    calendar=DMonth_CalendarMain.getInstanace();
	    tags=calendar.getTags();
	    
	    this.date=date;//"xxxx/xx/xx"	    

		 		 
      DayLabel = new JLabel(date);
      DayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
      DayLabel.setBounds(30, 15, 300, 60);
      contentPane.add(DayLabel);
      
      super.init();
      super.sNamePane();
      super.locationPane();
      timePane();
      tagPane();
      btnPane();  
   }

	@Override
	public void tagPane() {
		tagBox = new JComboBox<String>();
		tagBox.setFont(f);
		tagBox.setBounds(120, 260, 270, 42);

		String[] tagNames = new String[tags.size()];
		tagNames = calendar.getTagNames();
		for (int i = 0; i < tagNames.length; i++) {
			tagBox.addItem(tagNames[i]);
		}
		contentPane.add(tagBox);
	  }

	@Override
	public void timePane() {
		Calendar c=Calendar.getInstance();
		 now_hour=c.get(Calendar.HOUR_OF_DAY);
	     now_min=c.get(Calendar.MINUTE);
	     
			timePanel = new JPanel();
			timePanel.setOpaque(false);
			timePanel.setBounds(117, 175, 280, 39);
			add(timePanel);
			timePanel.setLayout(null);
			
			StartHour= new JSpinner();
			StartHour.setBounds(0, 4, 47, 28);
			StartHour.setModel(new SpinnerNumberModel(now_hour, 0, 23, 1));
			timePanel.add(StartHour);
			
			JLabel label=new JLabel("시");
			label.setBounds(46, 8, 16, 22);
			label.setFont( new Font("고딕", Font.BOLD, 15));
			timePanel.add(label);

			StartMin=new JSpinner();
			StartMin.setBounds(63, 4, 47, 28);
			StartMin.setModel(new SpinnerNumberModel(now_min, 0, 59, 1));
			timePanel.add(StartMin);
			
			JLabel label1 = new JLabel("분");
			label1.setBounds(109, 8, 16, 22);
			label1.setFont(new Font("고딕", Font.BOLD, 15));
			timePanel.add(label1);
					
			JLabel label2 = new JLabel("-",JLabel.CENTER);
			label2.setBounds(127, 3, 7, 29);
			label2.setFont(new Font("고딕", Font.BOLD, 20));
			timePanel.add(label2);

			
			EndHour= new JSpinner();
			EndHour.setBounds(140, 4, 47, 28);
			EndHour.setModel(new SpinnerNumberModel(now_hour, 0, 23, 1));
		    timePanel.add(EndHour);
									
							
			JLabel label3 = new JLabel("시");
			label3.setBounds(187, 8, 16, 22);
			label3.setFont(new Font("고딕", Font.BOLD, 15));
			timePanel.add(label3);

			EndMin=new JSpinner();
			EndMin.setBounds(202, 4, 47, 28);
			EndMin.setModel(new SpinnerNumberModel(now_min, 0, 59, 1));
			timePanel.add(EndMin);
											
			JLabel label4 = new JLabel("분");
			label4.setBounds(252, 8, 16, 22);
			label4.setFont(new Font("고딕", Font.BOLD, 15));
			timePanel.add(label4);
			
	}
	
	@Override
	public void btnPane() {		
	   JButton addBtn = new JButton(Images.OKIcon);
	   addBtn.setBounds(159, 425, 110, 50);
	   addBtn.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  int start_hour= (Integer)StartHour.getValue();		  
			  int start_min=(Integer)StartMin.getValue();
			  int end_hour=   (Integer)EndHour.getValue();
			  int end_min= (Integer)EndMin.getValue();
		     
			  try {
				starthour_Str=CalendarTimeFormat.intHour_DateformatStr(start_hour);
				startmin_Str=CalendarTimeFormat.intMin_DateformatStr(start_min);
				endhour_Str=CalendarTimeFormat.intHour_DateformatStr(end_hour);
				endmin_Str=CalendarTimeFormat.intMin_DateformatStr(end_min);
				
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		     
		     String tagname=(String) tagBox.getSelectedItem();
		      	 
		     for(int i=0;i<tags.size();i++){
		      	if(tagname.equals(tags.get(i).getData(1))){
		      	  tagId=tags.get(i).getData(0);
		      	  }
		      }
	      	 
	      		setEventData();
	      		 System.out.println("추가스케줄의 시작시간:"+event.getData(4));
	      		 //이벤트 데이터를 And에 보냄
	      		System.out.println("sendToAndroid FB");
	      		 
	      		JSONObject message = new JSONObject();
	      		 try {
				   message.put("type", "createSchedule");
				   message.put("SMaster", event.getData(1));
				   message.put("Sname", event.getData(2));
				   message.put("Place", event.getData(3));
				   message.put("StartTime", event.getData(4));
				   message.put("EndTime", event.getData(5));
				   message.put("Tagid", event.getData(6));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
	      		System.out.println(message.toString());    		 
	      		sendEventToAndroid(message.toString());	      		 
	      		 
	      		 //DB에서 이벤트 데이터 최신화 하고 캘린더를 새로고침
	      		 calendar.refreshSchedule();
	      		 dispose();       	 
	       }
	    });
	    contentPane.add(addBtn);
		
	}
	private void setEventData(){
		String []tok=date.split("/");
		String strYear=tok[0];
		String strMonth=tok[1];
		String strDay=tok[2];
		
    	 event=new EventData();
    	 event.setData(1,calendar.getUser().getData(0));//smaster - user id 가져옴 
    	 event.setData(2,super.ScheduleNameField.getText());
    	 event.setData(3,super.locationField.getText());
    	 event.setData(4,strYear+strMonth+strDay+starthour_Str+startmin_Str);//"yyyyMMddHHmm"
    	 event.setData(5,strYear+strMonth+strDay+endhour_Str+endmin_Str);//"yyyyMMddHHmm"
    	 event.setData(6,tagId);
     }
    /*스케줄추가->Android에 보내기*/////////
    private void sendEventToAndroid(String sql){
   	 /* Android에 이벤트 정보 보내기 */
			String url = "http://113.198.84.67/Calendar3S/FCMsendMyself.php";
			String token = calendar.getUser().getData(8);
			System.out.println("token : " + token);
			
			//토큰과 메세지를 파이어베이스에 보냄
			SendToFirebase stFB = new SendToFirebase(url, sql, token);
			stFB.start();// DB연결 스레드 시작
			try {
				stFB.join();// DB연결이 완료될때까지 대기
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			} 			
			System.out.println("result : " + stFB.getResult());
    }
 
}