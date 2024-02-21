import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatArea2 extends JPanel implements ActionListener {
    JTextField textField;
    JButton sendButton;
    JLabel jLabel;
    JScrollPane scrollPane;
    JPanel panelMessage;
    JPanel upJpanel;
    Klient klient;

    ChatArea2(String nazwa, Klient klient) throws IOException {
        this.klient = klient;

        setBackground(Color.BLACK);
        setBounds(200, 0, 400, 500);
        setLayout(null);

        upJpanel = new JPanel();
        upJpanel.setBackground(Color.BLACK);
        upJpanel.setLayout(null);
        upJpanel.setBounds(10, 10, 360, 40);

        JPanel chatname = new JPanel();
        chatname.setBackground(Color.BLACK);
        chatname.setBounds(0, 0, 180, 40);


        jLabel = new JLabel(nazwa);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(new Font("Arial", Font.BOLD, 24));
        jLabel.setHorizontalAlignment(SwingUtilities.CENTER);
        jLabel.setVerticalAlignment(SwingUtilities.CENTER);

        JLabel info = new JLabel("i");
        info.setForeground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(null);
        infoPanel.setBackground(Color.BLUE);
        infoPanel.setBounds(330, 10, 20, 20);
        infoPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String message = "!!CLIENTSLIST!!";
                System.out.println(message);
                try {
                    PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                    out.println(message);
                    out.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                infoPanel.setBackground(infoPanel.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                infoPanel.setBackground(Color.blue);
            }
        });
        infoPanel.add(info);

        chatname.add(jLabel);
        upJpanel.add(chatname);
        upJpanel.add(infoPanel);
        add(upJpanel);

        panelMessage = new JPanel();
        panelMessage.setBackground(Color.BLACK);
        panelMessage.setLayout(new BoxLayout(panelMessage, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelMessage);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(10, 50, 360, 360);

        customizeScrollBar(scrollPane);

        add(scrollPane);

        textField = new RoundTextField83(10);
        textField.setBounds(10, 420, 300, 30);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.white);

        Border border = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        textField.setBorder(border);
        add(textField);
        sendButton = new RightArrowButton();
        sendButton.setBounds(320, 420, 30, 30);
        sendButton.setBackground(new Color(0, 102, 204));
        sendButton.addActionListener(this);
        add(sendButton);
    }

    public void displayMessage(String message) {
        ChatBubble chatBubble = new ChatBubble("Me", message, true);
        panelMessage.add(chatBubble);
        panelMessage.add(Box.createVerticalStrut(5));
        panelMessage.revalidate();
        panelMessage.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == sendButton) {
            String message = textField.getText();
            if (!message.isEmpty()) {
                try {
                    displayMessage(message);
                    PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                    out.println(message);
                    out.flush();
                    textField.setText("");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void changeRoom(String name) {
        jLabel.setText(name);
        panelMessage.removeAll();
        panelMessage.revalidate();
        panelMessage.repaint();
        repaint();
        revalidate();
    }

    private void customizeScrollBar(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(0, 120, 215);
                this.trackColor = new Color(230, 230, 230);
                this.thumbDarkShadowColor = new Color(0, 120, 215);
                this.thumbLightShadowColor = new Color(0, 120, 215);
                this.trackHighlightColor = new Color(0, 120, 215);
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

class RoundTextField83 extends JTextField {
    public RoundTextField83(int columns) {
        super(columns);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int arc = 20;
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        super.paintComponent(g);
        g2d.dispose();
    }
}

class RightArrowButton extends JButton {
    RightArrowButton() {
        setOpaque(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        Polygon arrow = new Polygon();
        arrow.addPoint(5, 5);
        arrow.addPoint(width - 5, height / 2);
        arrow.addPoint(5, height - 5);

        g2d.setColor(Color.decode("#0084FF"));
        g2d.fillPolygon(arrow);

        g2d.dispose();
    }
}

