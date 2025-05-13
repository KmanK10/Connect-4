
# Connect 4

## Overview
Connect 4 is a Java-based implementation of the classic two-player board game, featuring both a console-based game and a graphical user interface (GUI). The program supports single-player and networked multiplayer modes, allowing players to compete locally or over a network. The game includes a server-client architecture for online play and a Swing-based GUI for an interactive experience.

## Features
- **Console Gameplay**: Play Connect 4 in the terminal with a text-based board, where players input column numbers (1-7) to drop red or yellow tokens.
- **Graphical Interface**: A Swing-based GUI with a menu for starting games and a visual board displaying the game state.
- **Networking**: Supports multiplayer games over a network using a server-client model, with message passing for real-time interaction.
- **Game Logic**: Implements standard Connect 4 rules, checking for wins in horizontal, vertical, and diagonal directions, with visual highlighting of winning tokens.
- **Customizable**: Includes a menu bar with options to set nicknames, view credits, and refresh online user lists (placeholder functionality).

## Usage
1. **Prerequisites**: Ensure Java is installed on your system.
2. **Running the Program**:
   - Compile and run `Main.java` to start both the server and client.
   - For console play, run `Game.java` directly to start a local two-player game.
   - For GUI and networked play, use `Main.java`, which initializes the server and client with a default port (12345).
3. **Console Play**:
   - Enter a column number (1-7) to drop a token.
   - The game alternates between players (red for Player 1, yellow for Player 2) and announces the winner when four tokens align.
4. **GUI Play**:
   - Use the main menu to select single-player or multiplayer modes.
   - The board displays the game state, with clickable areas planned for future interaction (currently placeholder).
5. **Networked Play**:
   - Start the server via `Server.java` or `Main.java`.
   - Run `Client.java` or use the GUI to connect to the server by entering the host IP and port.
   - Send messages to the server, which echoes them back; type "quit" to disconnect.

## File Structure
- `Game.java`: Handles the core game logic and console-based gameplay.
- `UI.java`: Implements the Swing-based GUI with a menu and game board.
- `Server.java`: Sets up the server to accept client connections and manage multiple clients.
- `Client.java`: Manages client-side networking, sending and receiving messages.
- `ClientHandler.java`: Server-side thread to handle individual client connections.
- `Main.java`: Entry point to start both server and client for networked play.

## Example Console Output
```
Welcome to connect 4, on your turn type the column you wish to play in (1-7) and press enter.

 1   2   3   4   5   6   7
( ) ( ) ( ) ( ) ( ) ( ) ( )
( ) ( ) ( ) ( ) ( ) ( ) ( )
( ) ( ) ( ) ( ) ( ) ( ) ( )
( ) ( ) ( ) ( ) ( ) ( ) ( )
( ) ( ) ( ) ( ) ( ) ( ) ( )
( ) ( ) ( ) ( ) ( ) ( ) ( )
◉ Player 1: 
```

## Dependencies
- Java Standard Library
- Java Swing (for GUI components)

## Contributing
Contributions are welcome! Please open an issue or submit a pull request with enhancements, bug fixes, or new features like improved GUI interaction or full networked gameplay.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.
