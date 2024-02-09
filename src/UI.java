import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UI {
    // Variables:
    // Components:
    private static JFrame frame = new JFrame();
    // Menu
    private static JPanel mainMenu = new JPanel();
    private static JLabel title = new JLabel();
    private static JPanel container = new JPanel();
    private static JButton sPBtn = new JButton();
    private static JButton lMBtn = new JButton();
    private static JButton oMBtn = new JButton();

    // Game
    private static JPanel board = new JPanel();
    private static JPanel[] spots = new JPanel[42];

    public static void initialize() {
        frame.setTitle("Connect 4");
        frame.setLayout(new BorderLayout(10, 10));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(875, 750);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        initializeMenu();
        initializeBoard();

        showMenu();

        frame.setVisible(true);
    }

    public static void initializeMenu() {
        mainMenu.setLayout(new BorderLayout());
        mainMenu.setBackground(Color.darkGray);
        title.setText("Connect 4");
        mainMenu.add(title, BorderLayout.NORTH);

        mainMenu.setVisible(true);
    }

    public static void initializeBoard() {
        board.setLayout(new GridLayout(7, 6, 5, 5));
        board.setBackground(Color.darkGray);

        for (JPanel spot : spots) {
            spot = new JPanel();

            spot.setBackground(Color.WHITE);
            spot.setBorder(new LineBorder(Color.BLACK, 1, true));

            board.add(spot);

            spot.setVisible(true);
        }

        board.setVisible(true);
    }

    public static void showMenu() {
        if (frame.getComponents().length > 0) {
            frame.remove(0);
        }

        frame.getContentPane().add(mainMenu);
        frame.revalidate();
    }

    public static void showBoard() {
        if (frame.getComponents().length > 0) {
            frame.remove(0);
        }

        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.revalidate();
    }
}
