import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    public static ArrayList<Room> rooms = new ArrayList<>();
    RoomManager(){
    }
    public static ArrayList<Room> getRooms() {
        return rooms;
    }
    public static void setRooms(ArrayList<Room> rooms) {
        RoomManager.rooms = rooms;
    }
    public void addRooms(Room room){
        rooms.add(room);
    }
}