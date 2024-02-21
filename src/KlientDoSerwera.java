//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KlientDoSerwera implements Runnable {
    Socket socket;
    BufferedReader klaw;
    PrintWriter out;

    public KlientDoSerwera(Socket socket) throws IOException {
        this.socket = socket;
        this.klaw = new BufferedReader(new InputStreamReader(System.in));
        this.out = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        while (true) {
            String message;
            try {
                message = this.klaw.readLine();
            } catch (IOException var5) {
                throw new RuntimeException(var5);
            }

            if (message.equalsIgnoreCase("/koniec")) {
                System.out.println("Zakonczono Polaczenie z Serverem");

                try {
                    this.klaw.close();
                } catch (IOException var4) {
                    throw new RuntimeException(var4);
                }

                this.out.close();

                try {
                    this.socket.close();
                } catch (IOException var3) {
                    throw new RuntimeException(var3);
                }
            } else {
                this.out.println(message);
                this.out.flush();
            }
        }
    }
}
