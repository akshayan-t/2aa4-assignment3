import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Board { //Class to act as board for game
    private HashMap<Resource, Integer> resources = new HashMap<>(); //Hashmap for resources

    private Node[] nodes = new Node[54]; //Nodes array
    private Tile[] tiles = new Tile[19]; //Tiles array
    private List<Road> roads = new ArrayList<>(); //Road list
    private int turn = 0; //Turn counter
    private TurnController turnController;

    private Player longestRoad = null; //Longest road owner
    private int longestRoadLength = 0; //Longest road length

    public Board() { //Constructor method
        SetupGame setup = new SetupGame(); //Sets up game
        this.tiles = setup.getTiles(); //Gets tiles and nodes from setup
        this.nodes = setup.getNodes();

        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i].setNodes(this); //Sets up all tiles with this as board
        }

        resources.put(Resource.WOOD, 19); //Initializes resources
        resources.put(Resource.BRICK, 19);
        resources.put(Resource.WHEAT, 19);
        resources.put(Resource.SHEEP, 19);
        resources.put(Resource.ORE, 19);

        this.turnController = new TurnController(this);
    }

    public Tile[] getTiles() {
        return tiles;
    } //Getter

    public Node[] getNodes() {
        return nodes;
    } //Getter

    public Node getNodes(int node) { //Getter for testing
        return nodes[node];
    }

    public List<Road> getRoads() {
        return roads;
    } //Getter

    public void addRoad(Road road) {
        roads.add(road);
    } //Setter

    public void updateResources(Resource resource, int change) { //Updates resources
        int amount = resources.get(resource); //Current amount
        resources.put(resource, amount + change); //Puts amount + change in resources
    }

    public int getResources(Resource resource) {
        return resources.get(resource);
    } //Getter

    public boolean checkResources(Resource resource, int change) { //Checks if resources would be 0 or greater if changed
        if (resource == null) {
            return false;
        }
        int amount = resources.get(resource);
        return amount + change >= 0;
    }

    public void printResources() { //Prints resources
        System.out.println("\nBoard resources");
        for (Resource resource: Resource.values()) {
            System.out.println(resource + ": " + resources.get(resource));
        }
    }

    public boolean placeSettlement(Player player, Node node) { //Places settlement
        return turnController.handleBuildSettlement(player, node, turn); //Calls action controller method
    }

    public boolean placeSettlement(Player player, int node) { //Places settlement, int given for testing
        if (0 <= node && node < nodes.length) { //If node is in range
            return turnController.handleBuildSettlement(player, getNodes(node), turn);
        }
        return false;
    }

    public boolean upgradeCity(Player player, Node node) { //Upgrades settlement to city
        return turnController.handleUpgradeCity(player, node);
    }

    public boolean upgradeCity(Player player, int node) { //Upgrades to city, method for testing
        return turnController.handleUpgradeCity(player, getNodes(node));
    }

    public boolean placeRoad(Player player, Node start, Node end) { //Places road
        return turnController.handleBuildRoad(player, start, end, turn);
    }

    public boolean placeRoad(Player player, int start, int end) { //Places road, method for testing
        if (0 <= start && start <= nodes.length && 0 <= end && end <= nodes.length) { //If ints given are in range
            return turnController.handleBuildRoad(player, getNodes(start), getNodes(end), turn);
        }
        return false;

    }

    public void setTurn(int turn) { //Setter
        this.turn = turn;
    }

    public List<Boolean> checkActions(Player player, List<Node> settlementNodes, List<Node> roadNodes) { //Checks possible actions
        return new ArrayList<>(turnController.checkActions(player, settlementNodes, roadNodes)); //Returns list of actions
    }

    public void setLongestRoad(Player player) { //Sets longest road owner to player
        longestRoad = player;
    }

    public Player getLongestRoad() { //Gets the longest road player
        return longestRoad;
    }

    public void calcLongestRoad(List<Player> players) { //Calculates longest road owner
        Player owner = null;
        int longestRoad = 0;
        int length = 0;
        for (Player player: players) { //Checks each player's longest road
            length = turnController.calcLongestRoad(player);
            if (length > longestRoad) {
                owner = player; //If player has longest, sets owner to player
                longestRoad = length;
            }
            else if (length == longestRoad) {
                owner = null; //
            }
        }
        if (getLongestRoad() != null && owner == null) { //If player currently has longest road and no player has surpassed them
            owner = getLongestRoad(); //Sets owner to original owner
        }
        if (longestRoad < 5) { //If longest road is less than 5
            owner = null; //Owner set to null
        }
        setLongestRoad(owner); //Sets longest road owner
        setLongestRoadLength(longestRoad); //Sets length

    }

    public void setLongestRoadLength(int length) { //Setter
        this.longestRoadLength = length;
    }

    public void printLongestRoad() { //Prints longest road length for testing
        System.out.println("Longest Road: " + longestRoadLength);
    }
}