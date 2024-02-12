import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RoomPanel extends JPanel {
    RoundButton makeRoom;
    JPanel dostepnePokoje;
    JPanel main;
    JScrollPane scrollPane;
    int color = 0;
    static ArrayList<String> roomPanels = new ArrayList<>();
    private Timer timer;
    Klient klient;
    RoomManager roomManager = new RoomManager();

    Server server;

    RoomPanel(Klient klient) throws IOException {
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
        customizeScrollPane(scrollPane);
        scrollPane.setBounds(10, 50, 180, 360);
        add(scrollPane);

        makeRoom = new RoundButton("Stwórz Pokój");
        makeRoom.setBackground(Color.DARK_GRAY);
        makeRoom.setForeground(Color.WHITE);
        makeRoom.setBounds(10, 420, 180, 30);
        add(makeRoom);
    }

    private void customizeScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(Color.GRAY);
        scrollPane.getViewport().setLayout(new BoxLayout(scrollPane.getViewport(), BoxLayout.Y_AXIS));
    }

    public void addRoomPanel(String name) throws IOException {
        JPanel panel = createStyledRoomPanel(name);
        main.add(panel);
        revalidate();
        repaint();
    }

    private JPanel createStyledRoomPanel(String name) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        JLabel jLabel = new JLabel(name);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(jLabel);


        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(panel.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String chat = jLabel.getText();
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
                    // addRoomPanel(frame.jTextField.getText());
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

    // Klasa RoundButton
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
}
