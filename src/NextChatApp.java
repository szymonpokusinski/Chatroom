import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;

public class NextChatApp {
    public static void main(String[] args) throws IOException, FontFormatException {
        final String[] nick = new String[1];


        LoginFrame loginFrame = new LoginFrame();
        loginFrame.jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object tagrget = e.getSource();
                if (tagrget == loginFrame.jButton) {
                    if (!loginFrame.jTextField.getText().isEmpty()) {
                        nick[0] = loginFrame.jTextField.getText();
                        loginFrame.dispose();

                        try {
                            Klient klient = new Klient(nick[0]);
                            PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                            out.println(klient.nickname);
                            out.flush();
                            System.out.println("Nawiązałem połączenie z serwerem" + String.valueOf(klient.socket));
                            GUI83 gui83 = new GUI83(klient);
                            gui83.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    try {
                                        klient.socket.close();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            });
                            Thread sendingMessage = new Thread(new KlientDoSerwera(klient.socket));
                            Thread receivingMessage = new Thread(new KlientOdSerwera(gui83.chatPanel, gui83.roomsMenu, gui83));
                            sendingMessage.start();
                            receivingMessage.start();
                        } catch (IOException | FontFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
