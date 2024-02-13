import java.io.IOException;

public class Main {
   public static void main(String[] args) {
       //UI.initialize();

       Server.start(Server.DEFAULT_PORT);
       Client.start(Server.DEFAULT_PORT);
   }
}