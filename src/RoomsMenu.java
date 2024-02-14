import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RoomsMenu extends JPanel {
    RoundButton makeRoom;
    JPanel dostepnePokoje;
    JPanel main;
    JScrollPane scrollPane;
    Klient klient;
    RoomsMenu(Klient klient) throws IOException {
        this.klient = klient;

        setBackground(Color.BLACK);
        setBounds(0, 0, 200, 500);
        setLayout(null);

        dostepnePokoje = new JPanel();
        JLabel titleText;
        titleText = new JLabel("Dostępne Pokoje");
        titleText.setVerticalAlignment(SwingUtilities.CENTER);
        titleText.setHorizontalAlignment(SwingUtilities.CENTER);
        titleText.setFont(new Font("Arial", Font.BOLD, 16));
        titleText.setForeground(Color.WHITE);
        dostepnePokoje.add(titleText);
        dostepnePokoje.setBackground(Color.DARK_GRAY);
        dostepnePokoje.setBounds(10, 10, 180, 35);
        add(dostepnePokoje);

        main = new JPanel();
        main.setBackground(Color.GRAY);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        Border mainBorder = BorderFactory.createLineBorder(Color.BLACK);
        main.setBorder(mainBorder);

        scrollPane = new JScrollPane(main);
        customizeScrollBar(scrollPane);
        scrollPane.setBounds(10, 50, 180, 360);
        add(scrollPane);

        makeRoom = new RoundButton("Stwórz Pokój");
        makeRoom.setBackground(Color.DARK_GRAY);
        makeRoom.setForeground(Color.WHITE);
        makeRoom.setBounds(10, 420, 180, 30);
        add(makeRoom);
    }
    public void addRoomPanel(String name) throws IOException {
        RoomPanel panel = (RoomPanel) createStyledRoomPanel(name);
        main.add(panel);
        revalidate();
        repaint();
    }
    private JPanel createStyledRoomPanel(String name) {
        RoomPanel panel = new RoomPanel(name);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(panel.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String chat = panel.name.getText();
                System.out.println(chat);
                String message = "!!CHANGEROOM!!" + chat;
                try {
                    PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                    out.println(message);
                    out.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return panel;
    }

    public void showDialogForRoom() {
        MakeRoomFrame frame = new MakeRoomFrame();
        frame.jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!frame.jTextField.getText().isEmpty()) {
                    frame.dispose();
                }
            }
        });
    }

    public void changeRoom(JLabel jLabel) {
        String chat = jLabel.getText();
        String message = "!!CHANGEROOM!!" + chat;
        System.out.println(message);
    }

    public void czyszczenie() {
        main.removeAll();
        main.revalidate();
        main.repaint();
    }

    class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(getBackground().darker());
            } else {
                g.setColor(getBackground());
            }
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(Color.BLACK); // Czarne obramowanie
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
    }
    private void customizeScrollBar(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.BLACK;
                this.trackColor = Color.BLACK;
                this.thumbDarkShadowColor = Color.BLACK;
                this.thumbLightShadowColor = Color.BLACK;
                this.trackHighlightColor = Color.BLACK;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            public Dimension getPreferredSize(JComponent c) {
                return new Dimension(8, super.getPreferredSize(c).height);
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                Dimension zeroDim = new Dimension(0, 0);
                button.setPreferredSize(zeroDim);
                button.setMinimumSize(zeroDim);
                button.setMaximumSize(zeroDim);
                return button;
            }
        });
    }
}
