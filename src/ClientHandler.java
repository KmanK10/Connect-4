import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void start() {
        // initialize the server input streams for one client
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Server reader and writer started");

            new Thread (() -> {
                try {
                    String msg = "";
                    while (msg != null) {
                        msg = reader.readLine();
                        writer.println(msg);
                        System.out.println("Got msg from client >>> " + msg);
                        if (msg.equalsIgnoreCase("quit")) {
                            close();
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

    @Override
    public void interrupt() {
        close();
    }

    private void close() {
        try {
            socket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
