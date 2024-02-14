import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Klient> klients = new ArrayList<>();
    public Server() {

    }
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(50007);

        RoomManager.rooms.add(new Room("Ogolny"));
        RoomManager.rooms.add(new Room("Chat1"));
        RoomManager.rooms.add(new Room("Chat2"));

        Thread mainThread = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Czekam");
                    Socket client = server.accept();
                    BufferedReader inn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String nick = inn.readLine();
                    Klient klient = new Klient(nick, client);
                    klient.addDostepnePokoje(RoomManager.rooms);
                    System.out.println(klient.getRooms());
                    klients.add(klient);
                    klient.setRoom(RoomManager.rooms.get(0));
                    System.out.println(klients.get(0).room.name);

                    System.out.println(klient);
                    Thread receivingMessagesFromClient = new Thread(new SerwerOdKlienta(client));
                    receivingMessagesFromClient.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mainThread.start();
    }

    public synchronized static ArrayList<Klient> getKlients() {
        return new ArrayList<>(klients);
    }
    public static void joinToServer(String nick, Socket socket) throws IOException {
        for (Klient klient : klients) {
            if (klient.socket != socket) {
                PrintWriter out = new PrintWriter(klient.socket.getOutputStream());
                out.println("JOIN: " + nick);
                out.flush();
            }
        }
    }
    public synchronized static String getRooms() {
        System.out.println(RoomManager.rooms);
        return RoomManager.rooms.toString();
    }
}
