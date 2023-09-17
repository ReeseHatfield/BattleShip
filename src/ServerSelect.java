import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerSelect extends JFrame {

    private JButton sendButton = new JButton("Start");
    private JTextField serverField = new JTextField(20);
    private JLabel label = new JLabel("Enter Server IP:");

    public ServerSelect() {
        setLayout(new FlowLayout());
        this.sendButton.addActionListener(e -> {

            if (!this.serverField.getText().isEmpty()) {
                this.dispose();
                new Client(this.serverField.getText().trim());
            }

        });

        this.add(this.label);
        this.add(this.serverField);
        this.add(this.sendButton);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 100);
        this.setVisible(true);
        this.setResizable(false);
    }
}
