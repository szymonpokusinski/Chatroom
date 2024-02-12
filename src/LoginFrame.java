import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

class RoundTextField1 extends JTextField {
    public RoundTextField1(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int arc = 20;

        Color grayColor = new Color(160, 160, 160);
        g2d.setColor(grayColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));

        super.paintComponent(g);
        g2d.dispose();
    }
}

public class LoginFrame extends JFrame {
    JLabel jLabel;
    RoundTextField1 jTextField;
    JButton jButton;
    String username;

    public LoginFrame() {
        setSize(300, 200);
        setTitle("Podaj Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color backgroundColor = new Color(230, 230, 230);
        getContentPane().setBackground(backgroundColor);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        jLabel = new JLabel("Podaj Login");
        jLabel.setFont(new Font("Arial", Font.BOLD, 18));

        jTextField = new RoundTextField1(15);

        jButton = new JButton("Zaloguj");

        jButton.setBackground(new Color(0, 153, 255));
        jButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(jLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(jTextField, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        add(jButton, gbc);


        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }

    public String getUsername() {
        return username;
    }
}
