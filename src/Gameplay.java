import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gameplay { //Class for running gameflow
    private SecureRandom rand = new SecureRandom();
    private Board board; //Board
    private List<Player> players = new ArrayList<>(); //List for players
    private Player currentPlayer;
    private int turn = 0; //Current turn
    private int maxTurns; //Max turns
    private TurnController turnController;

    public Gameplay (int maxTurns) { //Constructor
        this.board = new Board();
        this.maxTurns = maxTurns;
        Player player1 = new HumanPlayer(1, board, PlayerColour.RED);
        Player player2 = new Player(2, board, PlayerColour.BLUE);
        Player player3 = new Player(3, board, PlayerColour.GREEN);
        Player player4 = new Player(4, board, PlayerColour.ORANGE);
        this.players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        this.turnController = new TurnController(board);
    }

    public Gameplay (int maxTurns, List<Player> players, Board board) { //Constructor for testing with customizable options
        this.board = board;
        this.maxTurns = maxTurns;
        this.players = players;
        this.turnController = new TurnController(board);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public void runGame() { //Gameflow
        if (maxTurns <= 0 || maxTurns > 8192) { //If maxturns is valid
            System.out.println("Error: Turns must be between 1 and 8192");
            return;
        }
        turn = 1;
        board.setTurn(turn);
        roundOne(); //Round 1
        printPoints(); //Prints points
        if (maxTurns > 1) {
            turn++;
            board.setTurn(turn);
            roundTwo(); //Round 2
            printPoints();
        }
        if (maxTurns > 2) {
            while (turn < maxTurns) { //Plays game until turn reaches max turns
                turn++; //Increments turn each time
                board.setTurn(turn); //Updates board's turn
                for (Player player : players) { //For each player
                    currentPlayer = player;
                    System.out.print("Round " + turn + " / ");
                    if (playRound(player)) { //Plays round, if player wins
                        System.out.println();
                        System.out.println("Player " + player.getPlayerNumber() + " wins!");
                        printResults(); //Print win message and results
                        return;
                    }
                }
                printPoints(); //Prints points after each round
            }
        }
        printResults(); //Prints results if nobody wins
    }

    public int rollDice() { //Roll dice method
        return turnController.rollDice();
    }

    private void roundOne() { //First round
        for (Player player: players) { //For each player
            currentPlayer = player;
            PlayerTurnController controller = player.getController();
            controller.takeStartTurn(this, turnController);
        }
    }

    private void roundTwo() { //Second round
        for (int i=players.size()-1;i>=0;i--) { //Players play in reverse order
            currentPlayer = players.get(i);
            PlayerTurnController controller = players.get(i).getController();
            controller.takeStartTurn(this, turnController);
        }
    }

    private boolean playRound(Player player) { //Method for playing rounds
        if (isGameOver(player)) {
            return true;
        }
        PlayerTurnController controller = player.getController();
        System.out.print(player.getPlayerNumber() + ": "); //Prints player
        controller.takeTurn(this, turnController);
        board.calcLongestRoad(players); //Calculates longest road
        if (!(player instanceof HumanPlayer)) {
            System.out.println();
        }
        return isGameOver(player); //Checks if player wins
    }

    public void printPoints() { //Prints points
        System.out.print("Victory points: ");
        for (Player player : players) {
            System.out.print(player.calcPoints(players, board) + " ");
        }
        System.out.println();
    }

    public void printResults() { //Prints results
        System.out.print("\nFinal points: ");
        for (Player p : players) {
            System.out.print(p.calcPoints(players, board) + " ");
        }
        System.out.println("\nGAME TERMINATING");
    }

    public boolean isGameOver(Player player) { //Checks if player wins
        if (player.calcPoints(players, board) >= 10) { //If victory points >= 10
            return true;
        }
        return false;
    }
}
