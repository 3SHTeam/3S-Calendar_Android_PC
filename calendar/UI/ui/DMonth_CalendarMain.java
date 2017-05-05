package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DB.JsonMaster;
import DB.SendToDB;
import Data.EventData;
import Data.TagData;
import Data.UserData;

public class DMonth_CalendarMain extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JPanel YearPanel;// 년
	private JLabel YearLabel;

	private JPanel MonthPanel;// 월
	private JLabel MonthLabel;
	private JComboBox MonthCombo;

	DMonth_dayPane days[];// 42개의 day설정
	private int year, month, today, lastday, startYoil;// 년,월,일,달의 마지막일 ,시작요일

	private JButton TreeViewIcon;
	private JButton myPageBtn;
	private JButton todayBtn;

	private JCheckBox tagbox;

	private static EFriend_GroupMain group;

	public static final DMonth_CalendarMain instance = new DMonth_CalendarMain(group);

	private Calendar now = Calendar.getInstance();

	private static SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;

	private UserData user;// 사용자 정보
	private ArrayList<EventData> allEvents = new ArrayList<EventData>();// 모든
																		// 스케줄데이터
	private ArrayList<TagData> tags = new ArrayList<TagData>();// 각각 스케줄마다의 태그
	private TagSet tagSet;

	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// DMonth_CalendarView frame = new DMonth_CalendarView(group);
	// frame.setLocationRelativeTo(null);
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public DMonth_CalendarMain(EFriend_GroupMain group) {

		setTitle("CalendarView");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		freshMySchedule();

		freshTag();

		// 현재 년,월
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);

		// 년
		YearPanel = new JPanel();
		YearPanel.setBounds(34, 44, 132, 21);
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
		DownLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				DownLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				DownLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
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
		UpLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				UpLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				UpLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				YearLabel.setText("");
				year++;
				YearLabel.setText(year + "");
				changeDate();
			}
		});
		YearPanel.add(UpLabel, BorderLayout.EAST);

		// 월
		String[] DAY_OF_MONTH = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };

		MonthCombo = new JComboBox(DAY_OF_MONTH);
		MonthCombo.setSelectedIndex(month);
		MonthCombo.setBounds(199, 44, 69, 21);
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
		MonthPanel.setLayout(new BorderLayout());
		MonthLabel = new JLabel(DAY_OF_MONTH[month]);
		MonthLabel.setBounds(27, 0, 73, 51);
		MonthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MonthLabel.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 35));
		MonthPanel.setBounds(595, 11, 132, 54);
		MonthPanel.setLayout(null);
		MonthPanel.add(MonthLabel);
		contentPane.add(MonthPanel);

		// 주
		JPanel WeekPanel = new JPanel();
		JLabel[] WeekLabels;
		WeekPanel.setBackground(Color.LIGHT_GRAY);
		WeekPanel.setBounds(137, 75, 1023, 30);
		WeekPanel.setLayout(new GridLayout(1, 7));
		contentPane.add(WeekPanel);

		WeekLabels = new JLabel[7];
		String[] DAYS_OF_WEEK = { "일", "월", "화", "수", "목", "금", "토" };

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

		// 일
		JPanel DaysPanel = new JPanel();// day별 판넬
		DaysPanel.setBackground(Color.LIGHT_GRAY);
		DaysPanel.setBounds(137, 106, 1023, 585);
		DaysPanel.setLayout(new GridLayout(6, 7));
		days = new DMonth_dayPane[42];
		for (int i = 0; i < days.length; i++) {
			days[i] = new DMonth_dayPane(this, group);
			DaysPanel.add(days[i]);
		}
		changeDate();
		contentPane.add(DaysPanel);

		ImageIcon treeIcon = new ImageIcon("image/ic_treeview_black_48dp_1x.png");
		TreeViewIcon = new JButton(treeIcon);
		TreeViewIcon.addActionListener(this);
		TreeViewIcon.setBounds(1163, 17, 48, 48);
		contentPane.add(TreeViewIcon);

		myPageBtn = new JButton("MyPage");
		myPageBtn.setBounds(980, 42, 97, 23);
		myPageBtn.addActionListener(this);
		contentPane.add(myPageBtn);

		JButton msgBtn = new JButton("message");
		msgBtn.setBounds(12, 618, 97, 23);
		contentPane.add(msgBtn);

		todayBtn = new JButton("T");
		todayBtn.setBounds(1100, 17, 50, 48);
		todayBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				year = now.get(Calendar.YEAR);
				month = now.get(Calendar.MONTH);
				YearLabel.setText(year + "");
				MonthCombo.setSelectedIndex(month);
				MonthLabel.setText(DAY_OF_MONTH[month]);

				changeDate();

			}
		});
		contentPane.add(todayBtn);

		// 태그판넬
		JPanel tagPanel = new JPanel();
		tagPanel.setBackground(Color.GRAY);
		tagPanel.setBounds(0, 251, 137, 217);
		contentPane.add(tagPanel);
		tagPanel.setLayout(new GridLayout(tags.size(), 1));

		for (int i = 0; i < tags.size(); i++) {
			tagbox = tags.get(i).getBox();
			String colorStr = tags.get(i).getData(2);

			tagbox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changeDate();
				}
			});

			JLabel ColorLabel = new JLabel();
			ColorLabel.setBackground(TagSet.hex2Rgb(colorStr));
			ColorLabel.setPreferredSize(new Dimension(5, 5));
			ColorLabel.setOpaque(true);

			tagbox.setLayout(new BorderLayout());
			tagbox.add(ColorLabel, BorderLayout.EAST);
			tagPanel.add(tagbox);
		}

		JButton addTagBtn = new JButton("+태그 추가");
		addTagBtn.setBounds(10, 480, 110, 30);
		addTagBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddTag addt = new AddTag();
				addt.setVisible(true);

			}
		});
		contentPane.add(addTagBtn);

	}

	private void freshMySchedule() {
		/* DB에서 스케줄 가져오기 */
		url = "http://113.198.84.66/Calendar3S/SelectMySchedule.php";
		setUser(ALoginUI.getUser());
		Googleid = user.getData(0);

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
		jm.onPostExecute("SelectMySchedule", result);
		allEvents = jm.getEvents();

		for (int i = 0; i < allEvents.size(); i++) {
			allEvents.get(i).showData();
		}
		
	}

	private void freshTag() {
		/* DB에서 스케줄 태그가져오기 */
		url = "http://113.198.84.66/Calendar3S/SelectMyTag.php";

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
		jm.onPostExecute("SelectMyTag", result);
		tags = jm.getTags();

		tagSet = new TagSet();
		tagSet.setTags(tags);
	}

	/* 년 월 날짜 이동함수 */
	public void changeDate() {
		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, year); // 이동한 년으로 c 설정
		c.set(Calendar.MONTH, month);// 이동한 월로 c 설정

		// 현재date를 1일로 변경->시작요일 구하기
		c.set(Calendar.DAY_OF_MONTH, 1);
		startYoil = c.get(Calendar.DAY_OF_WEEK);

		// 해당 달의 마지막일구하기
		lastday = c.getActualMaximum(Calendar.DATE);
		setRange(startYoil, lastday, 1);
	}

	/* 42개의 판넬에 day설정 함수 */
	public void setRange(int startYoil, int lastday, int today) {
		int cnt = 1;
		int value;// 판넬 day설정값
		String[] tagcheck = tagSet.checkTagids();// tagId들만

		for (int i = 0; i < days.length; i++) {
			if (i < (startYoil - 1) || (i > (lastday + startYoil - 2)))// 42개중 day가 표시될 판넬 외에는
																		// 0으로 정보를 보내준다.
				value = 0;
			else
				value = cnt++;

			days[i].initInfo();
			days[i].setValue(year, month, value, i);// 기본정보인 년/월/일 정보를 보내주고, 각
													// 판넬에 day 위치 설정

			Vector<EventData> dayEvents = new Vector<EventData>();

			for (int j = 0; j < allEvents.size(); j++) {// 오늘자 스케줄을 합치기
				EventData sd = allEvents.get(j);
				for (int k = 0; k < tagcheck.length; k++) {// 이벤트데이터 Tagid와
															// check된 tagid비교
					if (sd.getData(6).equals(tagcheck[k]) && (year == sd.getYear()) && (month == sd.getMonth() - 1)
							&& (value == sd.getDay())) {
						dayEvents.add(sd);
					}
				}
			}

			days[i].setVec(dayEvents);

			if (value != 0) {
				days[i].setSchedule();
			}
		}
	}

	// 추가 삭제 동결 다 refresh
	public void refreshSchedule() {
		freshMySchedule();
		changeDate();// 갱신
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		// TODO Auto-generated method stub
		if (e.getSource() == TreeViewIcon) {
//			CTreeView treeView = (CTreeView) CTreeView.getInstanace();
//			treeView.setVisible(true);
			EFriend_GroupMain fgMain=new EFriend_GroupMain(this);
			fgMain.setVisible(true);
		}
		if (e.getSource() == myPageBtn) {
			// DWeek_CalendarView
			// weekCalendar=(DWeek_CalendarView)DWeek_CalendarView.getInstanace();
			// weekCalendar.setVisible(true);
			FMypage mypage = new FMypage(this.getUser());
			mypage.setVisible(true);
		}
	}

	public static DMonth_CalendarMain getInstanace() {
		// TODO Auto-generated method stub
		return instance;
	}

	public void setGroup(EFriend_GroupMain group) {
		this.group = group;
	}

	public EFriend_GroupMain getGroup() {
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
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setResizable(false);
			setLocationRelativeTo(null);

			JLabel tagNameLabel = new JLabel("태그명 : ");
			tagNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			tagNameLabel.setLocation(114, 94);
			tagNameLabel.setSize(78, 54);
			contentPane.add(tagNameLabel);

			tagName = new JTextField();
			tagName.setBounds(223, 110, 267, 27);
			contentPane.add(tagName);
			tagName.setColumns(10);

			JLabel colorLabel = new JLabel("색상 : ");
			colorLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
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

			okBtn = new JButton("추가");
			okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = tagName.getText();
					String color = TagSet.colorToHex(colorChooseBtn.getBackground());
					
					 insertTag(name, color);
					 freshTag();
					dispose();
				}
			});
			okBtn.setBounds(173, 302, 122, 38);
			contentPane.add(okBtn);

			cancelBtn = new JButton("취소");
			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelBtn.setBounds(368, 302, 122, 38);
			contentPane.add(cancelBtn);
		}

		public void insertTag(String name, String color) {
			/* DB에 태그 삽입하기 */
			url = "http://113.198.84.66/Calendar3S/InsertTag.php";
			Googleid = user.getData(0);

			String message = "'" + Googleid + "','" + name + "','" + color + "','맑은고딕','15'";

			stDB = new SendToDB(url, message);
			stDB.start();// DB연결 스레드 시작
			try {
				stDB.join();// DB연결이 완료될때까지 대기
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
		}
	}
}
