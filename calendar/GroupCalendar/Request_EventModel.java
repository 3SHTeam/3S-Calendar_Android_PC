package GroupCalendar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.json.JSONException;
import org.json.JSONObject;

import Calendar.DB.SendToDB;
import Calendar.DB.SendToFirebase;
import Calendar.Data.MessageData;
import Calendar.Data.RequestEventData;
import MonthCalendar.CalendarMain;

class Request_EventModel extends AbstractTableModel {
	private List<RequestEventData> datalist;
    public Request_EventModel() {
		datalist = new ArrayList<>();
	}

	@Override
    public String getColumnName(int column) {
        String value = null;
        switch (column) {
            case 0:
                value = "�׷� ��";
                break;
            case 1:
                value = "������ ��";
                break;
            case 2:
                value = "��¥";
                break;    
            case 3:
                value = "���";
                break;           
            case 4:
                value = "Accept/Reject";
                break;
        }
        return value;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class value = Object.class;
        switch (columnIndex) {
            case 0:
                value = String.class;
                break;
            case 1:
                value = String.class;
                break;
            case 2:
                value = String.class;
                break;
            case 3:
            	value=String.class;        
            	break;
        }
        return value;
    }

    @Override
    public int getRowCount() {
        return datalist.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	RequestEventData obj = datalist.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
            	value = obj.getGroupName();
                break;
            case 1:
                value = obj.getEventName();
                break;
            case 2:
                value = obj.getEventDate();
                break;
            case 3:
                value = obj.getLocation();
                break;       
            case 4:
                break;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 4) {

            System.out.println(aValue);

            RequestEventData value = datalist.get(rowIndex);
            MessageData msg=value.getRequest_MsgData();
            
            if ("accept".equals(aValue)) {
                System.out.println("Accepted");
                //�����ٿ� ����
                String[] tok = msg.getData(4).split("/");
                JSONObject message = new JSONObject();
	      		 try {
				   message.put("type", "joinGroupSchedule");
				   message.put("SMaster", msg.getData(1));
				   message.put("Sname", tok[0]);
				   message.put("Place", tok[1]);
				   message.put("StartTime", tok[2]);
				   message.put("EndTime", tok[3]);
				   message.put("Gid", msg.getData(5));
				   message.put("receiver",msg.getData(2));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
	      		 ///////////////////////////////////////�׷����� �����ٵ����� ��������groupPane.refresh
	      		sendEventToAndroid(message.toString());
                
            } else {
                System.out.println("Rejected");
            }
            //�޼����� ����
            deleteMessage(msg.getData(0));
            
            fireTableCellUpdated(rowIndex, columnIndex);
            
            remove(value);
        }
    }


    public void add(RequestEventData eRequestEventData) {
        int startIndex = getRowCount();
        datalist.add(eRequestEventData);
        fireTableRowsInserted(startIndex, getRowCount() - 1);
    }

    public void remove(RequestEventData value) {
        int startIndex = datalist.indexOf(value);
        System.out.println("startIndex = " + startIndex);
        datalist.remove(value);
        fireTableRowsInserted(startIndex, startIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }
    
    private void deleteMessage(String sql) {
		/* �׷� �����ϱ� */
		String url = "http://113.198.84.67/Calendar3S/DeleteMessage.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
    
    private void sendEventToAndroid(String sql){
      	 /* Android�� �̺�Ʈ ���� ������ */
   			String url = "http://113.198.84.67/Calendar3S/FCMsendMyself.php";
   			String token = CalendarMain.getInstanace().getUser().getData(8);
   			System.out.println("token : " + token);
   			
   			//��ū�� �޼����� ���̾�̽��� ����
   			SendToFirebase stFB = new SendToFirebase(url, sql, token);
   			stFB.start();// DB���� ������ ����
   			try {
   				stFB.join();// DB������ �Ϸ�ɶ����� ���
   			} catch (InterruptedException e) {
   				System.out.println(e.toString());
   			} 			
   			System.out.println("result : " + stFB.getResult());
       }

}
