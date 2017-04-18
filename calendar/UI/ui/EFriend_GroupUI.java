package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
public class EFriend_GroupUI extends JFrame implements MouseListener,ActionListener{
	private JTabbedPane tab;
	
	private JPanel FriendPanel;
	private JTable FriendTable;
	private JScrollPane scrollPane;
	private JSplitPane groupInfo;	//�׷� ����(���� �׷��̸�, ������ �ش� �׷��� ��ĳ��)
	private  JTable groupNameTable;
	private DefaultTableModel leftTable;
	private  JTable groupScheduleTable;
	private DefaultTableModel rightTable;
	
	private JPanel GroupPanel;

	private JMenuItem confirmCrew;
	private JMenuItem addFriend;
	private JMenuItem delete;
	private JPopupMenu Crew = new JPopupMenu();//������ ���콺 Ŭ����

	private FriendData fd;
	
	private JPanel RequestPanel;
	private JTable RequestTable;

	private Panel Gp1;
	private JTextField Gp1_name;
	private JTextField Gp1_member;
	private Vector <GroupData>GroupVec=new Vector<GroupData>();
	private Vector <FriendData>FriendVec=new Vector<FriendData>();
	
	private DMonth_CalendarView calendar;	//��ĳ�� ������ �������� ����
	EAdd_Group MakeGroup=new EAdd_Group(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EFriend_GroupUI frame = new EFriend_GroupUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EFriend_GroupUI() {
		setTitle("Friend_Group");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1210, 850);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);	
		tab=new JTabbedPane();
		tab.setBounds(0, 0, 1194, 710);
		
	/*�ǿ��� ģ�� �г�*/
		FriendPanel = new JPanel();		
		FriendPanel.setLayout(null);
		
		setFriendData("������","Google1@gmail.com",'M', 000000,00000000000,"scheduleM", "�ȳ� �ݰ���~~~!!");
		FriendVec.add(fd);
		setFriendData("�ռ�ȯ","Google2@gmail.com",'M', 000000,00000000000,"ssh", "�ȳ� �ݰ���~~~!!");
		FriendVec.add(fd);
		setFriendData("������","Google3@gmail.com",'M', 000000,00000000000,"yuj", "�ȳ� �ݰ���~~~!!");
		FriendVec.add(fd);
		setFriendData("���Ӱ�","Google4@gmail.com",'W', 000000,00000000000,"llk", "�ȳ� �ݰ���~~~!!");
		FriendVec.add(fd);
		setFriendData("�ֺ���","Google5@gmail.com",'W', 000000,00000000000,"cby", "�ȳ� �ݰ���~~~!!");
		FriendVec.add(fd);
		
		String []columnNames={"�̸�","���۾��̵�","����","�������","�ڵ�����ȣ","�г���","�Ѹ���"};//���̺� ����� ������

		DefaultTableModel friend_model=new DefaultTableModel(columnNames,0);//������ ù��° ��
		
		for(int i=0; i<FriendVec.size(); i++){
			
			friend_model.addRow(new Object[] {FriendVec.get(i).getName(),FriendVec.get(i).getGoogleId(),FriendVec.get(i).getSex(),FriendVec.get(i).getBirth(),FriendVec.get(i).getCellPhoneNum(),FriendVec.get(i).getNickName(),FriendVec.get(i).getComment()});

		}
		
//		friend_model.addRow(new Object[]{"�ֺ���","01066311668","boyoon456123@naver.com","����������Ʈ","�ݰ����ϴ�."});
//		friend_model.addRow(new Object[]{"����","01022362222","gdfge@naver.com","����������Ʈ","�ݰ����ϴ�."});
//		friend_model.addRow(new Object[]{"�ռ�ȯ","01022362222","gdfge@naver.com","����������Ʈ","�ݰ����ϴ�."});
//		friend_model.addRow(new Object[]{"���Ӱ�","01022362222","gdfge@naver.com","����������Ʈ","�ݰ����ϴ�."});
//		friend_model.addRow(new Object[]{"������","01022362222","gdfge@naver.com","����������Ʈ","�ݰ����ϴ�."});
		
