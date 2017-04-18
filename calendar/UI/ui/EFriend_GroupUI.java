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
	private JSplitPane groupInfo;	//그룹 정보(왼쪽 그룹이름, 오른쪽 해당 그룹의 스캐줄)
	private  JTable groupNameTable;
	private DefaultTableModel leftTable;
	private  JTable groupScheduleTable;
	private DefaultTableModel rightTable;
	
	private JPanel GroupPanel;

	private JMenuItem confirmCrew;
	private JMenuItem addFriend;
	private JMenuItem delete;
	private JPopupMenu Crew = new JPopupMenu();//오른쪽 마우스 클릭시

	private FriendData fd;
	
	private JPanel RequestPanel;
	private JTable RequestTable;

	private Panel Gp1;
	private JTextField Gp1_name;
	private JTextField Gp1_member;
	private Vector <GroupData>GroupVec=new Vector<GroupData>();
	private Vector <FriendData>FriendVec=new Vector<FriendData>();
	
	private DMonth_CalendarView calendar;	//스캐줄 정보를 가져오기 위해
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
		
	/*탭에서 친구 패널*/
		FriendPanel = new JPanel();		
		FriendPanel.setLayout(null);
		
		setFriendData("가가가","Google1@gmail.com",'M', 000000,00000000000,"scheduleM", "안녕 반가워~~~!!");
		FriendVec.add(fd);
		setFriendData("손성환","Google2@gmail.com",'M', 000000,00000000000,"ssh", "안녕 반가워~~~!!");
		FriendVec.add(fd);
		setFriendData("유우재","Google3@gmail.com",'M', 000000,00000000000,"yuj", "안녕 반가워~~~!!");
		FriendVec.add(fd);
		setFriendData("이임경","Google4@gmail.com",'W', 000000,00000000000,"llk", "안녕 반가워~~~!!");
		FriendVec.add(fd);
		setFriendData("최보윤","Google5@gmail.com",'W', 000000,00000000000,"cby", "안녕 반가워~~~!!");
		FriendVec.add(fd);
		
		String []columnNames={"이름","구글아이디","성별","생년월일","핸드폰번호","닉네임","한마디"};//테이블에 저장될 정보들

		DefaultTableModel friend_model=new DefaultTableModel(columnNames,0);//고정된 첫번째 줄
		
		for(int i=0; i<FriendVec.size(); i++){
			
			friend_model.addRow(new Object[] {FriendVec.get(i).getName(),FriendVec.get(i).getGoogleId(),FriendVec.get(i).getSex(),FriendVec.get(i).getBirth(),FriendVec.get(i).getCellPhoneNum(),FriendVec.get(i).getNickName(),FriendVec.get(i).getComment()});

		}
		
//		friend_model.addRow(new Object[]{"최보윤","01066311668","boyoon456123@naver.com","졸업프로젝트","반갑습니다."});
//		friend_model.addRow(new Object[]{"유후","01022362222","gdfge@naver.com","졸업프로젝트","반갑습니다."});
//		friend_model.addRow(new Object[]{"손성환","01022362222","gdfge@naver.com","졸업프로젝트","반갑습니다."});
//		friend_model.addRow(new Object[]{"이임경","01022362222","gdfge@naver.com","졸업프로젝트","반갑습니다."});
//		friend_model.addRow(new Object[]{"유우재","01022362222","gdfge@naver.com","졸업프로젝트","반갑습니다."});
		
		FriendTable = new JTable(friend_model);//친구리스트테이블
		FriendTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));			
		FriendTable.getColumnModel().getColumn(0).setPreferredWidth(100);//각 컬럼이름 사이즈 조정 
		FriendTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		FriendTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		FriendTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		FriendTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		FriendTable.getColumnModel().getColumn(6).setPreferredWidth(400);
		scrollPane = new JScrollPane(FriendTable);
		scrollPane.setBounds(0, 53, 1189, 628);
		FriendPanel.add(scrollPane);
	
		JButton inviteButton = new JButton("친구초대");//친구초대버튼
		inviteButton.setBounds(28, 20, 97, 23);
		FriendPanel.add(inviteButton);
		
		JButton addGroupButton = new JButton("그룹만들기");//그룹만들기버튼
		addGroupButton.setBounds(137, 20, 114, 23);
		FriendPanel.add(addGroupButton);
		addGroupButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            if(e.getSource()==addGroupButton){
	               MakeGroup.setVisible(true);
	               
	            }
	         }
	      });
		tab.addTab("친구", FriendPanel);
		
		//그룹 탭에서 왼쪽 테이블 왼쪽클릭 이벤트
		confirmCrew = new JMenuItem("소속원 보기");
		confirmCrew.addActionListener(this);
		Crew.add(confirmCrew);
		
		addFriend = new JMenuItem("친구 추가");
		addFriend.addActionListener(this);
		Crew.add(addFriend);
		
		delete = new JMenuItem("그룹 삭제");
		delete.addActionListener(this);
		Crew.add(delete);
		
		
	/*탭에서 그룹패널*/
		GroupPanel=new JPanel();	
		GroupPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 150, 100));
		groupInfo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		String leftColNames[] = { "그룹명"};  // Jtable 헤더는 1차원 배열
		leftTable = new DefaultTableModel(leftColNames,0){  //셀 수정 못하게 하는 부분
			 public boolean isCellEditable(int row, int column){
				    return false;
				 }
			};

		leftTable.setNumRows(0);
		
		String RightColNames[] = { "스케줄명", "시간"};  // Jtable 헤더는 1차원 배열
		rightTable = new DefaultTableModel(RightColNames,0){  //셀 수정 못하게 하는 부분
			 public boolean isCellEditable(int row, int column){
				    return false;
				 }
			};;
		rightTable.setNumRows(0);
	
		groupNameTable = new JTable(leftTable);   // 테이블 생성    
	    JScrollPane leftScrollPane = new JScrollPane(groupNameTable);  // 스크롤 기능 별도로 넣어줘야만 작동함
	    groupNameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//단일 선택
	    groupNameTable.addMouseListener(this);
	    groupNameTable.setShowVerticalLines(false);
	    
		groupScheduleTable = new JTable(rightTable);   // 테이블 생성    
	    JScrollPane rightScrollPane = new JScrollPane(groupScheduleTable);  // 스크롤 기능 별도로 넣어줘야만 작동함
	    groupScheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//단일 선택
	    groupScheduleTable.addMouseListener(this);
	    groupScheduleTable.setShowVerticalLines(false);
	    
	    groupInfo.setLeftComponent(leftScrollPane);// 테이블 적용
	    groupInfo.setRightComponent(rightScrollPane);
	    groupInfo.setDividerLocation(200); //디바이더(분리대) 위치 설정 


	    
