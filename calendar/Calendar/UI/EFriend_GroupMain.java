package Calendar.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Calendar.Data.GroupData;


public class EFriend_GroupMain extends JFrame implements ActionListener{
	private JTabbedPane tab;
	private JPanel contentPane;
	private JSplitPane GroupPanel; // 그룹 정보(왼쪽 그룹이름, 오른쪽 해당 그룹의 스캐줄)
	private JPanel RequestPanel;

	private EGroup eGroup;
	private ERequest eRequest;

	private DMonth_CalendarMain calendar; // 스캐줄 정보를 가져오기 위해

	private JButton mypageBtn;
	private JButton calendarBtn;
	private JButton AddGroupBtn;
	private JButton logoutBtn;
	private EAdd_Group MakeGroup;
	private Vector<GroupData> allGroup_Vec=new Vector<GroupData>();

	public EFriend_GroupMain(DMonth_CalendarMain calendar) {
		this.calendar = calendar;		
		setTitle("Friend_Group");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1230, 850);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img=Images.mainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    g.drawImage(img, 0, 0, 1230, 850, null);
				   }
				  };	
			calendarBtn = new JButton(Images.CalendarIcon);
			calendarBtn.setBounds(321, 730, 110, 50);
		    calendarBtn.addActionListener(this);
			
			
			AddGroupBtn = new JButton(Images.AddGroupIcon);
			AddGroupBtn.setBounds(479, 730, 110, 50);
			AddGroupBtn.addActionListener(this);
		
			
			mypageBtn = new JButton(Images.ProFileIcon);
			mypageBtn.setBounds(625, 730, 110, 50);
			mypageBtn.addActionListener(this);



			logoutBtn = new JButton(Images.LogoutIcon);
			logoutBtn.setBounds(780, 730, 110, 50);
			logoutBtn.addActionListener(this);
			
		 EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                }
	          
		tab = new JTabbedPane();
		tab.setBounds(10, 0, 1200, 710);
		tab.setOpaque(false);
		//tab의 group부분
		eGroup=new EGroup();
		GroupPanel=eGroup.getGroupSpiltPanel();
		
		allGroup_Vec=eGroup.getAllGroupVec();
		
		//tab의 request부분
		eRequest=new ERequest(allGroup_Vec);
		RequestPanel=eRequest.getRequestPanel();
		
		tab.addTab("그룹", GroupPanel);	
		tab.setOpaque(true);
		GroupPanel.setOpaque(true);
		tab.addTab("요청메세지", RequestPanel);
		RequestPanel.setOpaque(true);
		tab.setOpaque(true);



		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
			
		contentPane.add(tab);
		contentPane.add(calendarBtn);
		contentPane.add(AddGroupBtn);
		contentPane.add(mypageBtn);
		contentPane.add(logoutBtn);
	      }	      
		 });
	



	}	
	

	public void setDMonth_CalendarView(DMonth_CalendarMain calendar) {
		this.calendar = calendar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mypageBtn) {
			FMypage mypage=new FMypage(DMonth_CalendarMain.getInstanace().getUser());
			mypage.setVisible(true);
			dispose();
		}
		
		
		if (e.getSource() == calendarBtn) {
			calendar.refreshSchedule();
			calendar.freshTag();
			calendar.refreshTag();
			calendar.setVisible(true);
			dispose();
		}
		
		if (e.getSource() == AddGroupBtn) {
			MakeGroup=new EAdd_Group(eGroup);
			MakeGroup.setVisible(true);
		}
		if (e.getSource() == logoutBtn) {

		}
	}


	public String getUserId() {
		return calendar.getUser().getData(0);
	}

}
