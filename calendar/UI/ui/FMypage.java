package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FMypage extends JFrame {

   private JPanel contentPane;
   private JTextField NicktextField;
   private JTextField phone;
   private JTextField comment;


   /**
    * Create the frame.
    */
   public FMypage() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 450, 300);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      setLocationRelativeTo(null);
      
      JPanel panel = new JPanel();
      panel.setBackground(Color.GRAY);
      panel.setBounds(44, 38, 133, 140);
      contentPane.add(panel);
      
      JButton btnNewButton = new JButton("New button");
      btnNewButton.setBounds(65, 198, 97, 23);
      contentPane.add(btnNewButton);
      
      JLabel nameLabel = new JLabel("이름:");
      nameLabel.setBounds(189, 38, 57, 15);
      contentPane.add(nameLabel);
      
      JLabel name = new JLabel("New label");
      name.setBounds(277, 38, 57, 15);
      contentPane.add(name);
      
      JLabel nicknamelabel = new JLabel("닉네임:");
      nicknamelabel.setBounds(189, 66, 57, 15);
      contentPane.add(nicknamelabel);
      
      NicktextField = new JTextField();
      NicktextField.setBounds(262, 63, 116, 21);
      contentPane.add(NicktextField);
      NicktextField.setColumns(10);
      
      JLabel emailLabel = new JLabel("이메일:");
      emailLabel.setBounds(189, 96, 57, 15);
      contentPane.add(emailLabel);
      
      JLabel email = new JLabel("New label");
      email.setBounds(277, 94, 57, 15);
      contentPane.add(email);
      
      JLabel phoneLabel = new JLabel("전화번호:");
      phoneLabel.setBounds(189, 131, 57, 15);
      contentPane.add(phoneLabel);
      
      phone = new JTextField();
      phone.setBounds(262, 125, 116, 21);
      contentPane.add(phone);
      phone.setColumns(10);
      
      JLabel commentLabel = new JLabel("한마디:");
      commentLabel.setBounds(189, 166, 57, 15);
      contentPane.add(commentLabel);
      
      comment = new JTextField();
      comment.setBounds(262, 163, 116, 21);
      contentPane.add(comment);
      comment.setColumns(10);
   }
}