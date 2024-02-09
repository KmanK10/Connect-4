import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter writer;
    private static BufferedReader reader;
    public static final int DEFAULT_PORT = 12345;

    public static void start(int port) {
        try {
            System.out.println("Starting sockets");
            serverSocket = new ServerSocket(port);
            // the .accept() method waits for a client to connect before moving on in the code
            // you can use this with a loop and threads to handle multiple clients
            clientSocket = serverSocket.accept();
            System.out.println("Sockets started");

            // initialize the server input streams for one client
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Reader and writer started");

            new Thread (() -> {
                try {
                    String msg = "";
                    while (msg != null) {
                        msg = reader.readLine();
                        writer.println(msg);
                        System.out.println("got msg from client >>> " + msg);
                        if (msg.equals("stop")) {
                            stop();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void stop() throws IOException {
        clientSocket.close();
        reader.close();
        writer.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        start(Server.DEFAULT_PORT);
    }
}