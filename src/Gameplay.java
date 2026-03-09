import java.security.SecureRandom;
import java.util.*;

public class Gameplay { //Class for running gameflow
    private SecureRandom rand = new SecureRandom();
    private Board board; //Board
    private List<Player> players = new ArrayList<>(); //List for players
    private int turn = 0; //Current turn
    private int maxTurns; //Max turns

    public Gameplay (int maxTurns) { //Constructor
        this.board = new Board();
        this.maxTurns = maxTurns;
        Player player1 = new Player(1, board);
        Player player2 = new Player(2, board);
        Player player3 = new Player(3, board);
        Player player4 = new Player(4, board);
        this.players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
    }

    public Gameplay (int maxTurns, List<Player> players, Board board) { //Constructor for testing with customizable options
        this.board = board;
        this.maxTurns = maxTurns;
        this.players = players;
    }

    public void runGame() { //Gameflow
        if (maxTurns < 0 || maxTurns > 8192) { //If maxturns is valid
            System.out.println("Error: Turns must be between 0 and 8192");
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

    private int rollDice() { //Roll dice method
        int die1 = rand.nextInt(6 - 1 + 1) + 1; //Creates two dice from 1-6
        int die2 = rand.nextInt(6 - 1 + 1) + 1;
        int number = die1 + die2; //Returns rolled number
        return number;
    }

    private Node chooseNode() { //Chooses random node
        int node = rand.nextInt(54); //Number from 0 to 53
        return board.getNodes(node);
//        int counter = 0;
//        while (true) { //While loop to choose node
//            counter += 1;
//            int node = rand.nextInt(54); //Number from 0 to 53
//            int tileCount = board.getNodes(node).getTileSize();
//            if (tileCount == 3) { //Prioritizes nodes connected to 3 tiles
//                return board.getNodes(node);
//            }
//            else if (counter >= 5 && tileCount == 2) { //Chooses nodes with lower tile count over time
//                return board.getNodes(node);
//            }
//            else if (counter >= 10 && tileCount == 1) {
//                return board.getNodes(node);
//            }
//        }
    }

    private Node chooseAdjacentNode(Node node) { //Chooses random adjacent node
        ArrayList<Integer> nodeList = (ArrayList)node.getAdjacentNodes();
        int adjNode = nodeList.get(rand.nextInt(nodeList.size()));
        return board.getNodes(adjNode); //Returns adjacent node
    }

    private ArrayList<Node> getSettlementNodes(Player player) { //Gets settlement nodes from player
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node: player.getSettlements()) {
            nodes.add(node);
        }
        return nodes;
    }

    private ArrayList<Node> getRoadNodes(Player player) { //Gets road nodes from player
        ArrayList<Node> nodes = new ArrayList<>();
        for (Road road: player.getRoads()) {
            nodes.add(road.getEnd());
        }
        return nodes;
    }

    private void roundOne() { //First round
        for (Player player: players) { //For each player
            if (player instanceof HumanPlayer) {
                commandLine(player, true);
            }
            else {
                while (true) {
                    Node node = chooseNode(); //Chooses random node
                    if (board.placeSettlement(player, node) == true) { //Builds settlement
                        while (true) {
                            Node end = chooseAdjacentNode(node); //Gets adjacent node
                            if (board.placeRoad(player, node, end) == true) { //Builds road and prints info
                                System.out.println("Round 1 / " + player.getPlayerNumber() + ": built settlement at " + node + ", built road at (" + node + ", " + end + ")");
                                break;
                            }
                        }
                        isGameOver(player); //Checks if cheater already won
                        break;
                    }
                }
            }
        }
    }

    private void roundTwo() { //Second round
        for (int i=players.size()-1;i>=0;i--) { //Players play in reverse order
            if (players.get(i) instanceof HumanPlayer) {
                commandLine(players.get(i), true);
            }
            else {
                while (true) {
                    Node node = chooseNode(); //Chooses random node
                    if (board.placeSettlement(players.get(i), node) == true) { //If player places settlement
                        for (Tile tile: board.getTiles()) {
                            if (tile.getNodes().contains(node) && tile.getResource() != null) {
                                players.get(i).updateResources(tile.getResource(), 1, board);
                            }

                        }
                        while (true) {
                            Node end = chooseAdjacentNode(node); //Chooses adjacent node
                            if (board.placeRoad(players.get(i), node, end) == true) { //If player places road
                                System.out.println("Round 2 / " + players.get(i).getPlayerNumber() + ": built settlement at " + node + ", built road at (" + node + ", " + end + ")");
                                break; //Breaks
                            }
                        }
                        isGameOver(players.get(i)); //Checks if game is over
                        break;
                    }
                }
            }
        }
    }

    private boolean playRound(Player player) { //Method for playing rounds
        if (player instanceof HumanPlayer) {
            commandLine(player, false);
        }
        else {
            System.out.print(player.getPlayerNumber() + ": "); //Prints player
            makeResources(rollDice(), player); //Generates resources
            int action = -100; //Sets action out of bounds
            ArrayList<Node> settlementNodes = getSettlementNodes(player); //Gets settlement nodes
            ArrayList<Node> roadNodes = getRoadNodes(player); //Gets road nodes

            if (player.getTotalResources() > 7) { //If player has 7+ resources, checks available actions
                List<Boolean> actions = board.checkActions(player, getSettlementNodes(player), getRoadNodes(player)); //Passes new instances of settlement and road nodes to not alter originals
                while (actions.size() > 0 && player.getTotalResources() > 7) { //While action can be done
                    if (isGameOver(player)) { //Checks if player wins
                        return true;
                    }
                    action = rand.nextInt(actions.size()); //Chooses random action
                    if (actions.get(action) == true) { //If action true, breaks
                        break;
                    } else { //Else removes action
                        actions.remove(action);
                    }
                    action = -100;
                }

                if (action == 1) { //Build city
                    while (settlementNodes.size() > 0) {
                        int random = rand.nextInt(settlementNodes.size()); //Chooses random node
                        Node node = settlementNodes.get(random);
                        settlementNodes.remove(random);
                        if (board.upgradeCity(player, node)) { //Upgrades settlement
                            System.out.print(", upgraded city at node " + node);
                            break;
                        }
                    }
                } else if (action == 0 || action == 2) { //If player will build settlement or road
                    while (roadNodes.size() > 0 && player.getTotalResources() > 7) { //While available node to build
                        if (isGameOver(player)) { //Checks for win
                            return true;
                        }
                        int random = rand.nextInt(roadNodes.size());
                        Node node = roadNodes.get(random);
                        roadNodes.remove(random);
                        if (action == 0) { //Build settlement
                            if (board.placeSettlement(player, node)) {
                                System.out.print(", built settlement at node " + node);
                                break;
                            }
                        } else if (action == 2) { //Build road
                            for (int end : node.getAdjacentNodes()) {
                                if (board.placeRoad(player, node, board.getNodes(end))) {
                                    System.out.print(", built road at (" + node + ", " + end + ")");
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        }
        board.calcLongestRoad(players); //Calculates longest road
        System.out.println();
        return isGameOver(player); //Checks if player wins
    }

    private void makeResources(int number, Player player) {
        System.out.print("Rolled " + number);
        int total;
        Player owner1 = null;
        Player owner2 = null;
        switch (number) {
            case 6:
                owner1 = board.getTiles()[8].getOwner(); //If both tiles make same resource
                owner2 = board.getTiles()[10].getOwner(); //Gets tiles owners
                total = board.getTiles()[8].getResourcesProduced() + board.getTiles()[10].getResourcesProduced(); //Checks if total would overflow
                if (board.checkResources(Resource.ORE, -total) || (owner1 == owner2) || owner1 == null || owner2 == null) { //If tiles have same owner or none
                    board.getTiles()[8].makeResources(board); //ore
                    board.getTiles()[10].makeResources(board); //ore
                }
                break;
            case 7: //desert, no resources produced
                activateRobber(player);
                break;
            case 8:
                owner1 = board.getTiles()[2].getOwner();
                owner2 = board.getTiles()[14].getOwner();
                total = board.getTiles()[2].getResourcesProduced() + board.getTiles()[14].getResourcesProduced();
                if (board.checkResources(Resource.BRICK, -total) || (owner1 == owner2) || owner1 == null || owner2 == null) {
                    board.getTiles()[2].makeResources(board); //brick
                    board.getTiles()[14].makeResources(board); //brick
                }
                total = 0;
                break;
            default:
                for (Tile tile : board.getTiles()) { //Produces resources for each tile that matches number
                    if (number == tile.getNumber()) {
                        tile.makeResources(board);
                    }
                }
        }
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

    public void activateRobber(Player player) {
        Resource resource = null;
        for (Player p : players) {
            if (p != player) {
                if (p.getTotalResources() > 7) {
                    int newTotal = p.getTotalResources()/2;
                    while (p.getTotalResources() > newTotal) {
                        resource = chooseResource(p);
                        p.updateResources(resource, -1);
                        board.updateResources(resource, 1);
                    }
                }
            }
        }
        Tile tile = chooseTile();
        List<Player> owners = new ArrayList<>(tile.getOwners());
        owners.remove(player);
        owners.remove(null);
        Iterator<Player> iterator = owners.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getTotalResources() <= 0) {
                iterator.remove();
            }
        }
        if (owners.size() > 0) {
            Player robbedPlayer = owners.get(rand.nextInt(owners.size()));
            if (robbedPlayer != null) {
                int currentResources = robbedPlayer.getTotalResources();
                while (robbedPlayer.getTotalResources() == currentResources) {
                    resource = chooseResource(robbedPlayer);
                    System.out.print(" Player " + robbedPlayer.getPlayerNumber() + " has been robbed! (-1 " + resource + ")");
                    robbedPlayer.updateResources(resource, -1);
                    player.updateResources(resource, 1);
                }
            }
        }
    }

    public Resource chooseResource(Player player) {
        Resource[] resources = Resource.values();
        Resource resource = null;
        while (true) {
            resource = resources[rand.nextInt(resources.length)];
            if (player.getResources(resource) > 0) {
                break;
            }
        }
        return resource;
    }

    public Tile chooseTile() {
        Tile[] tiles = board.getTiles();
        Tile tile = tiles[rand.nextInt(tiles.length)];
        return tile;
    }

    public void commandLine(Player player, boolean startingRound) {
        boolean rolled = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter command: ");
        while (true) {
            String command = scan.next();
            if (command.equals("Go")) {
                if (rolled == false && startingRound == false) {
                    System.out.println("Haven't rolled yet");
                }
                else {
                    break;
                }
            }
            else if (command.equals("Roll") && rolled == false && startingRound == false) {
                makeResources(rollDice(), player);
                rolled = true;
                System.out.println();
            }
            else if (command.equals("List")) {
                player.printCards(board);
            }
            else if (command.equals("Build")) {
                if (scan.next().equals("settlement")) {
                    if (scan.hasNextInt()) {
                        int node = scan.nextInt();
                        if (board.placeSettlement(player, node)) {
                            System.out.println("Built settlement at node " + node);
                        }
                    }
                }
                if (scan.next().equals("city")) {
                    if (scan.hasNextInt()) {
                        int node = scan.nextInt();
                        if (board.upgradeCity(player, node)) {
                            System.out.println("Upgraded city at node " + node);
                        }
                    }

                }
                if (scan.next().equals("road")) {
                    if (scan.hasNextInt()) {
                        int start = scan.nextInt();
                        if (scan.hasNextInt()) {
                            int end = scan.nextInt();
                            if (board.placeRoad(player, start, end)) {
                                System.out.println("Built road at (" + start + ", " + end + ")");
                            }
                        }
                    }
                }
            }
        }
    }
}
