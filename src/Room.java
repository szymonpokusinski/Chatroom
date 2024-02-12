import java.util.ArrayList;
import java.util.List;

public class Room {
    public String name;
    public List<Klient> klients = new ArrayList<>();
    public List<Wiadomosc> wiadomoscs = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public void addKlient(Klient klient) {
        klients.add(klient);
    }

    public void removeKlient(Klient klient) {
        klients.remove(klient);
    }

    public String getName() {
        return name;
    }
}
