import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoomPanel extends JPanel {
    JLabel numberOfClients;
    //JLabel numberOfmesseges;
    String roomName;
    JLabel name;

    RoomPanel(String roomName){
        Font font = new Font("Arial", Font.BOLD, 16);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.roomName = roomName;
        name = new JLabel(roomName);
        name.setFont(font);
        name.setForeground(Color.WHITE);
        numberOfClients = new JLabel("●");
        numberOfClients.setForeground(Color.GREEN);
        numberOfClients.setFont(font);
        //numberOfmesseges.setForeground(Color.GREEN);
        //numberOfmesseges = new JLabel("7");
        //setLayout(new BorderLayout());
        setBorder(border);
        setBackground(Color.GRAY);
        add(name);
        add(numberOfClients);
        //add(numberOfmesseges);
    }
}
