package MonthCalendar;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.*;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Calendar.Data.EventData;
import Calendar.Data.TagData;
import Calendar.UI.Images;

public class Show_DayEvents  extends JDialog{
	/*dayPane의 내용상세내용 창*/
		private CalendarMain calendar;
		private JPanel contentPane;
		private  JTable table;
		DefaultTableModel model;
		private ArrayList<TagData>tagArr=new ArrayList<TagData>();
		
		// 1번 판넬 클릭하면 2번판넬(상세정보있는 페이지로)로 전환
		public Show_DayEvents(String date, Vector<EventData> dayScheduleVec){
			this.calendar=CalendarMain.getInstanace();	
			tagArr=calendar.getTags();
			
			setTitle("일별보기");
		    setResizable(false);
		    setLocationRelativeTo(null);
			setBounds(100, 100, 500, 768);
	
			Image img=Images.smainIcon.getImage();
			contentPane = new JPanel(){
				   public void paintComponent(Graphics g){
					    //super.paintComponents(g);
					    g.drawImage(img, 0, 0, 500, 768, null);
					   }
					  };
		    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    setContentPane(contentPane);
		    contentPane.setLayout(null);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel DateLabel = new JLabel(date);
			DateLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
			DateLabel.setBounds(30, 15, 200, 40);
			contentPane.add(DateLabel);
			
			
			  String colNames[] = { "시간", "스케줄명"};  // Jtable 헤더는 1차원 배열
			  model=new DefaultTableModel(colNames,0){ // 셀 수정 못하게 하는 부분
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
			  model.setNumRows(0);
			 
			  for(int i=0;i<dayScheduleVec.size();i++){ 
				   Vector<String> VecInfo=new Vector<String>();//Vec에서 시간과 스케줄명 가져와 row 벡터에 저장 시키고 model.addRow
				   String startTime=dayScheduleVec.get(i).getData(4).substring(8, 10)+":"+dayScheduleVec.get(i).getData(4).substring(10, 12);
				   String endTime=dayScheduleVec.get(i).getData(5).substring(8, 10)+":"+dayScheduleVec.get(i).getData(5).substring(10, 12);
				   VecInfo.add(startTime+"-"+endTime);//시작시간 데이터
				   VecInfo.add(dayScheduleVec.get(i).getData(2));//스켖
				   model.addRow(VecInfo);
				
			  }

			   table = new JTable(model);   // 테이블 생성    
			   table.setOpaque(true);
			   table.setRowHeight(40);
			   table.setFont(new Font("맑은 고딕",Font.BOLD,15));
		
				DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
				tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);	
				TableColumnModel tcmSchedule = table.getColumnModel();			 
				// 반복문을 이용하여 테이블을 가운데 정렬로 지정
				for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
				tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
				}
	           
				
			   table.addMouseListener(new MouseAdapter() {
				   		
				   public void mousePressed(MouseEvent e){
						int row=table.rowAtPoint(e.getPoint());
						EventData selectedEvent=dayScheduleVec.get(row);
						
						for(int i=0;i<tagArr.size();i++){
							if(selectedEvent.getData(6).equals(tagArr.get(i).getData(0))){		
								Collections.swap(tagArr, 0, i);
							}
						}

							dispose();					
							Show_EventDetail eventDetail;
							try {
								eventDetail = new Show_EventDetail(selectedEvent,tagArr);
								eventDetail.setVisible(true);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							

				   		}
				   }
			   );
		        JScrollPane scrollPane = new JScrollPane(table);  // 스크롤 기능 별도로 넣어줘야만 작동함
		        table.setShowVerticalLines(false);
		        scrollPane.setBounds(25, 65, 450, 550);
		        contentPane.add(scrollPane, BorderLayout.CENTER); // contentPane에 테이블 적용
		 
		        JButton disposeBtn=new JButton(Images.CancelIcon);
		        disposeBtn.setBounds(180, 645, 110, 50);
		        contentPane.add(disposeBtn);
		        disposeBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
		}
		
	}
		
