package MonthCalendar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.*;
import Calendar.UI.ALoginUI;
import Calendar.UI.FMypage;
import Calendar.UI.Images;
import Calendar.UI.TagColorSet;
import GroupCalendar.GroupMain;


public class CalendarMain extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JPanel YearPanel;// ��
	private JLabel YearLabel;

	private JPanel MonthPanel;// ��
	private JLabel MonthLabel;
	private JComboBox<String> MonthCombo;
	
	final String[] DAY_OF_MONTH={ "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��" };
	final String[] DAYS_OF_WEEK = { "��", "��", "ȭ", "��", "��", "��", "��" };
	
	CalendarDayPane days[];// 42���� day����
	private int year, month, today, lastday, startYoil;// ��,��,��,���� �������� ,���ۿ���

	private JButton groupBtn;
	private JButton myPageBtn;
	private JButton todayBtn;
	private JButton addTagBtn;
	private JButton delTagBtn;
	private JButton refreshBtn;
	private JCheckBox tagbox;

	private static GroupMain group;

	public static final CalendarMain instance = new CalendarMain(group);

	private Calendar now = Calendar.getInstance();

	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;

	private UserData user;// ����� ����
	private ArrayList<EventData> allEvents = new ArrayList<EventData>();// ��� �����ٵ�����
	private ArrayList<TagData> tags = new ArrayList<TagData>();// ���� �����ٸ����� �±�	
	private final DefaultListModel<TagList_CheckItem> model= new DefaultListModel<TagList_CheckItem>();
	private JList<TagList_CheckItem>tagList;
	
	public CalendarMain(GroupMain group) {
		
		setTitle("CalendarView");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 920);
		
		Image img=Images.mainIcon.getImage();
		contentPane = new JPanel(){
			   public void paintComponent(Graphics g){
				    g.drawImage(img, 0, 0, 1500, 920, null);
				   }
				  };

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		freshMySchedule();
		freshTag();
		setTagList();
		
		// ���� ��,��
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);

		// ��
		YearPanel = new JPanel();
		YearPanel.setBounds(190, 60, 132, 30);
		contentPane.add(YearPanel);
		YearPanel.setLayout(new BorderLayout(0, 0));

		YearLabel = new JLabel(year + "");
		YearLabel.setBorder(new LineBorder(Color.GRAY));
		YearLabel.setBackground(Color.WHITE);
		YearLabel.setHorizontalAlignment(SwingConstants.CENTER);
		YearPanel.add(YearLabel, BorderLayout.CENTER);

		JLabel DownLabel = new JLabel("  <  ");
		DownLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));

		DownLabel.setOpaque(true);
		DownLabel.setBackground(SystemColor.inactiveCaption);
		DownLabel.setForeground(new Color(0, 0, 0));
		DownLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e) {
				DownLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			}

			public void mousePressed(MouseEvent e) {
				DownLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}

			public void mouseClicked(MouseEvent e) {
				YearLabel.setText("");
				year--;
				YearLabel.setText(year + "");
				changeDate();
			}
		});
		YearPanel.add(DownLabel, BorderLayout.WEST);

		JLabel UpLabel = new JLabel("  >  ");
		UpLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		UpLabel.setBackground(SystemColor.inactiveCaption);
		UpLabel.setOpaque(true);
		UpLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e) {
				UpLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			}

			public void mousePressed(MouseEvent e) {
				UpLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}

			public void mouseClicked(MouseEvent e) {
				YearLabel.setText("");
				year++;
				YearLabel.setText(year + "");
				changeDate();
			}
		});
		YearPanel.add(UpLabel, BorderLayout.EAST);

		// ��
		MonthCombo = new JComboBox<String>(DAY_OF_MONTH);
		MonthCombo.setSelectedIndex(month);
		MonthCombo.setBounds(350, 60, 69, 30);
		MonthCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				month = cb.getSelectedIndex();
				MonthLabel.setText(DAY_OF_MONTH[month]);

				changeDate();
			}
		});
		contentPane.add(MonthCombo);

		MonthPanel = new JPanel();
		MonthPanel.setOpaque(false);
		MonthPanel.setLayout(new BorderLayout());
		MonthPanel.setBounds(745, 20, 200, 70);
		MonthPanel.setLayout(null);
	
		MonthLabel = new JLabel(DAY_OF_MONTH[month]);
		MonthLabel.setBounds(27, 10, 100, 51);
		MonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MonthLabel.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 50));
		MonthPanel.add(MonthLabel);
		contentPane.add(MonthPanel);

		// ��
		JPanel WeekPanel = new JPanel();
		JLabel[] WeekLabels;
		WeekPanel.setBackground(Color.LIGHT_GRAY);
		WeekPanel.setBounds(190, 100, 1200, 30);
		WeekPanel.setLayout(new GridLayout(1, 7));
		contentPane.add(WeekPanel);

		WeekLabels = new JLabel[7];

		for (int i = 0; i < WeekLabels.length; i++) {
			WeekLabels[i] = new JLabel(DAYS_OF_WEEK[i]);
			WeekLabels[i].setPreferredSize(new Dimension(140, 25));
			WeekLabels[i].setHorizontalAlignment(JLabel.CENTER);
			if (i == 0)
				WeekLabels[i].setForeground(Color.RED);
			if (i == 6)
				WeekLabels[i].setForeground(Color.BLUE);
			WeekPanel.add(WeekLabels[i]);
		}
		// ��
		JPanel DaysPanel = new JPanel();// day�� �ǳ�
		DaysPanel.setBackground(Color.LIGHT_GRAY);
		DaysPanel.setBounds(190, 130, 1200, 660);
		DaysPanel.setLayout(new GridLayout(6, 7));
		days = new CalendarDayPane[42];
		for (int i = 0; i < days.length; i++) {
			days[i] = new CalendarDayPane(this,group);
			DaysPanel.add(days[i]);
		}
		changeDate();
		contentPane.add(DaysPanel);
		

		refreshBtn=new JButton(Images.RefreshIcon);
		refreshBtn.addActionListener(this);
		refreshBtn.setBounds(1330, 36, 60, 60);
		contentPane.add(refreshBtn);
		
		
		groupBtn = new JButton(Images.GroupIcon);
		groupBtn.addActionListener(this);
		groupBtn.setBounds(420, 807, 120, 60);
		contentPane.add(groupBtn);

		myPageBtn = new JButton(Images.ProFileIcon);
		myPageBtn.setBounds(745, 807, 120, 60);
		myPageBtn.addActionListener(this);
		contentPane.add(myPageBtn);


		todayBtn = new JButton(Images.TodayIcon);
		todayBtn.setBounds(1065, 807, 120, 60);
		todayBtn.addActionListener(this);
		contentPane.add(todayBtn);
		
		addTagBtn = new JButton(Images.AddTagIcon);
		addTagBtn.setBounds(10, 570, 70, 40);
		addTagBtn.addActionListener(this);
		contentPane.add(addTagBtn);
		
		delTagBtn = new JButton(Images.DeleteTagIcon);
		delTagBtn.setBounds(100, 570, 70, 40);
		delTagBtn.addActionListener(this);
		contentPane.add(delTagBtn);
	}

	private void setTagList() {
		tagList=new JList<TagList_CheckItem>(model);
		tagList.setCellRenderer(new TagList_CheckRenderer());
		
		for (int i = 0; i < tags.size(); i++) {
			String tagName = tags.get(i).getData(1);
			String colorStr = tags.get(i).getData(2);

			model.addElement(new TagList_CheckItem(tagName,colorStr));
	}
		JScrollPane pane=new JScrollPane(tagList);
		pane.setBounds(5, 251, 182, 300);
		contentPane.add(pane);
		
		tagList.addMouseListener(new MouseAdapter()
          {
               public void mouseClicked(MouseEvent e)
               {
            	   JList list = (JList) e.getSource();
                   int index = list.locationToIndex(e.getPoint());  
                   TagList_CheckItem item = (TagList_CheckItem)list.getModel().getElementAt(index);
                   item.setSelected(! item.isSelected());
                   list.repaint(list.getCellBounds(index, index));
           
                   changeDate();
               }
          });
}


	private void freshMySchedule() {
		/* DB���� ������ �������� */
		url = "http://113.198.84.67/Calendar3S/SelectMySchedule.php";
		setUser(ALoginUI.getUser());
		Googleid = user.getData(0);

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
		jm.onPostExecute("SelectMySchedule", result);
		allEvents = jm.getEvents();

		if(allEvents != null){
			for (int i = 0; i < allEvents.size(); i++) {
				allEvents.get(i).showData();
			}
		}
	}

	public void freshTag() {
		/* DB���� ������ �±װ������� */
		url = "http://113.198.84.67/Calendar3S/SelectMyTag.php";

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
		jm.onPostExecute("SelectMyTag", result);
		tags = jm.getTags();
	}
	
	// �߰� ���� ���� �� refresh
	public void refreshSchedule() {
		freshMySchedule();
		changeDate();// ����
	}
	
	public void refreshTag(){	
		model.clear();
		setTagList();
	}


	/* �� �� ��¥ �̵��Լ� */
	public void changeDate() {
		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, year); // �̵��� ������ c ����
		c.set(Calendar.MONTH, month);// �̵��� ���� c ����

		// ����date�� 1�Ϸ� ����->���ۿ��� ���ϱ�
		c.set(Calendar.DAY_OF_MONTH, 1);
		startYoil = c.get(Calendar.DAY_OF_WEEK);

		// �ش� ���� �������ϱ��ϱ�
		lastday = c.getActualMaximum(Calendar.DATE);
		setRange(startYoil, lastday, 1);
	}

	/* 42���� �ǳڿ� day���� �Լ� */
	public void setRange(int startYoil, int lastday, int today) {
		int cnt = 1;
		int value;// �ǳ� day������
		String[] tagcheck = checkTagids();// tagId�鸸

		for (int i = 0; i < days.length; i++) {
			if (i < (startYoil - 1) || (i > (lastday + startYoil - 2)))// 42���� day�� ǥ�õ� �ǳ� �ܿ���
																		// 0���� ������ �����ش�.
				value = 0;
			else
				value = cnt++;

			days[i].initInfo();
			days[i].setValue(year, month, value, i);// �⺻������ ��/��/�� ������ �����ְ�, ��
													// �ǳڿ� day ��ġ ����

			Vector<EventData> dayEvents = new Vector<EventData>();

			if(allEvents != null){
				for (int j = 0; j < allEvents.size(); j++) {// ������ �������� ��ġ��
					EventData sd = allEvents.get(j);
					for (int k = 0; k < tagcheck.length; k++) {// �̺�Ʈ������ Tagid��
																// check�� tagid��
						if (sd.getData(6).equals(tagcheck[k]) && (year == Integer.valueOf(sd.getYear()))&& (month == Integer.valueOf(sd.getMonth()) - 1)
								&& (value == Integer.valueOf(sd.getDay()))) {
							dayEvents.add(sd);
						}
					}
				}
			}

			days[i].setVec(dayEvents);

			if (value != 0) {
				days[i].setSchedule();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == refreshBtn){
			refreshSchedule();
			freshTag();
			refreshTag();
		}
		
		if (e.getSource() == groupBtn) {
			this.setVisible(false);
			GroupMain fgMain=new GroupMain(this);
			fgMain.setVisible(true);
		}
		if (e.getSource() == myPageBtn) {
			FMypage mypage = new FMypage(this.getUser());
			mypage.setVisible(true);
		}
		if (e.getSource() == todayBtn) {
			year = now.get(Calendar.YEAR);
			month = now.get(Calendar.MONTH);
			today= now.get(Calendar.DATE);
			
			YearLabel.setText(year + "");
			MonthCombo.setSelectedIndex(month);
			MonthLabel.setText(DAY_OF_MONTH[month]);
			
			changeDate();
		}
		if (e.getSource() == addTagBtn) {
			AddTag addt = new AddTag();
			addt.setVisible(true);
		}
		if (e.getSource() == delTagBtn) {
			
		}
	}

	public static CalendarMain getInstanace() {
		return instance;
	}

	public void setGroup(GroupMain group) {
		this.group = group;
	}

	public GroupMain getGroup() {
		return group;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public void setTagbox(JCheckBox tagbox) {
		this.tagbox = tagbox;
	}

	public ArrayList<TagData> getTags() {
		// TODO Auto-generated method stub
		return tags;
	}

	// public String getScheduleGroupName(int index){
	// return scheduleVec.elementAt(index).getGroup();
	// }
	//
	// public String getScheduleName(int index){
	// return scheduleVec.elementAt(index).getScheduleName();
	// }
	//
	// public String getScheduleDate(int index){
	// return scheduleVec.elementAt(index).getDate() + " " +
	// scheduleVec.elementAt(index).getStartTime();
	// }

	class AddTag extends JDialog {
		private JPanel contentPane;
		private JTextField tagName;
		private JButton colorChooseBtn;
		private JButton okBtn;
		private JButton cancelBtn;

		public AddTag() {

			setTitle("Add Tag");
			setBounds(100, 100, 657, 479);
			Image img=Images.smainIcon.getImage();
			contentPane = new JPanel(){
				   public void paintComponent(Graphics g){
					    //super.paintComponents(g);
					    g.drawImage(img, 0, 0, 657, 479, null);
					   }
			};
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setResizable(false);
			setLocationRelativeTo(null);

			JLabel tagNameLabel = new JLabel("�±׸� : ");
			tagNameLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
			tagNameLabel.setLocation(114, 94);
			tagNameLabel.setSize(78, 54);
			contentPane.add(tagNameLabel);

			tagName = new JTextField();
			tagName.setBounds(223, 110, 267, 27);
			contentPane.add(tagName);
			tagName.setColumns(10);

			JLabel colorLabel = new JLabel("���� : ");
			colorLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
			colorLabel.setBounds(114, 186, 74, 54);
			contentPane.add(colorLabel);

			colorChooseBtn = new JButton();
			colorChooseBtn.setBounds(222, 203, 267, 27);
			colorChooseBtn.setBackground(Color.BLACK);
			colorChooseBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JColorChooser chooseColor = new JColorChooser();
					Color getColor = chooseColor.showDialog(null, "color", Color.BLACK);
					if (getColor != null) {
						colorChooseBtn.setBackground(getColor);
					}
				}
			});
			contentPane.add(colorChooseBtn);

			okBtn = new JButton(Images.OKIcon);
			okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = tagName.getText();
					String color = TagColorSet.colorToHex(colorChooseBtn.getBackground());
					
					insertTag(name, color);
					freshTag();
					refreshTag();
					dispose();
				}

			});
			okBtn.setBounds(173, 302, 110, 50);
			contentPane.add(okBtn);
			
			cancelBtn = new JButton(Images.CancelIcon);
			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelBtn.setBounds(368, 302, 110, 50);
			contentPane.add(cancelBtn);
		}

		public void insertTag(String name, String color) {
			/* DB�� �±� �����ϱ� */
			url = "http://113.198.84.67/Calendar3S/InsertTag.php";
			Googleid = user.getData(0);

			String message = "'" + Googleid + "','" + name + "','" + color + "','�������','15',''";

			stDB = new SendToDB(url, message);
			stDB.start();// DB���� ������ ����
			try {
				stDB.join();// DB������ �Ϸ�ɶ����� ���
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}
		
		private String[] checkTagids() {	
			String[] tagIds = new String[tagList.getModel().getSize()];

			for(int i=0;i<tagList.getModel().getSize();i++){
				String tagName=tags.get(i).getData(1);
				JList list= (JList) tagList;
				TagList_CheckItem tagbox=tagList.getModel().getElementAt(i);
				String tagboxName=tagbox.toString();

				if(tagbox.isSelected() && tagboxName.equals(tagName))
					tagIds[i] = tags.get(i).getData(0);		
			}	
			return tagIds;			
		}
		
		public ArrayList<String> getTagNames(String Gid) {
			//freshTag();
			ArrayList<String> tagNames = new ArrayList<String>();
				
			for(int i=0;i<tagList.getModel().getSize();i++){
				if(Gid.equals("NULL")){//�Ϲ� ������ �±�
					if(tags.get(i).getData(5).equals("0"))
						tagNames.add(tags.get(i).getData(1));
				}
				else{
					if(tags.get(i).getData(5).equals(Gid)){
						tagNames.add(tags.get(i).getData(1));
						break;
					}
				}
				
			}
			
			return tagNames;
		}


}

