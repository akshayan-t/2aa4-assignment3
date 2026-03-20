import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotTurnController extends PlayerTurnController {
    private Random rand = new Random();
    private BotDecisionPolicy botRules = new BotDecisionPolicy();
    private CommandManager commandManager = new CommandManager();

    public void takeTurn(Gameplay game, TurnController turnController) { //Runs turns after first 2 turns
        Player player = game.getCurrentPlayer();
        List<Player> players = game.getPlayers();
        turnController.makeResources(turnController.rollDice(), player, players); //Generates resources
        game.setTurnPhase(TurnPhase.ROLLED); //Sets phase to rolled

        while (player.getTotalResources() > 7) { //If player has 7+ resources, checks available actions
            if (game.isGameOver(player)) { //Checks for win
                return;
            }
            PlayerCommand command = botRules.chooseNextCommand(game, turnController, commandManager); //Gets command
            if (command == null) {
                break;
            }
            System.out.print(", ");
            command.execute(game, turnController); //Executes command
        }
    }

    public void takeStartTurn(Gameplay game, TurnController turnController) { //Runs first 2 turns
        Board board = game.getBoard();
        Player player = game.getCurrentPlayer();
        int turn = game.getTurn();
        if (turn == 1) {
            roundOne(player, board);
        }
        else if (turn == 2) {
            roundTwo(player, board);
        }
    }

    private void roundOne(Player player, Board board) { //First round
        while (true) {
            Node node = chooseNode(board); //Chooses random node
            if (board.placeSettlement(player, node) == true) { //Builds settlement
                while (true) {
                    Node end = chooseAdjacentNode(node, board); //Gets adjacent node
                    if (board.placeRoad(player, node, end) == true) { //Builds road and prints info
                        System.out.println("Round 1 / " + player.getPlayerNumber() + ": built settlement at " + node + ", built road at (" + node + ", " + end + ")");
                        break;
                    }
                }
                break;
            }
        }
    }

    private void roundTwo(Player player, Board board) { //Second round
        while (true) {
            Node node = chooseNode(board); //Chooses random node
            if (board.placeSettlement(player, node) == true) { //If player places settlement
                for (Tile tile : board.getTiles()) {
                    if (tile.getNodes().contains(node) && tile.getResource() != null) {
                        player.updateResources(tile.getResource(), 1, board); //Generates resources for settlement placed this round
                    }
                }
                while (true) {
                    Node end = chooseAdjacentNode(node, board); //Chooses adjacent node
                    if (board.placeRoad(player, node, end) == true) { //If player places road
                        System.out.println("Round 2 / " + player.getPlayerNumber() + ": built settlement at " + node + ", built road at (" + node + ", " + end + ")");
                        break; //Breaks
                    }
                }
                break;
            }
        }
    }

    private Node chooseNode(Board board) { //Chooses random node
        int node = rand.nextInt(54); //Number from 0 to 53
        return board.getNodes(node);
    }

    private Node chooseAdjacentNode(Node node, Board board) { //Chooses random adjacent node
        ArrayList<Integer> nodeList = (ArrayList)node.getAdjacentNodes();
        int adjNode = nodeList.get(rand.nextInt(nodeList.size()));
        return board.getNodes(adjNode); //Returns adjacent node
    }
}