		FriendTable = new JTable(friend_model);//ģ������Ʈ���̺�
		FriendTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));			
		FriendTable.getColumnModel().getColumn(0).setPreferredWidth(100);//�� �÷��̸� ������ ���� 
		FriendTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		FriendTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		FriendTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		FriendTable.getColumnModel().getColumn(6).setPreferredWidth(400);
		scrollPane = new JScrollPane(FriendTable);
		scrollPane.setBounds(0, 53, 1189, 628);
		FriendPanel.add(scrollPane);
	
		JButton inviteButton = new JButton("ģ���ʴ�");//ģ���ʴ��ư
		inviteButton.setBounds(28, 20, 97, 23);
		FriendPanel.add(inviteButton);
		
		JButton addGroupButton = new JButton("�׷츸���");//�׷츸����ư
		addGroupButton.setBounds(137, 20, 114, 23);
		FriendPanel.add(addGroupButton);
		addGroupButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            if(e.getSource()==addGroupButton){
	               MakeGroup.setVisible(true);
	               
	            }
	         }
	      });
		tab.addTab("ģ��", FriendPanel);
		
		//�׷� �ǿ��� ���� ���̺� ����Ŭ�� �̺�Ʈ
		confirmCrew = new JMenuItem("�Ҽӿ� ����");
		confirmCrew.addActionListener(this);
		Crew.add(confirmCrew);
		
		addFriend = new JMenuItem("ģ�� �߰�");
		addFriend.addActionListener(this);
		Crew.add(addFriend);
		
		delete = new JMenuItem("�׷� ����");
		delete.addActionListener(this);
		Crew.add(delete);
		
		
	/*�ǿ��� �׷��г�*/
		GroupPanel=new JPanel();	
		GroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 100));
		groupInfo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		String leftColNames[] = { "�׷��"};  // Jtable ����� 1���� �迭
		leftTable = new DefaultTableModel(leftColNames,0){  //�� ���� ���ϰ� �ϴ� �κ�
			 public boolean isCellEditable(int row, int column){
				    return false;
				 }
			};

		leftTable.setNumRows(0);
		
		String RightColNames[] = { "�����ٸ�", "�ð�"};  // Jtable ����� 1���� �迭
		rightTable = new DefaultTableModel(RightColNames,0){  //�� ���� ���ϰ� �ϴ� �κ�
			 public boolean isCellEditable(int row, int column){
				    return false;
				 }
			};;
		rightTable.setNumRows(0);
	
		groupNameTable = new JTable(leftTable);   // ���̺� ����    
	    JScrollPane leftScrollPane = new JScrollPane(groupNameTable);  // ��ũ�� ��� ������ �־���߸� �۵���
	    groupNameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//���� ����
	    groupNameTable.addMouseListener(this);
	    groupNameTable.setShowVerticalLines(false);
	    
		groupScheduleTable = new JTable(rightTable);   // ���̺� ����    
	    JScrollPane rightScrollPane = new JScrollPane(groupScheduleTable);  // ��ũ�� ��� ������ �־���߸� �۵���
	    groupScheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//���� ����
	    groupScheduleTable.addMouseListener(this);
	    groupScheduleTable.setShowVerticalLines(false);
	    
	    groupInfo.setLeftComponent(leftScrollPane);// ���̺� ����
	    groupInfo.setRightComponent(rightScrollPane);
	    groupInfo.setDividerLocation(200); //����̴�(�и���) ��ġ ���� 


	    
