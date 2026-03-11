import java.util.ArrayList;
import java.util.List;

public class Node { //Node class
    private int number; //Node number
    private Building building; //Building
    private Player owner; //Owner
    private List<Road> roads = new ArrayList<>(); //Road list
    private List<Integer> adjacentNodes = new ArrayList<>(); //Adjacent node list
//    private List<Tile> tiles = new ArrayList<>(); //Tile list

    public Node(List<Integer> adjacentNodes) {
        this.adjacentNodes = new ArrayList<>(adjacentNodes);
    } //Constructor

//    public void addTile(Tile tile) {
//        tiles.add(tile);
//    } //Adds tile to connected tile list
//    public int getTileSize() {
//        return tiles.size();
//    } //Gets number of tiles node is connected to
    public void setNumber(int number) {
        this.number = number;
    } //Sets node number

    public int getNumber() {
        return number;
    } //Gets number

    public void setBuilding(Building building) { //Sets building
        this.building = building;
    }

    public void setOwner(Player owner) { //Sets owner
        this.owner = owner;
    } //Setter

    public Building getBuilding() { //Gets building
        return building;
    } //Getter

    public Player getOwner() { //Gets owner
        return owner;
    } //Getter

    public void addRoad(Road road) { //Adds road to list
        this.roads.add(road);
    } //Adds road to list

    public boolean checkRoadOwner(Player player) { //Checks if player owns a road connected to this node
        for (Road road : this.roads) {
            if (road.getOwner() == player) {
                return true;
            }
        }
        return false;
    }

    public List<Road> getRoads() { //Gets list of roads
        return roads;
    } //Getter

    public List<Integer> getAdjacentNodes() { //Gets adjacent nodes
        return adjacentNodes;
    } //Getter

    public String toString() { //Returns string of node number
        return String.valueOf(number);
    }
    
    public int getNodeId() {
        return number;
}
}


