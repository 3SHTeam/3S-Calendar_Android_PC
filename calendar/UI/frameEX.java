import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Data.TagData;

public class frameEX extends JFrame {

	private JPanel contentPane;
	private JTextField tagName;
	private JComboBox tagColor;
	private JButton okBtn;
	private JButton cancelBtn;
	private TagData tagData;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frameEX frame = new frameEX();
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
	public frameEX() {
		setTitle("Add Tag");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JLabel tagNameLabel=new JLabel("�±׸� : ");
		tagNameLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		tagNameLabel.setLocation(114, 94);
		tagNameLabel.setSize(78, 54);
		contentPane.add(tagNameLabel);
		
		JLabel colorLabel = new JLabel("���� : ");
		colorLabel.setFont(new Font("���� ���", Font.PLAIN, 20));
		colorLabel.setBounds(114, 186, 74, 54);
		contentPane.add(colorLabel);
		
		tagName = new JTextField();
		tagName.setBounds(223, 110, 267, 27);
		contentPane.add(tagName);
		tagName.setColumns(10);
		
		tagColor = new JComboBox();
		tagColor.setModel(new DefaultComboBoxModel(new String[] {"BLACK","RED", "ORANGE", "YELLOW", "GREEN", "BLUE","PINK","CYAN","MAGENTA","GRAY"}));
		tagColor.setBounds(223, 202, 267, 27);
		contentPane.add(tagColor);
		
		okBtn = new JButton("�߰�");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tagData.setData(1,tagName.getText());
				tagData.setData(2,(String)tagColor.getSelectedItem());
			}
		});
		okBtn.setBounds(173, 302, 122, 38);
		contentPane.add(okBtn);
		
		cancelBtn = new JButton("���");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelBtn.setBounds(368, 302, 122, 38);
		contentPane.add(cancelBtn);

	}
}
