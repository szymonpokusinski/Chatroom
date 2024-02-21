import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Klient {
    public static final int PORT = 50007;
    public static final String HOST = "127.0.0.1";

    public String nickname;
    public Socket socket;
    public Room room;
    private ArrayList<Room> rooms;

    public Klient(String nickname) throws IOException {
        this.nickname = nickname;
        this.socket = new Socket(HOST, PORT);
        this.room = null;
    }

    public Klient(String nickname, Socket socket) {
        this.socket = socket;
        this.nickname = nickname;
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public String toString() {
        return "Klient{nickname='" + nickname + "', socket=" + this.socket + "}";
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addDostepnePokoje(ArrayList<Room> list) {
        rooms = new ArrayList<>(list);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
