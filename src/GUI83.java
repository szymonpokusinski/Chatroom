import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;

public class GUI83 extends JFrame implements ActionListener {
    RoomPanel roomPanel;
    ChatArea2 chatPanel;
    JTextField textField;
    Klient klient;

    GUI83(Klient klient) throws FontFormatException, IOException {

        this.klient = klient;

        setSize(600, 500);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        roomPanel = new RoomPanel(this.klient);
        roomPanel.makeRoom.addActionListener(this);
        roomPanel.dostepnePokoje.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Dostepne pokoje: ");
                String message = "!!GETSROOMS!!";
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

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        addRoomPanel("Ogolny");
        addRoomPanel("Chat1");
        addRoomPanel("Chat2");

        roomPanel.makeRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomName;
                MakeRoomFrame makeRoomFrame = new MakeRoomFrame();
                makeRoomFrame.jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!makeRoomFrame.jTextField.getText().isEmpty()){
                            System.out.println(makeRoomFrame.jTextField.getText());
                            addRoomPanel(makeRoomFrame.jTextField.getText());
                            try {
                                PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                                String message = "!!NEWROOM!!"+makeRoomFrame.jTextField.getText();
                                out.println(message);
                                out.flush();

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            makeRoomFrame.dispose();
                        }
                    }
                });
            }
        });

        add(roomPanel);

        chatPanel = new ChatArea2("chat1", this.klient);
        add(chatPanel);

        setVisible(true);
    }

    public void addRoomPanel(String name){
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        JLabel jLabel = new JLabel(name);
        panel.add(jLabel);
        panel.setBorder(border);
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
                chatPanel.changeRoom(jLabel.getText());
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
        roomPanel.main.add(panel);
        roomPanel.revalidate();
        roomPanel.repaint();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public void czyszczenie(){
        roomPanel.main.revalidate();
        roomPanel.main.repaint();
    }
}

