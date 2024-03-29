import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatApp {
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

                        } catch (IOException | FontFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
