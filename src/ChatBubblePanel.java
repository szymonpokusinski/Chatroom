import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ChatBubblePanel extends JPanel {
    private final String nick;
    private final String message;
    private final boolean isUser;
    private final int fixedHeight = 60;
    private final int firstMessageHeight = 30;

    public ChatBubblePanel(String nick, String message, boolean isUser) {
        this.nick = nick;
        this.message = message;
        this.isUser = isUser;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (isFirstMessage()) {
            setPreferredSize(new Dimension(300, firstMessageHeight));
        } else {
            setPreferredSize(new Dimension(300, fixedHeight));
        }
    }

    private boolean isFirstMessage() {
        // Sprawdź, czy to jest pierwsza wiadomość
        // Możesz dostosować tę logikę do własnych potrzeb
        // Na przykład, sprawdzając, czy panel nie ma poprzedników w kontenerze
        return getParent() == null || getParent().getComponentCount() == 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int bubbleWidth = getWidth() - 1;
        int bubbleHeight = getHeight() - 1;
        int arc = 20;

        if (isUser) {
            g2d.setColor(new Color(0, 153, 255));
        } else {
            g2d.setColor(new Color(128, 128, 128));
        }

        g2d.fill(new RoundRectangle2D.Double(0, 0, bubbleWidth, bubbleHeight, arc, arc));

        if (isUser) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(Color.WHITE);
        }

        Font font = new Font("Arial", Font.PLAIN, 24);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);

        int nickX = isUser ? 5 : bubbleWidth - metrics.stringWidth(nick) - 5;
        int nickY = 5 + metrics.getAscent();
        g2d.drawString(nick, nickX, nickY);

        int messageX = (bubbleWidth - metrics.stringWidth(message)) / 2;
        int messageY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g2d.drawString(message, messageX, messageY);

        g2d.dispose();
        super.paintComponent(g);
    }

}
