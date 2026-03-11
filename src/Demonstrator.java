import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demonstrator { //Main class
    private ReadConfig readConfig = new ReadConfig();
    private int turns = 0;

    public Demonstrator() {
        try {
            turns = readConfig.readTurns();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in file.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testOne() { //Testing building and wins
        System.out.println("Test One");
        Board board = new Board(); //Makes new board
        Player player1 = new Player(1, board); //Makes player for testing

        List<Player> players = new ArrayList<>(Arrays.asList(player1));

        player1.updateResources(Resource.WOOD, 100); //Maxes player's resources
        player1.updateResources(Resource.BRICK, 100);
        player1.updateResources(Resource.WHEAT, 100);
        player1.updateResources(Resource.SHEEP, 100);
        player1.updateResources(Resource.ORE, 100);

        board.placeSettlement(player1, 17); //Build settlement, city, and road
        board.upgradeCity(player1, 17);
        board.placeRoad(player1, 17, 18);
        board.placeRoad(player1, 17, 500); //Testing impossible roads
        board.placeRoad(player1, 17, 50);

        Gameplay play = new Gameplay(turns, players, board); //Creates testable gameflow
        play.runGame(); //Since player1 has > 7 resources, it will do at least one action every turn
        player1.printBuildings(); //Prints final buildings
    }

    public void testTwo() { //Testing Resources
        System.out.println("\nTest Two");
        Board board = new Board();
        Player player1 = new Player(1, board);

        List<Player> players = new ArrayList<>(Arrays.asList(player1));

        player1.updateResources(Resource.WOOD, 10); //Updates resources
        player1.updateResources(Resource.BRICK, 10);
        player1.updateResources(Resource.WHEAT, 10);
        player1.updateResources(Resource.SHEEP, 10);
        player1.updateResources(Resource.ORE, 10);

        player1.printResources(); //Shows current resource amounts
        board.printResources();

        board.placeSettlement(player1, 17); //Places settlements and roads to mimic first 2 turns
        board.placeRoad(player1, 17, 18);
        board.placeSettlement(player1, 2);
        board.placeRoad(player1, 2, 3);

        player1.printResources(); //No changes since setup turn
        board.printResources();

        board.upgradeCity(player1, 17); //Upgrade city

        player1.printResources(); //-1 wheat -2 ore
        board.printResources(); //+1 wheat +2 ore

        board.setTurn(3); //Resources start being consumed after turn 2
        board.placeRoad(player1, 3, 4); // -1 wood and -1 brick
        board.placeSettlement(player1, 4); // -1 for wood, brick, wheat, sheep

        player1.printResources(); //In total, -2 wood, -2 brick, +1 wheat, +1 sheep
        board.printResources(); // +2 wood, +2 brick, -1 wheat, -1 sheep

        player1.printBuildings(); //Prints constructed buildings

        System.out.println("Tile 0 generating resources"); //Generates resources for tile 0
        board.getTiles()[0].makeResources(board); //+2 wood

        player1.printResources(); //+2 wood
        board.printResources(); //-2 wood;
    }

    public void testThree() { //Tests roads and longest road
        System.out.println("\nTest Three");
        System.out.println();
        Board board = new Board();
        Player player1 = new Player(1, board);
        Player player2 = new Player(2, board);

        List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
        Gameplay play = new Gameplay(turns, players, board);

        board.placeRoad(player1, 0, 1);
        board.placeRoad(player1, 1, 0); //Tests for duplicate road
        board.placeRoad(player1, 0, 20);
        board.placeRoad(player1, 20, 19);
        board.placeRoad(player1, 20, 22);
        board.placeRoad(player1, 1, 2);
        board.placeRoad(player1, 2, 3);
        board.placeRoad(player1, 0, 5);
        board.calcLongestRoad(players); //Calculates longest road
        board.printLongestRoad(); //Longest road is 5

        play.printPoints(); //Prints current points, player 1 should have 2 VP from longest road

        board.placeSettlement(player2, 0); //If other player settlement blocks road
        board.calcLongestRoad(players);
        board.printLongestRoad(); //Longest road is now 3
        play.printPoints(); //Player 1 loses longest road points
    }

    public void testFour() { //Showing build limit
        System.out.println("\nTest Four");
        Board board = new Board();
        Player player1 = new Player(1, board);

        board.placeSettlement(player1, 18); //Builds multiple settlements
        board.placeSettlement(player1, 4);
        board.placeSettlement(player1, 9);
        board.placeSettlement(player1, 33);
        board.placeSettlement(player1, 41);
        board.placeSettlement(player1, 52);
        board.placeSettlement(player1, 0);
        board.placeSettlement(player1, 100);
        player1.printBuildings(); //Max 5 settlements
    }

    public void playGame() { //Runs game
        System.out.println("\nTest Gameplay");
        Gameplay play = new Gameplay(turns); //Shows how game should be run without testing or creating players or board beforehand
        play.runGame();
    }

    public void testRobber() {
        System.out.println("Testing Robber");
        Board board = new Board(); //Makes new board
        Player player1 = new Player(1, board);
        Player player2 = new Player(2, board);
        Player player3 = new Player(3, board);
        Player player4 = new Player(4, board);

        List<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        Gameplay play = new Gameplay(turns, players, board);
        TurnController turnController = new TurnController(board);
        for (Player player: players) {
            player.updateResources(Resource.WOOD, 1); //Maxes player's resources
            player.updateResources(Resource.BRICK, 2);
            player.updateResources(Resource.WHEAT, 1);
            player.updateResources(Resource.SHEEP, 2);
            player.updateResources(Resource.ORE, 3);
        }
        board.placeSettlement(player2, 17);
        board.placeSettlement(player2, 21);
        board.placeSettlement(player2, 22);
        board.placeSettlement(player2, 7);
        board.placeSettlement(player2, 10);
        board.placeSettlement(player3, 13);
        board.placeSettlement(player3, 0);
        player1.printResources();
        player2.printResources();
        player3.printResources();
        player4.printResources();
        board.printResources();
        turnController.activateRobber(player1, players);
        player1.printResources();
        player2.printResources();
        player3.printResources();
        player4.printResources();
        board.printResources();
    }

//    public void assignmentOneTests() {
//        main.testOne();
//        main.testTwo();
//        main.testThree();
//        main.testFour();
//        main.playGame();
//    }

    public static void main(String[] args) { //Main method for testing
        Demonstrator main = new Demonstrator();
        main.testRobber();
//        String[] inputs = {"Roll", "Go", "List", "Build settlement 5", "Build road 1, 2", "Build city 1"};
//        String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
//        Pattern pattern = Pattern.compile(regex);
//
//        for (String input : inputs) {
//            Matcher matcher = pattern.matcher(input);
//            if (matcher.matches()) {
//                if (matcher.group("building") == null) {
//                    System.out.println("Roll");
//                }
//                String aaa[] = matcher.group("command").split(" ");
//                System.out.println("Command: " + aaa[0]);
//                System.out.println("Sub: " + matcher.group("building"));
//                System.out.println("Args: " + matcher.group("nodeId"));
//                System.out.println("Chunga: " + matcher.group("fromNodeId"));
//                System.out.println("---");
//            }
//        }
    }
}