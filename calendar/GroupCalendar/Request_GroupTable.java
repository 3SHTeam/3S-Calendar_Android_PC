package GroupCalendar;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import Calendar.AcceptReject.Editor;
import Calendar.AcceptReject.Renderer;
import Calendar.Data.MessageData;
import Calendar.Data.RequestGroupData;


public class Request_GroupTable extends JFrame{
	private ArrayList<MessageData> request_GroupList=new ArrayList<MessageData>();
	private Request_GroupModel model;
	private	JPanel pane=new JPanel();
	private GroupPane groupPane;
	public GroupPane getGroupPane() {return groupPane;}
	public void setGroupPane(GroupPane groupPane) {this.groupPane = groupPane;}


	public Request_GroupTable(ArrayList<MessageData> request_GroupList) {
		this.request_GroupList=request_GroupList;
		
		model = new Request_GroupModel(request_GroupList);
		
		pane.setBounds(0,0,1200,300);
		pane.setLayout(null);
		JLabel AddGroupLabel = new JLabel("그룹 요청 메세지");
		AddGroupLabel.setFont(new Font("맑은 고딕",Font.BOLD,20));
		AddGroupLabel.setBounds(5, 5, 156, 40);
		pane.add(AddGroupLabel);
		
		if(request_GroupList!=null){
			TableFormatList();
		}
		
		 JTable table = new JTable(model);
         Renderer renderer = new Renderer();
         table.getColumnModel().getColumn(3).setCellRenderer(renderer);
         table.getColumnModel().getColumn(3).setCellEditor(new Editor());
         table.setRowHeight(renderer.getTableCellRendererComponent(table, null, true, true, 0, 0).getPreferredSize().height);
         
         table.setPreferredScrollableViewportSize(new Dimension(1150, 220));
         table.getColumnModel().getColumn(0).setPreferredWidth(250);// 각 컬럼이름 사이즈 조정
         table.getColumnModel().getColumn(1).setPreferredWidth(350);
         table.getColumnModel().getColumn(2).setPreferredWidth(300);
         table.getColumnModel().getColumn(3).setPreferredWidth(250);
		JScrollPane scrollPane1 = new JScrollPane(table);
		scrollPane1.setBounds(2, 47, 1189, 221);
		
		pane.add(scrollPane1);
	}


	public JPanel getRequestPane(){
		return pane;	
	}
	private void TableFormatList() {
		for(int i=0;i<request_GroupList.size();i++){
			MessageData msg=request_GroupList.get(i);
			model.add(new RequestGroupData(msg));
		}
	}
}
