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
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JList;

public class frameEX extends JFrame {
	private TagData tagData;
	private JLabel groupName;

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
		setTitle("MemberList");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 542, 754);
		setResizable(false);
		getContentPane().setLayout(null);
		
		groupName = new JLabel();
		groupName.setFont(new Font("MD개성체", Font.PLAIN, 25));
		groupName.setText("그룹명");
		groupName.setBounds(17, 31, 202, 56);
		getContentPane().add(groupName);
		
		JList Memberlist = new JList();
		Memberlist.setBounds(39, 102, 437, 511);
		getContentPane().add(Memberlist);
		
		JButton okButton = new JButton("확인");
		okButton.setBounds(202, 644, 125, 29);
		getContentPane().add(okButton);

	}
}
