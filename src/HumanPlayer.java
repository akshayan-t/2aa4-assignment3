import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(int playerNumber, Board board) { //Constructor
        super(playerNumber, board);
    }
    public HumanPlayer(int playerNumber, Board board, PlayerColour colour) { //Constructor
        super(playerNumber, board, colour);
    }
}
