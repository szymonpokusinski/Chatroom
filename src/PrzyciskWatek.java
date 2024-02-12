public class PrzyciskWatek implements Runnable{
    PrzyciskWatek(){
        }

    @Override
    public void run() {
        while (true){
            System.out.println(RoomManager .getRooms());
        }
    }
    public static void main(String[] args){
        Thread thread = new Thread(new PrzyciskWatek());
        thread.start();
    }
}
