import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GUI83 extends JFrame implements ActionListener {
    RoomsMenu roomsMenu;
    ChatArea2 chatPanel;
    JTextField textField;
    Klient klient;
    public static ArrayList<RoomPanel> roomPanels = new ArrayList<>();

    GUI83(Klient klient) throws FontFormatException, IOException {

        this.klient = klient;

        setSize(600, 500);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        roomsMenu = new RoomsMenu(this.klient);
        roomsMenu.makeRoom.addActionListener(this);
        roomsMenu.dostepnePokoje.addMouseListener(new MouseListener() {
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

        roomsMenu.makeRoom.addActionListener(new ActionListener() {
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

        add(roomsMenu);

        chatPanel = new ChatArea2("chat1", this.klient);
        add(chatPanel);

        setVisible(true);
    }

    public void addRoomPanel(String name){
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        RoomPanel panel = new RoomPanel(name);
        roomPanels.add(panel);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(panel.getBackground().darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.GRAY.darker());
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                String chat = panel.name.getText();
                System.out.println(chat);
                chatPanel.changeRoom(panel.name.getText());
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
        roomsMenu.main.add(panel);
        roomsMenu.revalidate();
        roomsMenu.repaint();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
    public void czyszczenie(){
        roomsMenu.main.revalidate();
        roomsMenu.main.repaint();
    }
}

