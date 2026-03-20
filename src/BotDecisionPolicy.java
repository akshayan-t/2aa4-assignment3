import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotDecisionPolicy { //Chooses command for computer
    private Random rand = new Random();
    private BenefitScoringStrategy strategy = new BenefitScoringStrategy();

    public PlayerCommand chooseNextCommand(Gameplay game, TurnController turnController, CommandManager commandManager) { //Chooses next command
        Board board = game.getBoard();
        Player player = game.getCurrentPlayer();
        List<Player> players = game.getPlayers();

        List<PlayerCommand> legalActions = board.getLegalActions(player, getSettlementNodes(player, board), getRoadNodes(player, board)); //Passes new instances of settlement and road nodes to not alter originals
        PlayerCommand bestMove = null;
        double maxVal = -1.0;
        List<PlayerCommand> tiedMoves = new ArrayList<>();

        for (PlayerCommand cmd : legalActions) {
            double currentVal = strategy.calculateValue(cmd, game, turnController);

            if (currentVal > maxVal) {
                maxVal = currentVal;
                bestMove = cmd;
                tiedMoves.clear();
                tiedMoves.add(cmd);
            } else if (currentVal == maxVal) {
                tiedMoves.add(cmd);
            }
        }

        // R3.2: Tie-break with random action
        if (!tiedMoves.isEmpty()) {
            PlayerCommand finalChoice = tiedMoves.get(rand.nextInt(tiedMoves.size()));
            return finalChoice;
        }

        return bestMove; //Returns null if no actions available
    }

    private ArrayList<Node> getSettlementNodes(Player player, Board board) { //Gets settlement nodes from player
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node: player.getSettlements()) {
            nodes.add(node);
        }
        return nodes;
    }

    private ArrayList<Node> getRoadNodes(Player player, Board board) { //Gets road nodes from player
        ArrayList<Node> nodes = new ArrayList<>();
        for (Road road: player.getRoads()) {
            nodes.add(road.getEnd());
        }
        return nodes;
    }
}
