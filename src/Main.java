import java.io.IOException;

public class Main {
   public static void main(String[] args) {
       Server.start(Server.DEFAULT_PORT);
       Client.start(Server.DEFAULT_PORT);

       Client.send("test");
    }
}