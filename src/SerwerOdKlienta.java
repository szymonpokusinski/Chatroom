//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class SerwerOdKlienta implements Runnable {
    Socket socket;
    BufferedReader in;

    public SerwerOdKlienta(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        while(true) {
            try {
                String message = this.in.readLine();
                String getclientList = "!!CLIENTSLIST!!";
                String changeRoom = "!!CHANGEROOM!!";
                String newRoom = "!!NEWROOM!!";
                String getRoom = "!!GETSROOMS!!";

                if (message.equals(getRoom)) {

                    sendingAvailableRooms();

                } else if (message.startsWith(changeRoom)) {
                    String[] parts = message.split(changeRoom,2);
                    String roomName = parts[1];
                    for (Klient klient: Server.getKlients()){
                        if(klient.socket == socket){
                            Room oldRoom = klient.room;
                            oldRoom.removeKlient(klient);
                            for (Room room: RoomManager.rooms){
                                if(roomName.equals(room.getName())){
                                    klient.setRoom(room);
                                    room.addKlient(klient);

                                    roomsAndNumberOfClients();

                                    PrintWriter out = new PrintWriter(socket.getOutputStream());

                                    String mesout = "";
                                    for (Wiadomosc w : klient.room.wiadomoscs){
                                        mesout += w.nick+"&&"+w.tresc+"#";
                                    }
                                    out.println("!!MESSAGE!!" + mesout);
                                    out.flush();
                                    break;
                                }
                            }
                        }
                    }
                } else if (message.startsWith(newRoom)) {
                    String[] parts = message.split(newRoom,2);
                    String nameNewRoom = parts[1];
                    System.out.println("Name new room: " + nameNewRoom);
                    Boolean exist = null;
                    for(Room room: RoomManager.rooms){
                        if (room.name.equals(nameNewRoom)){
                            exist = true;
                        }
                    }
                    if (Boolean.TRUE.equals(exist)){
                        System.out.println("Taki pokoj juz istnieje");
                    } else {
                        RoomManager.rooms.add(new Room(nameNewRoom));
                        System.out.println("Lista nowych Pokoi: " + RoomManager.getRooms());
                        sendingMessagesToEveryone("!!ROOMS!!"+checkingAvailableRooms());
                    }

                } else if (message.equals(getclientList)) {

                    Klient sender = lookingForAClient();
                    String message1 = "!!CLIENTLIST!!" + availableClientsInTheRoomByMessage(sender.room);
                    System.out.println("Wiadomość: " + message1);
                    sendMessage(message1, socket);

                } else {
                    Klient sender;
                    System.out.println(message);
                    for(Klient klient: Server.getKlients()){
                        if (klient.socket == socket){
                            sender = klient;
                            System.out.println("Wiadomosc: " + message + " od: " + sender.nickname + " Do pokoju: " + sender.room.name);
                            dodawanieWiadomosciDoPokoju(new Wiadomosc(message, sender.nickname), sender.room);
                            System.out.println("Lista wiadomosci pokoju: " + sender.room.wiadomoscs);
                            System.out.println(sender.room.wiadomoscs.get(0).nick);
                            for (Wiadomosc wiadomosc: sender.room.wiadomoscs){
                                System.out.println(wiadomosc.tresc);
                            }
                            for (Klient klient1: Server.getKlients()){
                                if (sender.room == klient1.room && sender.socket != klient1.socket){
                                    PrintWriter out = new PrintWriter(klient1.socket.getOutputStream());
                                    out.println(sender.nickname+"&&"+message);
                                    out.flush();
                                }
                            }
                        }
                    }

                    }
                } catch (IOException e) {
                    System.out.println("Ngałe zerwanie połączenia");
                    throw new RuntimeException(e);
            } finally {
                try {
                    System.out.println("zamkniecie połączenia");
                    in.close();
                    socket.close();
                    //break;
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                }
            }

        }
    }
    private void dodawanieWiadomosciDoPokoju(Wiadomosc wiadomosc, Room room){
        room.wiadomoscs.add(wiadomosc);
    }
    private String availableClientsInTheRoomByMessage(Room room){
        StringBuilder message = new StringBuilder();
        for (Klient klient : room.klients){
            message.append("&&").append(klient.nickname);
        }
        return message.toString();
    }
    private Klient lookingForAClient(){
        for (Klient klient : Server.getKlients()){
            if (klient.socket == socket){
                return klient;
            }
        }
        return null;
    }
    private void sendMessage(String message, Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(message);
        out.flush();
    }
    private String checkingAvailableRooms(){
        StringBuilder message = new StringBuilder();
        for (Room room : RoomManager.rooms){
            message.append(room.name).append("!");
        }
        return message.toString();
    }
    private void sendingAvailableRooms() throws IOException {
        String message = "!!ROOMS!!" + checkingAvailableRooms();
        sendMessage(message, socket);
    }
    private void sendingMessagesToEveryone(String message) throws IOException {
        for (Klient klient : Server.getKlients()){
            sendMessage(message, klient.socket);
        }
    }
    private void roomsAndNumberOfClients() throws IOException {
        StringBuilder message = new StringBuilder(" ");
        for (Room room : RoomManager.rooms){
            message.append(room.name).append("&&").append(room.klients.size()).append("#");
        }
        System.out.println(message);
        sendingMessagesToEveryone("!!NROFCLIENTINROOM!!"+message);
    }
}




