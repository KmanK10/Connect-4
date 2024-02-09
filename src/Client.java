import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static Scanner scanner = new Scanner(System.in);
    private static String input;

    public static void start(int port) {
        try {
            System.out.println("Please enter the hosts IP:\n");
            input = scanner.nextLine();

            System.out.println("Starting client on " + input);
            socket = new Socket(input, port);
            System.out.println("Client started");

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Reader and writer started");
            System.out.println("You may now enter messages below");

            // receiving
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

            // Sending
            new Thread(() -> {
                while (true) {
                    send(scanner.nextLine());
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(String msg) {
        System.out.println("Sending message");
        writer.println(msg);
    }
}