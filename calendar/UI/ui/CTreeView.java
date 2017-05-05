package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Data.EventData;


public class CTreeView extends JFrame implements ActionListener{

   private JPanel contentPane;
   private JButton mypageBtn;
   private JButton friendgroupBtn;
   private JButton SettingBtn;
   private JButton LogoutBtn;
   private JButton CalendarIcon;
 
   public static final CTreeView instance =new CTreeView();
   
   private EFriend_GroupMain fgUI;

   DMonth_CalendarMain calendar=(DMonth_CalendarMain) DMonth_CalendarMain.getInstanace(); 
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
      
      mypageBtn = new JButton("����������");
      mypageBtn.setBounds(546, 217, 114, 23);
      mypageBtn.addActionListener(this);
      contentPane.add(mypageBtn);
      
      friendgroupBtn = new JButton("Friend&Group");
      friendgroupBtn.setBounds(546, 328, 114, 23);
      friendgroupBtn.addActionListener(this);
      contentPane.add(friendgroupBtn);
      
      SettingBtn = new JButton("ȯ�漳��");
      SettingBtn.setBounds(546, 432, 114, 23);
      SettingBtn.addActionListener(this);
      contentPane.add(SettingBtn);
      
      LogoutBtn = new JButton("�α׾ƿ�");
      LogoutBtn.setBounds(546, 546, 114, 23);
      LogoutBtn.addActionListener(this);
      contentPane.add(LogoutBtn);
      
     ImageIcon calendarIcon=new ImageIcon("image/ic_event_available_black_48dp_1x.png");
      CalendarIcon = new JButton();
      CalendarIcon.setBounds(570, 59, 48, 48);
      CalendarIcon.addActionListener(this);
      contentPane.add(CalendarIcon);
      
      /*���� treeTable*/
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
//      Mine.add(addScheduleData("2017/03/21","����"));
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
         
         
         calendar.setGroup(fgUI);	//DMonth_CalendarView�� EFriend_GroupUI ������(�׷� ���� ����)
      //  fgUI.setDMonth_CalendarView(calendar);	//EFriend_GroupUI�� DMonth_CalendarView ������(��ĳ�� ����)
         calendar.setVisible(true);
      }
   }
   
   public static CTreeView getInstanace() {
		// TODO Auto-generated method stub
		return instance;
	}
   
   public EFriend_GroupMain geteFGUi(){
	   return fgUI;
   }
}