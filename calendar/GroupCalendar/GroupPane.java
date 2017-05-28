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
	
	private JPopupMenu Crew = new JPopupMenu();// 오른쪽 마우스 클릭시

	private Vector<GroupData> GroupVec = new Vector<GroupData>();//그룹정보가 들어있는 전체그룹vec
	public Vector<GroupData> getAllGroupVec() {return GroupVec;}

	private Vector<String> GnameVec;//그룹이름들
	private String selectedName;//테이블 열이름

	private String user_id;
	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;

	
	public GroupPane() {
		user_id=CalendarMain.getInstanace().getUser().getData(0);

		freshGroup();
		
		GroupPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);	
		GroupPanel.setLeftComponent(Left_GroupPanel());//왼쪽:그룹들
		GroupPanel.setRightComponent(Right_GroupPanel());//오른쪽:한그룹의 스케줄
		GroupPanel.setDividerLocation(200);
	}

	private JScrollPane Left_GroupPanel() {
		//왼쪽 내가 속한 그룹만 표기		
		String leftColNames[] = { "그룹명" }; // Jtable 헤더는 1차원 배열
		leftModel = new DefaultTableModel(leftColNames, 0) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int row, int column) {return false;}
		};
		if(GroupVec!=null){
			setLeftModel();
		}	
		left_GroupsTable=new JTable(leftModel);
		left_GroupsTable.setRowHeight(40);
		left_GroupsTable.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
		left_GroupsTable.setCellSelectionEnabled(true);  // 셀의 선택을 유효화
		ListSelectionModel cellSelectionModel = left_GroupsTable.getSelectionModel();// 선택 모델의 얻기
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일 선택
		
		left_GroupsTable.setShowVerticalLines(false);			
		JScrollPane leftScrollPane=new JScrollPane(left_GroupsTable);
		
		left_GroupsTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int row=left_GroupsTable.rowAtPoint(e.getPoint());
				selectedName=(String) leftModel.getValueAt(row, 0);
				
					if (e.getButton() == 3) {//오른쪽클릭						
						Crew.show((Component) e.getSource(), e.getX(), e.getY());					
					}
					else {//왼쪽클릭
					
//					GroupNameLabel=new JLabel(rowName);
//					GroupNameLabel.setSize(GroupPanel.getWidth(),40);
					
//					rightModel.setNumRows(0);// 오른쪽테이블 초기화
	
					/* DB에서 스케줄을 가져온다.//스케줄에 해당 그룹 스케줄이 있다면 오른쪽 테이블에 표기 */
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
		
		// 그룹 탭에서 왼쪽 테이블 왼쪽클릭 이벤트
		confirmCrew = new JMenuItem("소속원 보기");
		confirmCrew.addActionListener(this);
		Crew.add(confirmCrew);

		addFriend = new JMenuItem("소속원 추가");
		addFriend.addActionListener(this);
		Crew.add(addFriend);

		deleteGroup = new JMenuItem("그룹 삭제");
		deleteGroup.addActionListener(this);
		Crew.add(deleteGroup);		
		
		addGroupEvent=new JMenuItem("그룹스케줄 추가");
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

		//오른쪽 그룹의 스케줄표기
		String RightColNames[] = { "스케줄명", "시간" }; // Jtable 헤더는 1차원 배열
		rightModel = new DefaultTableModel(RightColNames, 0) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		rightModel.setNumRows(0);
		
		right_groupScheduleTable=new JTable(rightModel);		
		right_groupScheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일선택
		right_groupScheduleTable.setShowVerticalLines(false);
		

		return rightScrollPane;
	}
	
	// 그룹 추가후 다시 db에서받기
	public void refreshLeftNameList() {
		leftModel.setNumRows(0); // 테이블 초기화
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
		/* DB에서 내 그룹들 가져오기 */
		url = "http://113.198.84.67/Calendar3S/SelectMyGroup.php";
		Googleid = user_id;
		
		System.out.println(Googleid);

		stDB = new SendToDB(url, Googleid);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		result = stDB.getResult();// Json형식의 String값 가져옴
		System.out.println(result);

		jm = new JsonMaster();
		jm.onPostExecute("SelectMyGroup", result);
		GroupVec = jm.getGroupVec();

		if(GroupVec != null){
			for(int i = 0;i<GroupVec.size();i++){
				//그룹멤버들의 이름을 Data엔set
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
			
		if(selectedName.equals(group.getData(1))){//테이블에있는 이름벡터와 전체 그룹벡터비교
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
					JOptionPane.showMessageDialog(null, "멤버를 추가할 권한이 없습니다.", "No Authorization", JOptionPane.ERROR_MESSAGE);
					}
				}
			
			else if(e.getSource() == deleteGroup){
				if(user_id.equals(GMaster)){
				  deleteGroup(Gid);
				  refreshLeftNameList();
				}
				else{
				   JOptionPane.showMessageDialog(null, "그룹을 삭제할 권한이 없습니다.", "No Authorization", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			else if(e.getSource() == addGroupEvent){
				if(user_id.equals(GMaster)){
					Add_GroupEvent addGroupEvent=new Add_GroupEvent(group);
					addGroupEvent.setVisible(true);
				}	
				else{
					   JOptionPane.showMessageDialog(null, "그룹스케줄을 추가할 권한이 없습니다.", "No Authorization", JOptionPane.ERROR_MESSAGE);
				}
			}
				
		}
	}
}

		
		
//		  if(e.getSource() == confirmCrew){	
//			  for(int i=0;i<GroupVec.size();i++){
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//테이블에있는 이름벡터와 전체 그룹벡터비교
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
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//테이블에있는 이름벡터와 전체 그룹벡터비교
//					  Gid = GroupVec.get(i).getData(0);
//				  }
//			  }	
//			   EAdd_Member addMember=new EAdd_Member(this,Gid);
//			   addMember.setVisible(true);
//	        }
//		  else if(e.getSource() == deleteGroup){		   
//			  for(int i=0;i<GroupVec.size();i++){
//				  if(rowName.equals(GroupVec.get(i).getData(1))){//테이블에있는 이름벡터와 전체 그룹벡터비교
//					  GMaster = GroupVec.get(i).getData(3);
//					  if(user_id.equals(GMaster)){
//						  //그룹스케줄 조인, 그룹태그,그룹조인,그룹을 모두 삭제
//						  deleteGroup(GroupVec.get(i).getData(0));
//						  refreshLeftNameList();
//					  }
//					  else{
//						  JOptionPane.showMessageDialog(null, "그룹을 삭제할 권한이 없습니다.", "No Authorization", JOptionPane.ERROR_MESSAGE);
//					  }
//				  }
//			  }	
//	       }
//	}


	public JSplitPane getGroupSpiltPanel() {return GroupPanel;}


	public void makeGroup(String groupName) {
		String Gid = selectGroup(groupName,user_id);
		//생성된 그룹을 찾아서 가입한다.
		joinGroup("'"+ user_id + "','" + Gid +"'");

		//그룹 스케줄 태그를 만들기
		addGroupTag(groupName,Gid);
		
	}
	
	private String selectGroup(String groupName,String id) {
		/* DB에서 그룹찾기 */
		String url = "http://113.198.84.67/Calendar3S/SelectMakeGroup.php";
		String sql = "Group_name = '" + groupName + "'&& GMaster = '"+id+"'"; 
		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		
		String result = stDB.getResult();// Json형식의 String값 가져옴
		System.out.println(result);

		jm = new JsonMaster();
		jm.onPostExecute("SelectMakeGroup", result);
		
		return jm.getResult();
	}
	
	private void deleteGroup(String sql) {
		/* 그룹 가입하기 */
		String url = "http://113.198.84.67/Calendar3S/DeleteGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}
	
	private void joinGroup(String sql) {
		/* 그룹 가입하기 */
		String url = "http://113.198.84.67/Calendar3S/JoinGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}

	public void addGroupTag(String name, String Gid) {
		/* DB에 그룹 태그 삽입하기 */
		url = "http://113.198.84.67/Calendar3S/InsertTag.php";
		Googleid = CalendarMain.getInstanace().getUser().getData(0);

		String message = "'" + Googleid + "','" + name + "','" + "#f6f9bd" + "','맑은고딕','15','"+Gid+"'";

		stDB = new SendToDB(url, message);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
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
		groupNameLabel.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
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


