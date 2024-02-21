import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class KlientOdSerwera implements Runnable {
    private final Socket socket;
    private final BufferedReader inputReader;
    private final ChatArea2 chatArea;
    RoomsMenu roomsMenu;
    GUI83 gui83;
    Klient klient;

    public KlientOdSerwera(ChatArea2 chatArea, RoomsMenu roomsMenu, GUI83 gui83) {
        this.socket = chatArea.klient.getSocket();

        try {
            this.inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.chatArea = chatArea;
        this.roomsMenu = roomsMenu;
        this.gui83 = gui83;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = inputReader.readLine();

                if (message != null) {
                    if (message.equalsIgnoreCase("/koniec")) {
                        inputReader.close();
                        socket.close();
                        return;
                    } else if (message.startsWith("!!ROOMS!!")) {
                        String[] parts = message.split("!!ROOMS!!", 2);
                        String rooms = parts[1];
                        String[] parts2 = rooms.split("!");
                        roomsMenu.czyszczenie();
                        for (String s : parts2) {
                            gui83.addRoomPanel(s);
                        }
                    } else if (message.startsWith("!!MESSAGE!!")) {
                        String[] parts = message.split("!!MESSAGE!!", 2);
                        String mes = parts[1];
                        String[] parts2 = mes.split("#");
                        if (mes.isEmpty()) {

                        } else {
                            chatArea.panelMessage.removeAll();
                            for (String s : parts2) {
                                String[] parts3 = s.split("&&");
                                if (parts3[0].equals(gui83.klient.nickname)) {
                                    chatArea.panelMessage.add(new ChatBubble("Me", parts3[1], true));
                                    chatArea.panelMessage.add(Box.createRigidArea(new Dimension(0, 10)));
                                } else {
                                    chatArea.panelMessage.add(new ChatBubble(parts3[0], parts3[1], false));
                                    chatArea.panelMessage.add(Box.createRigidArea(new Dimension(0, 10)));
                                }
                            }
                            chatArea.panelMessage.revalidate();
                            chatArea.panelMessage.repaint();
                        }

                    } else if (message.startsWith("!!NROFCLIENTINROOM!!")) {
                        numberOfRooms(message);

                    } else if (message.startsWith("!!CLIENTLIST!!")) {
                        readingTheCleintList(message);

                    } else {
                        String[] parts = message.split("&&");
                        ChatBubble chatBubble = new ChatBubble(parts[0], parts[1], false);
                        chatBubble.setAlignmentX(Component.LEFT_ALIGNMENT);
                        chatArea.panelMessage.add(chatBubble);

                        chatArea.panelMessage.add(Box.createVerticalStrut(10));
                        chatArea.panelMessage.revalidate();
                        chatArea.panelMessage.repaint();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void displayMessageInPanel(String message) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);
        chatArea.displayMessage(message);
    }

    private void readingTheCleintList(String message) {
        String[] parts = message.split("!!CLIENTLIST!!", 2);
        System.out.println(parts[1]);
        String[] clientNicks = parts[1].split("&&");
        System.out.println(Arrays.toString(clientNicks));
    }

    public void numberOfRooms(String message) {

        String[] parts = message.split("!!NROFCLIENTINROOM!! ", 2);
        String[] p2 = parts[1].split("#");
        int i = 0;
        for (RoomPanel roomPanel : GUI83.roomPanels) {
            for (String s : p2) {
                if (roomPanel.name.getText().equals(s.split("&&")[0])) {
                    if (s.split("&&")[1].equals("0")) {
                        roomPanel.numberOfClients.setText("●0");
                        roomPanel.numberOfClients.setForeground(Color.RED);
                    } else {
                        roomPanel.numberOfClients.setText("● " + s.split("&&")[1]);
                        roomPanel.numberOfClients.setForeground(Color.green.darker());
                    }
                }
            }
        }
    }


}
