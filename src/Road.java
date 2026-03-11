import java.util.ArrayList;
import java.util.HashMap;

public class Road extends Buildable { //Road class
    private Node start; //Start and end nodes
    private Node end;
    private Player owner; //Road owner

    public Road(Node start, Node end, Player owner) { //Constructor
        this.start = start;
        this.end = end;
        this.owner = owner;

        cost.put(Resource.BRICK, 1);
        cost.put(Resource.WOOD, 1);
    }

    @Override
    public HashMap<Resource, Integer> getCost() {
        return cost;
    }

    public Node getStart() { //Gets start
        return start;
    } //Gets start
    public Node getEnd() {
        return end;
    } //Gets end
    public Player getOwner() {
        return owner;
    } //Gets owner
    public void printRoad() { //Print method
        System.out.print("(" + getStart() + "," +  getEnd() + ") ");
    }
}