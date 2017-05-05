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
	private JPopupMenu Crew = new JPopupMenu();// 오른쪽 마우스 클릭시
		
	private Vector<GroupData> GroupVec = new Vector<GroupData>();//그룹정보가 들어있는 전체그룹vec
	private Vector<String>nameVec;//그룹이름들
	private String rowName;//테이블 열이름


	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;
	
	public EGroup(EFriend_GroupMain fgMain) {
		this.fgMain=fgMain;
		
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

		setLeftModel();
		
		left_GroupsTable=new JTable(leftModel);
		left_GroupsTable.setRowHeight(40);
		left_GroupsTable.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 25));
		left_GroupsTable.setCellSelectionEnabled(true);  // 셀의 선택을 유효화
		ListSelectionModel cellSelectionModel = left_GroupsTable.getSelectionModel();// 선택 모델의 얻기
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 단일 선택
		
		left_GroupsTable.setShowVerticalLines(false);			
		JScrollPane leftScrollPane=new JScrollPane(left_GroupsTable);
		
		left_GroupsTable.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				int row=left_GroupsTable.rowAtPoint(e.getPoint());
				rowName=(String) leftModel.getValueAt(row, 0);
					if (e.getButton() == 3) {//오른쪽클릭
						
						Crew.show((Component) e.getSource(), e.getX(), e.getY());
						
					}
					else {//왼쪽클릭
					
//					int row = left_GroupsTable.getSelectedRow();
//					int col = left_GroupsTable.getSelectedColumn();
//					String groupName = left_GroupsTable.getValueAt(row, col).toString();
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

		addFriend = new JMenuItem("친구 추가");
		addFriend.addActionListener(this);
		Crew.add(addFriend);

		deleteGroup = new JMenuItem("그룹 삭제");
		deleteGroup.addActionListener(this);
		Crew.add(deleteGroup);		
		
		return leftScrollPane;			
	}

	
	private JScrollPane Right_GroupPanel() {
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
		
		JScrollPane rightScrollPane=new JScrollPane(right_groupScheduleTable);
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
			nameVec=new Vector<String>();
			nameVec.addElement(GroupVec.get(i).getData(1));
			leftModel.addRow(nameVec);
		}		
		
	}

	private void freshGroup() {
		/* DB에서 내 그룹들 가져오기 */
		url = "http://113.198.84.66/Calendar3S/SelectMyGroup.php";
		Googleid = getMyid();

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

		
		for(int i = 0;i<GroupVec.size();i++){
			//그룹멤버들의 이름을 Data엔set
			GroupVec.get(i).setUserIds_Arr();	
		}	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		  if(e.getSource() == confirmCrew)
		   {	
			  for(int i=0;i<GroupVec.size();i++){
				  if(rowName.equals(GroupVec.get(i).getData(1))){//테이블에있는 이름벡터와 전체 그룹벡터비교
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
		//생성된 그룹을 찾아서 가입한다.
		joinGroup("'"+ getMyid() + "','" + selectGroup(groupName,getMyid())+"'");
		
		//그룹의 모든것에 새로고침
	}
	
	private String selectGroup(String groupName,String id) {
		/* DB에서 그룹찾기 */
		String url = "http://113.198.84.66/Calendar3S/SelectMakeGroup.php";
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
	
	private void joinGroup(String sql) {
		/* 그룹 가입하기 */
		String url = "http://113.198.84.66/Calendar3S/JoinGroup.php";

		SendToDB stDB = new SendToDB(url, sql);
		stDB.start();// DB연결 스레드 시작
		try {
			stDB.join();// DB연결이 완료될때까지 대기
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
			groupNameLabel.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
			groupNameLabel.setBounds(50, 28, 400, 70);
			getContentPane().add(groupNameLabel);
			
			JList Memberlist = new JList(members.toArray());	
			Memberlist.setBounds(39, 102, 437, 511);
			Memberlist.setFixedCellHeight(40);
			JScrollPane scroll = new JScrollPane();
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setViewportView(Memberlist);
			getContentPane().add(Memberlist);
			
			JButton okButton = new JButton("확인");
			okButton.setBounds(202, 644, 125, 29);
			getContentPane().add(okButton);
		}
		
	}