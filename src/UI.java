import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class UI {
    // Variables:
    private static boolean useFallbackTheme = false;
    // Menu bar:
    static JMenuBar menuBar;
    static JMenu commandMenu;
    static JMenu usersMenu;
    static JMenu usersSubMenu;
    static JMenu creditsMenu;
    static JMenu helpMenu;
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            new Thread(() -> JOptionPane.showMessageDialog(frame, "System Theme not supported, using fallback theme")).start();
            useFallbackTheme = true;
        }

        initializeMenuBar();

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

    public static void initializeMenuBar() {
        menuBar = new JMenuBar();

        // Command menu
        commandMenu = new JMenu("Commands");

        JMenuItem helpCommand = new JMenuItem("Help");

        JMenuItem nicknameCommand = new JMenuItem("Set Nickname");

        JMenuItem quitCommand = new JMenuItem("Quit");

        // Online users menu
        usersMenu = new JMenu("Users");
        usersSubMenu = new JMenu("List");

        JMenuItem refreshButton = new JMenuItem("Refresh");

        usersMenu.add(usersSubMenu);

        // Credits menu
        creditsMenu = new JMenu("Credits");

        JMenuItem credits = new JMenuItem("Show Credits");

        menuBar.add(commandMenu);
        menuBar.add(usersMenu);
        menuBar.add(creditsMenu);

        frame.setJMenuBar(menuBar);
    }

    public static void initializeMenu() {
        mainMenu.setLayout(new GridLayout(0, 1));
        mainMenu.setBackground(Color.darkGray);

        title.setText("Connect 4");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        title.setForeground(Color.white);

        container.setBackground(Color.darkGray);

        container.add(sPBtn);
        container.add(lMBtn);
        container.add(oMBtn);

        mainMenu.add(title);
        mainMenu.add(container);

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
        for (int i = 0; i < frame.getComponents().length - 1; i++) {
            frame.remove(i);
        }

        frame.add(mainMenu, BorderLayout.CENTER);
        frame.revalidate();
    }

    public static void showBoard() {
        for (int i = 0; i < frame.getComponents().length - 1; i++) {
            frame.remove(i);
        }

        frame.getContentPane().add(board, BorderLayout.CENTER);
        frame.revalidate();
    }
}
