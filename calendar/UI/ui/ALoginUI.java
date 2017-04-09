package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import javax.swing.JPasswordField;

public class ALoginUI extends JFrame{


   private JPanel contentPane;
   private JTextField Googleid;
   private JButton OKBtn;
   private JPasswordField Googlepw;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               ALoginUI frame = new ALoginUI();
               frame.setLocationRelativeTo(null);
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
   public ALoginUI() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Login");
      setResizable(false);
      setBounds(100, 100, 700, 600);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      getContentPane().setLayout(null);
      contentPane.setLayout(null);
      
      JLabel lblGoogle = new JLabel("Google");
      lblGoogle.setFont(new Font("Agency FB", Font.BOLD, 51));
      lblGoogle.setBounds(290, 138, 122, 67);
      contentPane.add(lblGoogle);      
      
      Googleid = new JTextField();
      Googleid.setBounds(285, 279, 145, 21);
      contentPane.add( Googleid);
      Googleid.setColumns(10);
      
      JLabel IDLabel = new JLabel("ID");
      IDLabel.setFont(new Font("굴림", Font.BOLD, 13));
      IDLabel.setBounds(198, 280, 41, 18);
      contentPane.add(IDLabel);
      
      JLabel PasswordLabel = new JLabel("Password");
      PasswordLabel.setFont(new Font("굴림", Font.BOLD, 13));
      PasswordLabel.setBounds(177, 321, 85, 15);
      contentPane.add(PasswordLabel);

      
      Checkbox checkbox = new Checkbox("자동 로그인");
      checkbox.setBounds(300, 362, 101, 23);
      contentPane.add(checkbox);
      
      JLabel label = new JLabel("구글 아이디가 없으신가요?");
      label.setForeground(Color.BLUE);
      label.setBounds(285, 396, 159, 15);
      contentPane.add(label);
      
      OKBtn = new JButton("OK");
      OKBtn.setBounds(453, 318, 63, 21);
      OKBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if(e.getSource()==OKBtn){
               BFirstLoginUI login=new BFirstLoginUI(); 
                  dispose();
                  login.setVisible(true);
            }
         }
      });
      contentPane.add(OKBtn);
      
      Googlepw = new JPasswordField();
      Googlepw.setBounds(285, 318, 145, 21);
      contentPane.add(Googlepw);
   }
}