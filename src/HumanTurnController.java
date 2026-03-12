import java.util.Scanner;

public class HumanTurnController extends PlayerTurnController {
    private ConsoleTextInputSource input = new ConsoleTextInputSource();
    private HumanCommandParser parser = new HumanCommandParser();

    public void takeTurn(Gameplay game, TurnController turnController) {
        boolean rolled = false;
        Player player = game.getCurrentPlayer();
        System.out.println("Enter command: ");
        while (true) {
            if (game.isGameOver(player)) {
                return;
            }
            String line = input.readLine();
            PlayerCommand command = parser.parse(line);
            if (command != null) {
                if (rolled == false) {
                    if (command instanceof RollDiceCommand) {
                        command.execute(game, turnController);
                        rolled = true;
                    } else if (command instanceof ListStatusCommand){
                        command.execute(game, turnController);
                    } else {
                        System.out.print("Roll before continuing");
                    }
                } else {
                    if (command instanceof RollDiceCommand) {
                        System.out.print("Already rolled");
                    } else if (command instanceof GoCommand) {
                        break;
                    } else {
                        command.execute(game, turnController);
                    }
                }
                System.out.println();
            }
            else {
                System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
        }
    }

    public void takeStartTurn(Gameplay game, TurnController turnController) {
        int turn = game.getTurn();
        Player player = game.getCurrentPlayer();
        Board board =  game.getBoard();
        String line = null;
        int node = 0;
        boolean builtSettlement = false;
        boolean builtRoad = false;
        System.out.println("Enter command: ");
        while (true) {
            line = input.readLine();
            PlayerCommand command = parser.parse(line);
            if (command != null) {
                String lines[] = line.split(" ");
                if (command instanceof GoCommand) {
                    if (builtSettlement == true && builtRoad == true) {
                        break;
                    }
                    else {
                        System.out.print("Must finish building before proceeding");
                    }
                }
                else if (command instanceof RollDiceCommand) {
                    System.out.print("Can't roll during the first 2 turns");
                }
                else if (command instanceof ListStatusCommand){
                    command.execute(game, turnController);
                }
                else if (command instanceof BuildSettlementCommand) {
                    if (builtSettlement == false) {
                        if (command.execute(game, turnController) != null) {
                            node = Integer.valueOf(lines[2]);
                            builtSettlement = true;
                            if (turn == 2) {
                                for (Tile tile : board.getTiles()) {
                                    if (tile.getNodes().contains(board.getNodes()[node]) && tile.getResource() != null) {
                                        player.updateResources(tile.getResource(), 1, board);
                                    }
                                }
                            }
                        }
                    }
                    else System.out.print("Build unsuccessful");
                }
                else if (command instanceof BuildRoadCommand) {
                    if (builtSettlement == true && builtRoad == false) {
                        int start = Integer.valueOf(lines[2].replace(",", ""));
                        int end = Integer.valueOf(lines[3]);
                        if (node == start || node == end) {
                            if (command.execute(game, turnController) != null) {
                                builtRoad = true;
                            }
                        }
                        else System.out.print("Build unsuccessful");
                    }
                    else System.out.print("Build unsuccessful");
                }
                else {
                    command.execute(game, turnController);
                }
            }
            else {
                System.out.print("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
            }
            System.out.println();
        }
    }
}