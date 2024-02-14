import javax.swing.*;
import java.awt.*;

public class ChatBubble extends JLabel {
    boolean isUser;
    public ChatBubble(String nick, String message, boolean isUser){

        setOpaque(true);
        setBackground(Color.black);
        setForeground(Color.WHITE);
        if (isUser){
            setHorizontalAlignment(SwingConstants.RIGHT);
            setText("<html><div style='width: 100%; background-color: #4169E1; padding: 5px; border-radius: 100px;'>" + message + "</div></html>");
        }else{
            setHorizontalAlignment(SwingConstants.LEFT);
            setText("<html><div style='width: 100%; background-color: #808080; padding: 5px; border-radius: 20px;'>"
                    + "<div style='font-size: smaller; color: #000000; text-align: left;'>"
                    + nick
                    + "</div>"
                    + message
                    + "</div></html>");
        }

    }

}
