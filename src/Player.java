import java.util.*;

public class Player {
    private int playerNumber; //Player number

    private HashMap<Resource, Integer> resources = new HashMap<>(); //Hashmap to store resource values

    private List<Node> settlements = new ArrayList<>(); //Lists for settlements, cities, roads, buildings
    private List<Node> cities = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private PlayerColour colour;

    public Player(int playerNumber, Board board) { //Constructor
        this.playerNumber = playerNumber;
        resources.put(Resource.WOOD, 0);
        resources.put(Resource.BRICK, 0);
        resources.put(Resource.WHEAT, 0);
        resources.put(Resource.SHEEP, 0);
        resources.put(Resource.ORE, 0);
    }

    public Player (int playerNumber, Board board, PlayerColour colour) {
        this(playerNumber, board);
        this.colour = colour;
    }

    public int getResources(Resource resource) {
        return resources.get(resource);
    } //Gets resources

    public void updateResources(Resource resource, int change) { //Updates resources
        int amount = resources.get(resource);
        resources.put(resource, amount + change);
    }

    public void updateResources(Resource resource, int change, Board board) { //Updates resources
        int amount = resources.get(resource);
        resources.put(resource, amount + change);
        board.updateResources(resource, -change);
    }

    public List<Building> getBuildings() {
        return buildings;
    } //Gets buildings

    public List<Node> getSettlements() { //Gets settlements
        return settlements;
    } //Gets settlements

    public List<Node> getCities() { //Gets settlements
        return cities;
    } //Gets cities

    public int getSettlementCount() { //Gets settlement count
        int counter = 0;
        for (Building building: buildings) { //Checks each building
            if (building instanceof Settlement) {
                counter++;
            }
        }
        return counter;
    }

    public int getCityCount() { //Gets city count
        int counter = 0;
        for (Building building: buildings) {
            if (building instanceof City) {
                counter++;
            }
        }
        return counter;
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
    } //Adds building

    public void addSettlement(Node node) {
        this.settlements.add(node);
    } //Adds settlement

    public void addCity(Node node) {
        this.cities.add(node);
    } //Adds city

    public void addRoad(Road road) {
        this.roads.add(road);
    } //Adds road

    public void removeSettlement(Node node) { //Removes settlement
        if (getCities().contains(node)) { //Checks if cities has city before removing
            this.settlements.remove(node);
        }
    }

    public int calcPoints(List<Player> players, Board board) { //Calculates points
        int points = 0;
        for (Building building: buildings) { //Checks each building
            points += building.getPoints(); //Adds to points
        }
        board.calcLongestRoad(players); //Calculates longest road
        if (board.getLongestRoad() == this) { //If player has longest road, +2 points
            points += 2;
        }
        return points; //Returns points
    }

    public int getTotalResources() { //Returns total resources
        int total = resources.get(Resource.BRICK) + resources.get(Resource.WOOD) + resources.get(Resource.SHEEP) + resources.get(Resource.WHEAT) + resources.get(Resource.ORE);
        return total;
    }

    public List<Road> getRoads() {
        return roads;
    } //Returns roads

    public int getPlayerNumber() {
        return playerNumber;
    } //Returns player numbers

    public void printBuildings() { //Prints buildings
        System.out.println("\nPlayer " + playerNumber + " buildings");
        System.out.println("Settlements: " + getSettlements());
        System.out.println("Cities: " + getCities());
        System.out.print("Roads: ");
        for (Road road: getRoads()) {
            road.printRoad();
        }
        System.out.println();
    }

    public void printResources() { //Prints resources
        System.out.println("\nPlayer " + playerNumber + " resources");
        for (Resource resource: Resource.values()) {
            System.out.println(resource + ": " + resources.get(resource));
        }
    }

    public void printCards(Board board) {
        System.out.print("Resource Cards: ");
        for (Resource resource: Resource.values()) {
            int value = this.getResources(resource);
            System.out.print(value + "x " + resource + ", ");
        }
        if (board.getLongestRoad() == this) {
            System.out.print("1x Longest Road");
        }
        System.out.println();
    }

    public HashMap<Resource, Integer> getResources() {
        return resources;

    public PlayerColour getColour() {
        return colour;
    }
    }
}


