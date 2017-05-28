package GroupCalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.GroupData;
import Calendar.UI.Images;
import MonthCalendar.CalendarMain;

public class GroupPane extends JFrame implements ActionListener{
	private GroupMain fgMain;
	
	private JSplitPane GroupPanel; 
	
	private JTable left_GroupsTable;
	private DefaultTableModel leftModel;
	
	private JTable right_groupScheduleTable;
	private	DefaultTableModel rightModel;
	
	private JMenuItem confirmCrew;
	private JMenuItem addFriend;
	private JMenuItem deleteGroup;
	private JMenuItem addGroupEvent;
	
	private JPopupMenu Crew = new JPopupMenu();// ������ ���콺 Ŭ����

	private Vector<GroupData> GroupVec = new Vector<GroupData>();//�׷������� ����ִ� ��ü�׷�vec
	public Vector<GroupData> getAllGroupVec() {return GroupVec;}

	private Vector<String> GnameVec;//�׷��̸���
	private String selectedName;//���̺� ���̸�

	private String user_id;
	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;

	
	public GroupPane() {
		user_id=CalendarMain.getInstanace().getUser().getData(0);

		freshGroup();
		
		GroupPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);	
		GroupPanel.setLeftComponent(Left_GroupPanel());//����:�׷��
		GroupPanel.setRightComponent(Right_GroupPanel());//������:�ѱ׷��� ������
		GroupPanel.setDividerLocation(200);
	}

	private JScrollPane Left_GroupPanel() {
		//���� ���� ���� �׷츸 ǥ��		
		String leftColNames[] = { "�׷��" }; // Jtable ����� 1���� �迭
		leftModel = new DefaultTableModel(leftColNames, 0) { // �� ���� ���ϰ� �ϴ� �κ�
			public boolean isCellEditable(int row, int column) {return false;}
		};
		if(GroupVec!=null){
			setLeftModel();
		}	
		left_GroupsTable=new JTable(leftModel);
		left_GroupsTable.setRowHeight(40);
		left_GroupsTable.setFont(new Font("���� ���� B", Font.PLAIN, 25));
		left_GroupsTable.setCellSelectionEnabled(true);  // ���� ������ ��ȿȭ
		ListSelectionModel cellSelectionModel = left_GroupsTable.getSelectionModel();// ���� ���� ���
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���� ����
		
		left_GroupsTable.setShowVerticalLines(false);			
		JScrollPane leftScrollPane=new JScrollPane(left_GroupsTable);
		
		left_GroupsTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int row=left_GroupsTable.rowAtPoint(e.getPoint());
				selectedName=(String) leftModel.getValueAt(row, 0);
				
					if (e.getButton() == 3) {//������Ŭ��						
						Crew.show((Component) e.getSource(), e.getX(), e.getY());					
					}
					else {//����Ŭ��
					
//					GroupNameLabel=new JLabel(rowName);
//					GroupNameLabel.setSize(GroupPanel.getWidth(),40);
					
//					rightModel.setNumRows(0);// ���������̺� �ʱ�ȭ
	
					/* DB���� �������� �����´�.//�����ٿ� �ش� �׷� �������� �ִٸ� ������ ���̺� ǥ�� */
					// for(int i=0; i<calendar.getScheduleData().size();i++){
					//
					// if(calendar.getScheduleGroupName(i).equals(groupName)){
					// Vector<String> VecInfo=new Vector<String>();
					// VecInfo.add(calendar.getScheduleName(i));
					// VecInfo.add(calendar.getScheduleDate(i));
					// rightTable.addRow(VecInfo);
					//
					// }
					//
					// }
					}
			}
		});
		
		// �׷� �ǿ��� ���� ���̺� ����Ŭ�� �̺�Ʈ
		confirmCrew = new JMenuItem("�Ҽӿ� ����");
		confirmCrew.addActionListener(this);
		Crew.add(confirmCrew);

		addFriend = new JMenuItem("�Ҽӿ� �߰�");
		addFriend.addActionListener(this);
		Crew.add(addFriend);

		deleteGroup = new JMenuItem("�׷� ����");
		deleteGroup.addActionListener(this);
		Crew.add(deleteGroup);		
		
		addGroupEvent=new JMenuItem("�׷콺���� �߰�");
		addGroupEvent.addActionListener(this);
		Crew.add(addGroupEvent);		
		
		return leftScrollPane;			
	}

	
	private JScrollPane Right_GroupPanel() {
	//	JPanel GroupEventsPane=new JPanel();
	//	GroupEventsPane.setLayout(new BorderLayout());
		
		JScrollPane rightScrollPane=new JScrollPane(right_groupScheduleTable);
	//	GroupEventsPane.add(GroupNameLabel,BorderLayout.NORTH);
	//	GroupEventsPane.add(rightScrollPane,BorderLayout.CENTER);

		//������ �׷��� ������ǥ��
		String RightColNames[] = { "�����ٸ�", "�ð�" }; // Jtable ����� 1���� �迭
		rightModel = new DefaultTableModel(RightColNames, 0) { // �� ���� ���ϰ� �ϴ� �κ�
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		rightModel.setNumRows(0);
		
		right_groupScheduleTable=new JTable(rightModel);		
		right_groupScheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���ϼ���
		right_groupScheduleTable.setShowVerticalLines(false);
		

		return rightScrollPane;
	}
	
	// �׷� �߰��� �ٽ� db�����ޱ�
	public void refreshLeftNameList() {
		leftModel.setNumRows(0); // ���̺� �ʱ�ȭ
		freshGroup();
		setLeftModel();
	}
	private void setLeftModel(){
		for(int i=0;i<GroupVec.size();i++){
			GnameVec=new Vector<String>();
			GnameVec.addElement(GroupVec.get(i).getData(1));
			leftModel.addRow(GnameVec);
		}		
		
	}

	private void freshGroup() {
		/* DB���� �� �׷�� �������� */
		url = "http://113.198.84.67/Calendar3S/SelectMyGroup.php";
		Googleid = user_id;
		
		System.out.println(Googleid);

		stDB = new SendToDB(url, Googleid);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		result = stDB.getResult();// Json������ String�� ������
		System.out.println(result);

		jm = new JsonMaster();
		jm.onPostExecute("SelectMyGroup", result);
		GroupVec = jm.getGroupVec();

		if(GroupVec != null){
			for(int i = 0;i<GroupVec.size();i++){
				//�׷������� �̸��� Data��set
				GroupVec.get(i).setUserIds_Arr();	
			}	
		}
	}


	@Override
public void actionPerformed(ActionEvent e) {
	String GMaster="NULL";
	String Gid = "NULL";
	GroupData group;
	String Gname="NULL";
	
	for(int i=0;i<GroupVec.size();i++){	
			group=GroupVec.get(i);
			Gid=group.getData(0);
			Gname=group.getData(1);
			GMaster = group.getData(3);
			
		if(selectedName.equals(group.getData(1))){//���̺��ִ� �̸����Ϳ� ��ü �׷캤�ͺ�
			if(e.getSource() == confirmCrew){				 
				ArrayList<String>memberlist= group.getUserIds_Arr();
				EMemberListLog m =new EMemberListLog(Gname,memberlist); 
				m.setVisible(true);
				}
			
			else if(e.getSource() == addFriend){
				if(user_id.equals(GMaster)){
				   Add_Member addMember=new Add_Member(this,Gid,Gname);
				   addMember.setVisible(true);
				}		
				else{
					JOptionPane.showMessageDialog(null, "����� �߰��� ������ �����ϴ�.", "No Authorization", JOptionPane.ERROR_MESSAGE);
					}
				}
			
			else if(e.getSource() == deleteGroup){
				if(user_id.equals(GMaster)){
				  deleteGroup(Gid);
				  refreshLeftNameList();
				}
				else{
				   JOptionPane.showMessageDialog(null, "�׷��� ������ ������ �����ϴ�.", "No Authorization", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else if(e.getSource() == addGroupEvent){
				if(user_id.equals(GMaster)){
					Add_GroupEvent addGroupEvent=new Add_GroupEvent(group);
					addGroupEvent.setVisible(true);
				}	
				else{
					   JOptionPane.showMessageDialog(null, "�׷콺������ �߰��� ������ �����ϴ�.", "No Authorization", JOptionPane.ERROR_MESSAGE);
				}
			}
				
		}
	}
}

		
		
//		  if(e.getSource() == confirmCrew){	
//			  for(int i=0;i<GroupVec.size();i++){
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//���̺��ִ� �̸����Ϳ� ��ü �׷캤�ͺ�
//					  GroupData group=GroupVec.get(i);
//					  ArrayList<String>memberlist= group.getUserIds_Arr();
//					  EMemberListLog m =new EMemberListLog(group.getData(1),memberlist); 
//					  m.setVisible(true);
//				  }
//			  }			  
//	        }
//		  
//		  else if(e.getSource() == addFriend){		  
//			  String Gid = "NULL";
//			  for(int i=0;i<GroupVec.size();i++){
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//���̺��ִ� �̸����Ϳ� ��ü �׷캤�ͺ�
//					  Gid = GroupVec.get(i).getData(0);
//				  }
//			  }	
//			   EAdd_Member addMember=new EAdd_Member(this,Gid);
//			   addMember.setVisible(true);
//	        }
//		  else if(e.getSource() == deleteGroup){		   
//			  for(int i=0;i<GroupVec.size();i++){
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//���̺��ִ� �̸����Ϳ� ��ü �׷캤�ͺ�
//					  GMaster = GroupVec.get(i).getData(3);
//					  if(user_id.equals(GMaster)){
//						  //�׷콺���� ����, �׷��±�,�׷�����,�׷��� ��� ����
//						  deleteGroup(GroupVec.get(i).getData(0));
//						  refreshLeftNameList();
//					  }
//					  else{
//						  JOptionPane.showMessageDialog(null, "�׷��� ������ ������ �����ϴ�.", "No Authorization", JOptionPane.ERROR_MESSAGE);
//					  }
//				  }
//			  }	
//	       }
//	}


	public JSplitPane getGroupSpiltPanel() {return GroupPanel;}


	public void makeGroup(String groupName) {
		String Gid = selectGroup(groupName,user_id);
		//������ �׷��� ã�Ƽ� �����Ѵ�.
		joinGroup("'"+ user_id + "','" + Gid +"'");

		//�׷� ������ �±׸� �����
		addGroupTag(groupName,Gid);
		
	}
	
	private String selectGroup(String groupName,String id) {
		/* DB���� �׷�ã�� */
		String url = "http://113.198.84.67/Calendar3S/SelectMakeGroup.php";
		String sql = "Group_name = '" + groupName + "'&& GMaster = '"+id+"'"; 
		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		
		String result = stDB.getResult();// Json������ String�� ������
		System.out.println(result);

		jm = new JsonMaster();
		jm.onPostExecute("SelectMakeGroup", result);
		
		return jm.getResult();
	}
	
	private void deleteGroup(String sql) {
		/* �׷� �����ϱ� */
		String url = "http://113.198.84.67/Calendar3S/DeleteGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
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
		url = "http://113.198.84.67/Calendar3S/InsertTag.php";
		Googleid = CalendarMain.getInstanace().getUser().getData(0);

		String message = "'" + Googleid + "','" + name + "','" + "#f6f9bd" + "','�������','15','"+Gid+"'";

		stDB = new SendToDB(url, message);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
	
	
}

class EMemberListLog extends JDialog{
	private JPanel contentPane;
	private ArrayList<String> members;
	private String groupName;
	public EMemberListLog(String groupName,ArrayList<String>members) {
		this.members=members;
		this.groupName=groupName;
		Image img=Images.smainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    //super.paintComponents(g);
				    g.drawImage(img, 0, 0, 542, 754, null);
				   }
				  };
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setTitle("MemberList");
		setLayout(null);
		setBounds(100, 100, 542, 754);
		setResizable(false);

		
		
		JLabel groupNameLabel = new JLabel();
		groupNameLabel.setText(groupName);
		groupNameLabel.setFont(new Font("���� ���� B", Font.PLAIN, 40));
		groupNameLabel.setBounds(50, 28, 400, 70);
		contentPane.add(groupNameLabel);
		
		JList Memberlist = new JList(members.toArray());	
		Memberlist.setBounds(39, 102, 437, 511);
		Memberlist.setFixedCellHeight(40);
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportView(Memberlist);
		contentPane.add(Memberlist);
		
		JButton okButton = new JButton(Images.OKIcon);
		okButton.setBounds(202, 634, 110, 50);
		contentPane.add(okButton);
	}	
}