//			Gp1 = new Panel();
//			Gp1.setBackground(Color.LIGHT_GRAY);
//			Gp1.setPreferredSize(new Dimension(350,150));
//			GroupPanel.add(Gp1);
//			Gp1.setLayout(null);
//			
//			Gp1_name = new JTextField();
//			Gp1_name.setText("그룹이름");
//			Gp1_name.setBounds(160, 35, 116, 21);
//			Gp1.add(Gp1_name);
//			Gp1_name.setColumns(10);
//			
//			Gp1_member = new JTextField();
//			Gp1_member.setText("멤버이름");
//			Gp1_member.setColumns(10);
//			Gp1_member.setBounds(160, 91, 116, 21);
//			Gp1.add(Gp1_member);
			tab.addTab("그룹", groupInfo);
		
	/*탭에서 요청메세지 패널*/
		RequestPanel=new JPanel();
		RequestPanel.setLayout(null);
		String []columnNames2={"소제목 명","그룹","날짜","장소","참석여부"};
		DefaultTableModel request_model=new DefaultTableModel(columnNames2,0);
		RequestTable = new JTable(request_model);//요청리스트테이블
		RequestTable.setPreferredScrollableViewportSize(new Dimension(1150, 600));	
		RequestTable.getColumnModel().getColumn(0).setPreferredWidth(300);//각 컬럼이름 사이즈 조정 
		RequestTable.getColumnModel().getColumn(1).setPreferredWidth(180);
		RequestTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		RequestTable.getColumnModel().getColumn(4).setPreferredWidth(300);	
		
		scrollPane = new JScrollPane(RequestTable);
		scrollPane.setBounds(0, 53, 1189, 628);
		RequestPanel.add(scrollPane);
		tab.addTab("요청메세지", RequestPanel);	
		
		getContentPane().add(tab);
  
		Button mypageBtn = new Button("마이페이지");
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
		
		Button settingBtn = new Button("환경설정");
		settingBtn.setBounds(615, 752, 115, 23);
		getContentPane().add(settingBtn);
		
		Button logoutBtn = new Button("로그아웃");
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
		if(e.getComponent() ==  groupNameTable){	//왼쪽 테이블에서 클릭
			if(e.getButton()==3){//오른쪽 마우스 클릭
				Crew.show((Component)e.getSource(), e.getX(), e.getY());
			}
			else
			{
				int row = groupNameTable.getSelectedRow();
				int col = groupNameTable.getSelectedColumn();
				String groupName = groupNameTable.getValueAt(row,col).toString();
				rightTable.setNumRows(0);//테이블 초기화
				
				/*DB에서 스케줄을 가져온다.//스케줄에 해당 그룹 스케줄이 있다면 오른쪽 테이블에 표기*/
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
//		else if(e.getComponent() ==  groupNameTable){	//오른쪽 테이블에서 클릭
//			if(e.getButton()==3){//오른쪽 마우스 클릭
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
	
	public void setFriendData(String name,String GoogleId, char sex, int birth, int cellPhoneNum, String nickName, String comment){//친구 추가(임시)
		fd = new FriendData(name, GoogleId, sex, birth, cellPhoneNum, nickName, comment);
	}
	   
}
