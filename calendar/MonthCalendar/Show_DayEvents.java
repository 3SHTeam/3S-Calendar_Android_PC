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
	/*dayPane�� ����󼼳��� â*/
		private CalendarMain calendar;
		private JPanel contentPane;
		private  JTable table;
		DefaultTableModel model;
		private ArrayList<TagData>tagArr=new ArrayList<TagData>();
		
		// 1�� �ǳ� Ŭ���ϸ� 2���ǳ�(�������ִ� ��������)�� ��ȯ
		public Show_DayEvents(String date, Vector<EventData> dayScheduleVec){
			this.calendar=CalendarMain.getInstanace();	
			tagArr=calendar.getTags();
			
			setTitle("�Ϻ�����");
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
			DateLabel.setFont(new Font("���� ���",Font.BOLD,25));
			DateLabel.setBounds(30, 15, 200, 40);
			contentPane.add(DateLabel);
			
			
			  String colNames[] = { "�ð�", "�����ٸ�"};  // Jtable ����� 1���� �迭
			  model=new DefaultTableModel(colNames,0){ // �� ���� ���ϰ� �ϴ� �κ�
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
			  model.setNumRows(0);
			 
			  for(int i=0;i<dayScheduleVec.size();i++){ 
				   Vector<String> VecInfo=new Vector<String>();//Vec���� �ð��� �����ٸ� ������ row ���Ϳ� ���� ��Ű�� model.addRow
				   String startTime=dayScheduleVec.get(i).getData(4).substring(8, 10)+":"+dayScheduleVec.get(i).getData(4).substring(10, 12);
				   String endTime=dayScheduleVec.get(i).getData(5).substring(8, 10)+":"+dayScheduleVec.get(i).getData(5).substring(10, 12);
				   VecInfo.add(startTime+"-"+endTime);//���۽ð� ������
				   VecInfo.add(dayScheduleVec.get(i).getData(2));//���O
				   model.addRow(VecInfo);
				
			  }

			   table = new JTable(model);   // ���̺� ����    
			   table.setOpaque(true);
			   table.setRowHeight(40);
			   table.setFont(new Font("���� ���",Font.BOLD,15));
		
				DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
				tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);	
				TableColumnModel tcmSchedule = table.getColumnModel();			 
				// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
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
		        JScrollPane scrollPane = new JScrollPane(table);  // ��ũ�� ��� ������ �־���߸� �۵���
		        table.setShowVerticalLines(false);
		        scrollPane.setBounds(25, 65, 450, 550);
		        contentPane.add(scrollPane, BorderLayout.CENTER); // contentPane�� ���̺� ����
		 
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
		