//			Gp1 = new Panel();
//			Gp1.setBackground(Color.LIGHT_GRAY);
//			Gp1.setPreferredSize(new Dimension(350,150));
//			GroupPanel.add(Gp1);
//			Gp1.setLayout(null);
//			
//			Gp1_name = new JTextField();
//			Gp1_name.setText("�׷��̸�");
//			Gp1_name.setBounds(160, 35, 116, 21);
//			Gp1.add(Gp1_name);
//			Gp1_name.setColumns(10);
//			
//			Gp1_member = new JTextField();
//			Gp1_member.setText("����̸�");
//			Gp1_member.setColumns(10);
//			Gp1_member.setBounds(160, 91, 116, 21);
//			Gp1.add(Gp1_member);
			tab.addTab("�׷�", groupInfo);
		
	/*�ǿ��� ��û�޼��� �г�*/
		RequestPanel=new JPanel();
		RequestPanel.setLayout(null);
		String []columnNames2={"������ ��","�׷�","��¥","���","��������"};
		DefaultTableModel request_model=new DefaultTableModel(columnNames2,0);
		RequestTable = new JTable(request_model);//��û����Ʈ���̺�
		RequestTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));	
		RequestTable.getColumnModel().getColumn(0).setPreferredWidth(300);//�� �÷��̸� ������ ���� 
		RequestTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		RequestTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(4).setPreferredWidth(300);	
		
		scrollPane = new JScrollPane(RequestTable);
		scrollPane.setBounds(0, 53, 1189, 628);
		RequestPanel.add(scrollPane);
		tab.addTab("��û�޼���", RequestPanel);	
		
		getContentPane().add(tab);
  
		Button mypageBtn = new Button("����������");
		mypageBtn.setBounds(321, 752, 115, 23);
		getContentPane().add(mypageBtn);
		mypageBtn.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            if(e.getSource()==mypageBtn){
	            	CTreeView mypage = (CTreeView) CTreeView.getInstanace();
	            	mypage.setVisible(true);
	            	dispose();
	            }
	         }
	      });
		
		
		Button friendgroupBtn = new Button("Friend&Group");
		friendgroupBtn.setBounds(469, 752, 115, 23);
		getContentPane().add(friendgroupBtn);
		
		Button settingBtn = new Button("ȯ�漳��");
		settingBtn.setBounds(615, 752, 115, 23);
		getContentPane().add(settingBtn);
		
		Button logoutBtn = new Button("�α׾ƿ�");
		logoutBtn.setBounds(758, 752, 115, 23);
		getContentPane().add(logoutBtn);
	}
	


	//addGroup
	public void addGroup(GroupData groupData){
		GroupVec.addElement(groupData);
	}
	
	public String getGroupIntro(int index){
		return GroupVec.elementAt(index).getGroupIntro();
	}
	public String getGroupName(int index){
		return GroupVec.elementAt(index).getGroupName();
	}
	public int getGroupNum(){
		return GroupVec.size();
	}
	
	public DefaultTableModel getLeftTable(){
		return leftTable;
	}
	
	public void setGroupNameTable(Vector VecInfo){
		leftTable.addRow(VecInfo);
	}
	
	public void setDMonth_CalendarView(DMonth_CalendarView calendar){
		this.calendar = calendar;
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		if(e.getComponent() ==  groupNameTable){	//���� ���̺��� Ŭ��
			if(e.getButton()==3){//������ ���콺 Ŭ��
				Crew.show((Component)e.getSource(), e.getX(), e.getY());
			}
			else
			{
				int row = groupNameTable.getSelectedRow();
				int col = groupNameTable.getSelectedColumn();
				String groupName = groupNameTable.getValueAt(row,col).toString();
				rightTable.setNumRows(0);//���̺� �ʱ�ȭ
				
				/*DB���� �������� �����´�.//�����ٿ� �ش� �׷� �������� �ִٸ� ������ ���̺� ǥ��*/
//				for(int i=0; i<calendar.getScheduleData().size();i++){
//					
//					if(calendar.getScheduleGroupName(i).equals(groupName)){	
//						Vector<String> VecInfo=new Vector<String>();
//						VecInfo.add(calendar.getScheduleName(i));
//						VecInfo.add(calendar.getScheduleDate(i));
//						rightTable.addRow(VecInfo);
//			        	
//					}
//					
//				}
//				
//				
//				System.out.println(groupName);
			}
		}
//		else if(e.getComponent() ==  groupNameTable){	//������ ���̺��� Ŭ��
//			if(e.getButton()==3){//������ ���콺 Ŭ��
//
//			}
//			else
//			{
//
//			}
//		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFriendData(String name,String GoogleId, char sex, int birth, int cellPhoneNum, String nickName, String comment){//ģ�� �߰�(�ӽ�)
		fd = new FriendData(name, GoogleId, sex, birth, cellPhoneNum, nickName, comment);
	}
	   
}
