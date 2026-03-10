import java.util.List;
import java.util.ArrayList;

/**
 * Represents a node (intersection) on the Catan board where buildings can be placed
 */
public class Node {
    private Building building;
    private List<Tile> adjacentTiles;
    
    public Node() {
        this.adjacentTiles = new ArrayList<>();
    }
    
    public Building getBuilding() {
        return building;
    }
    
    public void setBuilding(Building building) {
        this.building = building;
    }
    
    public List<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }
    
    public void addAdjacentTile(Tile tile) {
        this.adjacentTiles.add(tile);
    }
    
    @Override
    public String toString() {
        return "Node{building=" + building + ", adjacentTiles=" + adjacentTiles.size() + "}";
    }
}
