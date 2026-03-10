import java.util.List;
import java.util.ArrayList;

/**
 * Represents the Catan game board
 */ 
public class Board {
    private List<Tile> tiles;
    private List<Node> nodes;
    private List<Road> roads;
    
    public Board() {
        this.tiles = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.roads = new ArrayList<>();
    }
    
    public List<Tile> getTiles() {
        return tiles;
    }
    
    public List<Node> getNodes() {
        return nodes;
    }
    
    public List<Road> getRoads() {
        return roads;
    }
    
    public boolean placeRoad(Player player, Node n1, Node n2) {
        // Check if road placement is valid
        Road road = new Road(n1, n2, player);
        roads.add(road);
        player.getRoads().add(road);
        return true;
    }
    
    public boolean placeSettlement(Node node, Player player) {
        // Check if settlement placement is valid
        if (node.getBuilding() == null) {
            Settlement settlement = new Settlement(player, node);
            node.setBuilding(settlement);
            player.getBuildings().add(settlement);
            return true;
        }
        return false;
    }
    
    public boolean upgradeToCity(Node node, Player player) {
        // Check if upgrade is valid
        Building currentBuilding = node.getBuilding();
        if (currentBuilding instanceof Settlement && currentBuilding.getOwner() == player) {
            player.getBuildings().remove(currentBuilding);
            City city = new City(player, node);
            node.setBuilding(city);
            player.getBuildings().add(city);
            return true;
        }
        return false;
    }
    
    public void addTile(Tile tile) {
        tiles.add(tile);
    }
    
    public void addNode(Node node) {
        nodes.add(node);
    }
    
    @Override
    public String toString() {
        return "Board{tiles=" + tiles.size() + ", nodes=" + nodes.size() + ", roads=" + roads.size() + "}";
    }
}
