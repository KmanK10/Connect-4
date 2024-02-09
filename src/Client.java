import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;

    public static void start(int port) {
        try {
            System.out.println("Starting client");
            socket = new Socket("127.0.0.1", port);
            System.out.println("Client started");

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Reader and writer started");

            new Thread(() -> {
                try {
                    String msg = "";

                    while (msg != null) {
                        msg = reader.readLine();
                        System.out.println("\rMessage: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(String msg) {
        System.out.println("Sending message");
        writer.write(msg);
    }

    public static void main(String[] args) {
        start(Server.DEFAULT_PORT);

        send("test");
    }
}