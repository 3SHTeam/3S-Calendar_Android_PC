package Calendar.AcceptReject;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BtnPane extends JPanel {

    private JButton accept;
    private JButton reject;
    private String state;

    public BtnPane() {
        setLayout(new GridBagLayout());
        accept = new JButton("Accept");
        accept.setActionCommand("accept");
        reject = new JButton("Reject");
        reject.setActionCommand("reject");

        add(accept);
        add(reject);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = e.getActionCommand();
                System.out.println("State = " + state);
            }
        };

        accept.addActionListener(listener);
        reject.addActionListener(listener);
    }

    public void addActionListener(ActionListener listener) {
        accept.addActionListener(listener);
        reject.addActionListener(listener);
    }

    public String getState() {
        return state;
    }
}

