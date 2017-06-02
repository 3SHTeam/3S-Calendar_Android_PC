package GroupCalendar;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Calendar.DB.SendToDB;
import Calendar.Data.MessageData;
import Calendar.Data.RequestGroupData;
import MonthCalendar.CalendarMain;

public class Request_GroupModel extends AbstractTableModel {
	private List<RequestGroupData> datalist;
	private ArrayList<MessageData> groupMsgs=new ArrayList<MessageData>();
	
	private GroupPane gmain;
	public void setGPane(GroupPane gmain){this.gmain = gmain;}

    public Request_GroupModel(ArrayList<MessageData> groupMsgs) {
    	   datalist = new ArrayList<>();
    	   this.groupMsgs=groupMsgs;
    }

    @Override
    public String getColumnName(int column) {
        String value = null;
        switch (column) {
            case 0:
                value = "�ʴ��� �׷� ��";
                break;
            case 1:
                value = "�׷� �޼���";
                break;
            case 2:
                value = "���� ���";
                break;
            case 3:
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
        }
        return value;
    }

    @Override
    public int getRowCount() {
        return datalist.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	RequestGroupData obj = datalist.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
            	value = obj.getGroupName();
                break;
            case 1:
                value = obj.getGroupInviteMsg();
                break;
            case 2:
                value = obj.getSender();
                break;
            case 3:
                break;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 3) {

            RequestGroupData value = datalist.get(rowIndex);
            MessageData msg=value.getRequest_MsgData();
            if ("accept".equals(aValue)) {
                System.out.println("Accepted");
                //�׷쿡 ����
                String sql = "'" + CalendarMain.getInstanace().getUser().getData(0) 
               										+ "','" + msg.getData(5) + "'";
                joinGroup(sql);
                addGroupTag(msg.getData(6),msg.getData(5));
                
               //groupPane.refreshLeftNameList();

	      		gmain.freshpanel();
            } else {
                System.out.println("Rejected");
            } 
            //�޼����� ����
            deleteMessage(msg.getData(0));   
           fireTableCellUpdated(rowIndex, columnIndex);
           remove(value);
        }
    }


    public void add(RequestGroupData eRequestGroupData) {
        int startIndex = getRowCount();
        datalist.add(eRequestGroupData);
        fireTableRowsInserted(startIndex, getRowCount() - 1);
    }

    public void remove(RequestGroupData value) {
        int startIndex = datalist.indexOf(value);
        System.out.println("startIndex = " + startIndex);
        datalist.remove(value);
        fireTableRowsInserted(startIndex, startIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }
    
    private void joinGroup(String sql) {
		/* �׷� �����ϱ� */
		String url = "http://113.198.84.67/Calendar3S/JoinGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
    
    public void addGroupTag(String name, String Gid) {
		/* DB�� �׷� �±� �����ϱ� */
		String url = "http://113.198.84.67/Calendar3S/InsertTag.php";
		String Googleid = CalendarMain.getInstanace().getUser().getData(0);

		String message = "'" + Googleid + "','" + name + "','" + "#f6f9bd" + "','�������','15','"+Gid+"'";

		SendToDB stDB = new SendToDB(url, message);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
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
}