package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import Data.EventData;


public class CTreeView extends JFrame implements ActionListener{

   private JPanel contentPane;
   private JButton mypageBtn;
   private JButton friendgroupBtn;
   private JButton SettingBtn;
   private JButton LogoutBtn;
   private JButton CalendarIcon;
 
   public static final CTreeView instance =new CTreeView();
   
   private EFriend_GroupUI fgUI = new EFriend_GroupUI();

   DMonth_CalendarView calendar=(DMonth_CalendarView) DMonth_CalendarView.getInstanace(); 
   public CTreeView() {
	   setLocationRelativeTo(null);
	   setResizable(false);
      setTitle("TreeView");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 688, 867);
      contentPane = new JPanel();
      contentPane.setBackground(Color.WHITE);
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      getContentPane().setLayout(null);
      
      mypageBtn = new JButton("마이페이지");
      mypageBtn.setBounds(546, 217, 114, 23);
      mypageBtn.addActionListener(this);
      contentPane.add(mypageBtn);
      
      friendgroupBtn = new JButton("Friend&Group");
      friendgroupBtn.setBounds(546, 328, 114, 23);
      friendgroupBtn.addActionListener(this);
      contentPane.add(friendgroupBtn);
      
      SettingBtn = new JButton("환경설정");
      SettingBtn.setBounds(546, 432, 114, 23);
      SettingBtn.addActionListener(this);
      contentPane.add(SettingBtn);
      
      LogoutBtn = new JButton("로그아웃");
      LogoutBtn.setBounds(546, 546, 114, 23);
      LogoutBtn.addActionListener(this);
      contentPane.add(LogoutBtn);
      
     ImageIcon calendarIcon=new ImageIcon("image/ic_event_available_black_48dp_1x.png");
      CalendarIcon = new JButton();
      CalendarIcon.setBounds(570, 59, 48, 48);
      CalendarIcon.addActionListener(this);
      contentPane.add(CalendarIcon);
      
      /*일정 treeTable*/
      JPanel SchedulePanel = new JPanel();
      SchedulePanel.setBackground(SystemColor.activeCaption);
      	//SchedulePanel.setBackground(Color.LIGHT_GRAY);
        SchedulePanel.setBounds(12, 25, 518, 774);
      contentPane.add(SchedulePanel);
      SchedulePanel.setLayout(new BorderLayout());
   
//      
//      DefaultMutableTreeNode CalendarRoot=new DefaultMutableTreeNode("Calendar");
//      DefaultMutableTreeNode Mine=new DefaultMutableTreeNode("MyCalendar");
//      DefaultMutableTreeNode Group=new DefaultMutableTreeNode("GroupCalendar");
//      
//      CalendarRoot.add(Mine);
//      CalendarRoot.add(Group);
//      
//      Mine.add(addScheduleData("2017/03/21","과제"));
//      JTree tree=new JTree(CalendarRoot);
//      
//      SchedulePanel.add(tree);
//      tree.setOpaque(false);
      
      

   }
//   
//   private DefaultMutableTreeNode addScheduleData(String date,String ScheduleName){
//	   EventData event=new EventData();
//	   //event.setData(6,date);
//	   event.getData(2);
//	   return new DefaultMutableTreeNode(event);
//
//   }
//      

   @Override
   public void actionPerformed(ActionEvent e) {
    	this.setVisible(false);
      
      if(e.getSource()==mypageBtn){
         FMypage mypage=new FMypage(); 
            mypage.setVisible(true);
      }
      
      if(e.getSource()==friendgroupBtn){  
    	  fgUI.setVisible(true);
      }
      
      if(e.getSource()==SettingBtn){
         FSetting setting=new FSetting();         
         setting.setVisible(true);
      }
      
      if(e.getSource()==LogoutBtn){
         
      }
      
      if(e.getSource()==CalendarIcon){
         
         
         calendar.setGroup(fgUI);	//DMonth_CalendarView에 EFriend_GroupUI 보내기(그룹 정보 공유)
        fgUI.setDMonth_CalendarView(calendar);	//EFriend_GroupUI에 DMonth_CalendarView 보내기(스캐줄 공유)
         calendar.setVisible(true);
      }
   }
   
   public static CTreeView getInstanace() {
		// TODO Auto-generated method stub
		return instance;
	}
   
   public EFriend_GroupUI geteFGUi(){
	   return fgUI;
   }
}