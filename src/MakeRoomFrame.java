import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

class RoundTextField extends JTextField {
    public RoundTextField(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int arc = 20;

        // Ustawienie niestandardowego odcienia szarości
        Color grayColor = new Color(160, 160, 160);
        g2d.setColor(grayColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));

        super.paintComponent(g);
        g2d.dispose();
    }
}

public class MakeRoomFrame extends JFrame {
    JLabel jLabel;
    RoundTextField1 jTextField;
    JButton jButton;
    String roomName;

    MakeRoomFrame() {
        setSize(300, 200);
        setTitle("Stwórz Pokój");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ustawienie niestandardowego odcienia szarości dla tła
        Color backgroundColor = new Color(230, 230, 230);
        getContentPane().setBackground(backgroundColor);

        // Ustawienie niestandardowego layoutu GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        jLabel = new JLabel("Stwórz Pokój");
        jLabel.setFont(new Font("Arial", Font.BOLD, 18));

        jTextField = new RoundTextField1(15);

        jButton = new JButton("Stwórz");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomName = jTextField.getText();
                // Tutaj możesz dodać dodatkową logikę związaną ze stworzeniem pokoju
            }
        });

        jButton.setBackground(new Color(0, 153, 255));
        jButton.setForeground(Color.WHITE);

        // Ustawienie napisu "Stwórz pokój" na środku
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(jLabel, gbc);

        // Ustawienie pola do wpisywania tekstu i przycisku
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.anchor = GridBagConstraints.LINE_START;
        add(jTextField, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        add(jButton, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MakeRoomFrame::new);
    }

    public String getRoomName() {
        return roomName;
    }
}
