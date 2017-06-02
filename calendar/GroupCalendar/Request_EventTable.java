package GroupCalendar;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Calendar.AcceptReject.Editor;
import Calendar.AcceptReject.Renderer;
import Calendar.Data.MessageData;
import Calendar.Data.RequestEventData;

public class Request_EventTable extends JFrame{
	private ArrayList<MessageData> request_EventList=new ArrayList<MessageData>();
	private Request_EventModel model = new Request_EventModel();
	private	JPanel pane=new JPanel();
	
	public Request_EventTable(ArrayList<MessageData> request_EventList,GroupPane gmain) {
		this.request_EventList=request_EventList;

		pane.setLayout(null);
		pane.setBounds(0,300,1000,400);
		JLabel AddEventLabel = new JLabel("스케줄 요청 메세지");
		AddEventLabel.setFont(new Font("맑은 고딕",Font.BOLD,20));
		AddEventLabel.setBounds(5, 5,400, 40);
		pane.add(AddEventLabel);
	
		if(request_EventList!=null){
			TableFormatList();
		}
		
			model.setGPane(gmain);
		 JTable table = new JTable(model);
         Renderer renderer = new Renderer();
         table.getColumnModel().getColumn(4).setCellRenderer(renderer);
         table.getColumnModel().getColumn(4).setCellEditor(new Editor());
         table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height);
         
         table.setPreferredScrollableViewportSize(new Dimension(975, 300));
         table.getColumnModel().getColumn(0).setPreferredWidth(250);// 각 컬럼이름 사이즈 조정
         table.getColumnModel().getColumn(1).setPreferredWidth(250);
         table.getColumnModel().getColumn(2).setPreferredWidth(200);
         table.getColumnModel().getColumn(3).setPreferredWidth(200);
         table.getColumnModel().getColumn(4).setPreferredWidth(250);
		JScrollPane scrollPane1 = new JScrollPane(table);
		scrollPane1.setBounds(2, 47, 980, 300);
		
		pane.add(scrollPane1);
	}



	public JPanel getRequestPane(){
		return pane;	
	}
	private void TableFormatList() {
		
		for(int i=0;i<request_EventList.size();i++){
			MessageData msg=request_EventList.get(i);
			model.add(new RequestEventData(msg));
		}
	}
}
