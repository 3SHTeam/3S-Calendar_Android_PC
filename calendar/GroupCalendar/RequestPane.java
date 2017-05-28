package GroupCalendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.GroupData;
import Calendar.Data.MessageData;
import MonthCalendar.CalendarMain;

public class RequestPane extends JFrame{
	
	private JPanel RequestPanel;
	
	private static Calendar.DB.SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;
	
	public ArrayList<MessageData> messages = new ArrayList<MessageData>();
	public ArrayList<MessageData> getMessages() {return messages;}
	public void setMessages(ArrayList<MessageData> messages) {this.messages = messages;}
	
	private ArrayList<MessageData>request_GroupList=new ArrayList<MessageData>();
	public ArrayList<MessageData> getRequest_GroupList() {return request_GroupList;}
	private ArrayList<MessageData>request_ScheduleList=new ArrayList<MessageData>();
	public ArrayList<MessageData> getRequest_ScheduleList() {return request_ScheduleList;}

	private Vector<GroupData> allGroup_Vec=new Vector<GroupData>();
	private Font f=new Font("맑은 고딕",Font.BOLD,20);
	
	public RequestPane(Vector<GroupData> allGroup_Vec) {
		this.allGroup_Vec=allGroup_Vec;
		
		String sql = "receiver='" + 
				CalendarMain.getInstanace().getUser().getData(0) + "'";
		
		selectMyInviteToDB(sql);
		
		//message타입별로 구분 model에 add
		if(messages!=null){
			for(int i=0;i<messages.size();i++){
				MessageData message=messages.get(i);
				
				if(message.getData(3).equals("groupInvite")){
					request_GroupList.add(message);
				}
				else if(message.getData(3).equals("groupSchedule")){
					request_ScheduleList.add(message);
				}
			}
		
		}			
		RequestPanel = new JPanel();
		RequestPanel.setLayout(null);
		
		Request_GroupTable requestGTable=new Request_GroupTable(request_GroupList);
		RequestPanel.add(requestGTable.getRequestPane());
		
		Request_EventTable requestETable=new Request_EventTable(request_ScheduleList);
		RequestPanel.add(requestETable.getRequestPane());

	}
	
	public JPanel getRequestPanel() {return RequestPanel;}
	
	
	private void deleteInviteToDB(String sql) {
	      String url = "http://113.198.84.67/Calendar3S/DeleteMessage.php";

	      SendToDB stDB = new SendToDB(url, sql);
	      stDB.start();
	      try {
	         stDB.join();
	      } catch (InterruptedException e) {
	         System.out.println(e.toString());
	      }
	}
	
	   private void selectMyInviteToDB(String sql) {
	      String url = "http://113.198.84.67/Calendar3S/SelectMyMessage.php";

	      SendToDB stDB = new SendToDB(url, sql);
	      stDB.start();
	      try {
	         stDB.join();
	      } catch (InterruptedException e) {
	         System.out.println(e.toString());
	      }
	      
	      String result = stDB.getResult();// Json형식의 String값 가져옴
	      System.out.println(result);

	      jm = new JsonMaster();
	      jm.onPostExecute("SelectMyMessage", result);
	      this.messages = jm.getMessages();
	   }
}
