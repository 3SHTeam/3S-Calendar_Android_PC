package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.JsonMaster;
import DB.SendToDB;
import Data.GroupData;

public class EGroup extends JFrame implements ActionListener{
	private EFriend_GroupMain fgMain;
	
	private JSplitPane GroupPanel; 
	
	private JScrollPane leftScrollPane;
	private JTable left_GroupsTable;
	private DefaultTableModel leftModel;
	
	private JScrollPane rightScrollpane;	
	private JTable right_groupScheduleTable;
	private	DefaultTableModel rightModel;
	
	private JMenuItem confirmCrew;
	private JMenuItem addFriend;
	private JMenuItem deleteGroup;
	private JPopupMenu Crew = new JPopupMenu();// ������ ���콺 Ŭ����
		
	private Vector<GroupData> GroupVec = new Vector<GroupData>();//�׷������� ����ִ� ��ü�׷�vec
	private Vector<String>nameVec;//�׷��̸���
	private String rowName;//���̺� ���̸�


	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;
	
	public EGroup(EFriend_GroupMain fgMain) {
		this.fgMain=fgMain;
		
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

		setLeftModel();
		
		left_GroupsTable=new JTable(leftModel);
		left_GroupsTable.setRowHeight(40);
		left_GroupsTable.setFont(new Font("���� ���", Font.CENTER_BASELINE, 25));
		left_GroupsTable.setCellSelectionEnabled(true);  // ���� ������ ��ȿȭ
		ListSelectionModel cellSelectionModel = left_GroupsTable.getSelectionModel();// ���� ���� ���
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���� ����
		
		left_GroupsTable.setShowVerticalLines(false);			
		JScrollPane leftScrollPane=new JScrollPane(left_GroupsTable);
		
		left_GroupsTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int row=left_GroupsTable.rowAtPoint(e.getPoint());
				rowName=(String) leftModel.getValueAt(row, 0);
					if (e.getButton() == 3) {//������Ŭ��
						
						Crew.show((Component) e.getSource(), e.getX(), e.getY());
						
					}
					else {//����Ŭ��
					
//					int row = left_GroupsTable.getSelectedRow();
//					int col = left_GroupsTable.getSelectedColumn();
//					String groupName = left_GroupsTable.getValueAt(row, col).toString();
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

		addFriend = new JMenuItem("ģ�� �߰�");
		addFriend.addActionListener(this);
		Crew.add(addFriend);

		deleteGroup = new JMenuItem("�׷� ����");
		deleteGroup.addActionListener(this);
		Crew.add(deleteGroup);		
		
		return leftScrollPane;			
	}

	
	private JScrollPane Right_GroupPanel() {
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
		
		JScrollPane rightScrollPane=new JScrollPane(right_groupScheduleTable);
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
			nameVec=new Vector<String>();
			nameVec.addElement(GroupVec.get(i).getData(1));
			leftModel.addRow(nameVec);
		}		
		
	}

	private void freshGroup() {
		/* DB���� �� �׷�� �������� */
		url = "http://113.198.84.66/Calendar3S/SelectMyGroup.php";
		Googleid = getMyid();

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

		
		for(int i = 0;i<GroupVec.size();i++){
			//�׷������� �̸��� Data��set
			GroupVec.get(i).setUserIds_Arr();	
		}	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		  if(e.getSource() == confirmCrew)
		   {	
			  for(int i=0;i<GroupVec.size();i++){
				  if(rowName.equals(GroupVec.get(i).getData(1))){//���̺��ִ� �̸����Ϳ� ��ü �׷캤�ͺ�
					  GroupData group=GroupVec.get(i);
					  ArrayList<String>memberlist= group.getUserIds_Arr();
					  MemberListLog m =new MemberListLog(group.getData(1),memberlist); 
					  m.setVisible(true);
				  }
			  }			  
	        }
		  
		  else if(e.getSource() == addFriend)
		   {		   
			   
	        }
		  else if(e.getSource() == deleteGroup)
		   {		   
			   
	        }
		
	}


	
	public String getMyid() {
		return  fgMain.getUserId();
	}

	public JSplitPane getGroupSpiltPanel() {return GroupPanel;}


	public void makeGroup(String groupName) {
		//������ �׷��� ã�Ƽ� �����Ѵ�.
		joinGroup("'"+ getMyid() + "','" + selectGroup(groupName,getMyid())+"'");
		
		//�׷��� ���Ϳ� ���ΰ�ħ
	}
	
	private String selectGroup(String groupName,String id) {
		/* DB���� �׷�ã�� */
		String url = "http://113.198.84.66/Calendar3S/SelectMakeGroup.php";
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
	
	private void joinGroup(String sql) {
		/* �׷� �����ϱ� */
		String url = "http://113.198.84.66/Calendar3S/JoinGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB���� ������ ����
		try {
			stDB.join();// DB������ �Ϸ�ɶ����� ���
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
}




	class MemberListLog extends JDialog{

		private ArrayList<String> members;
		private String groupName;
		public MemberListLog(String groupName,ArrayList<String>members) {
			this.members=members;
			this.groupName=groupName;
			
			setModal(true);
			setLocationRelativeTo(null);
			setTitle("MemberList");
			setLayout(null);
			setBounds(100, 100, 542, 754);
			setResizable(false);

			JLabel groupNameLabel = new JLabel();
			groupNameLabel.setText(groupName);
			groupNameLabel.setFont(new Font("���� ���� B", Font.PLAIN, 40));
			groupNameLabel.setBounds(50, 28, 400, 70);
			getContentPane().add(groupNameLabel);
			
			JList Memberlist = new JList(members.toArray());	
			Memberlist.setBounds(39, 102, 437, 511);
			Memberlist.setFixedCellHeight(40);
			JScrollPane scroll = new JScrollPane();
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setViewportView(Memberlist);
			getContentPane().add(Memberlist);
			
			JButton okButton = new JButton("Ȯ��");
			okButton.setBounds(202, 644, 125, 29);
			getContentPane().add(okButton);
		}
		
	}