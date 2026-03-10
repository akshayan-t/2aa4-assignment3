import java.util.List;
import java.util.ArrayList;

/**
 * Controls the turn-based logic of the Catan game
 */
public class TurnController {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    
    public TurnController(Board board) {
        this.board = board;
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
    
    public void startGame() {
        System.out.println("Game started with " + players.size() + " players");
        currentPlayerIndex = 0;
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        System.out.println("Now it's Player " + currentPlayerIndex + "'s turn");
    }
    
    public Player getCurrentPlayer() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(currentPlayerIndex);
    }
    
    public void handleBuildAction(Player player, Node node) {
        board.placeSettlement(node, player);
    }
    
    public void handleUpgradeCity(Player player, Node node) {
        board.upgradeToCity(node, player);
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    @Override
    public String toString() {
        return "TurnController{currentPlayer=" + currentPlayerIndex + ", totalPlayers=" + players.size() + "}";
    }
}
