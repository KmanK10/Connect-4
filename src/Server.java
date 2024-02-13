import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    public static final int DEFAULT_PORT = 12345;

    public static void start(int port) {
        try {
            System.out.println("Starting server socket");
            serverSocket = new ServerSocket(port);
            // the .accept() method waits for a client to connect before moving on in the code
            // you can use this with a loop and threads to handle multiple clients
            new Thread (() -> {
                while (true) {
                    try {
                        clientSocket = serverSocket.accept();
                        ClientHandler clientHandler = new ClientHandler(clientSocket);
                        clientHandler.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Server socket started");
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}