import java.util.List;
import java.util.ArrayList;

/**
 * Manages the gameplay mechanics of Catan
 */
public class Gameplay {
    private Board board;
    private List<Player> players;
    private int currentTurn;
    private int maxRound;
    private int currentRound;
    
    public Gameplay(Board board) {
        this.board = board;
        this.players = new ArrayList<>();
        this.currentTurn = 0;
        this.maxRound = 10; // Default max rounds
        this.currentRound = 1;
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
    
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }
    
    public int getMaxRound() {
        return maxRound;
    }
    
    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }
    
    public int getCurrentRound() {
        return currentRound;
    }
    
    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    public void playRound() {
        System.out.println("Playing round " + currentRound);
        for (int i = 0; i < players.size(); i++) {
            currentTurn = i;
            Player currentPlayer = players.get(i);
            System.out.println("Player " + i + "'s turn");
            // Game logic for a turn would go here
        }
        currentRound++;
    }
    
    public Player getGameOver() {
        // Check if any player has won
        for (Player player : players) {
            if (player.calculatePoints() >= 10) {
                return player;
            }
        }
        if (currentRound > maxRound) {
            // Return player with most points
            Player winner = players.get(0);
            for (Player player : players) {
                if (player.calculatePoints() > winner.calculatePoints()) {
                    winner = player;
                }
            }
            return winner;
        }
        return null;
    }
    
    public void printPoints() {
        System.out.println("Current Scores:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + i + ": " + players.get(i).calculatePoints() + " points");
        }
    }
    
    @Override
    public String toString() {
        return "GamePlay{round=" + currentRound + "/" + maxRound + ", players=" + players.size() + "}";
    }
}
