/**
 * Represents a road on the Catan board
 */
public class Road {
    private Node start;
    private Node end;
    private Player owner;
    
    public Road(Node start, Node end, Player owner) {
        this.start = start;
        this.end = end;
        this.owner = owner;
    }
    
    public Node getStart() {
        return start;
    }
    
    public void setStart(Node start) {
        this.start = start;
    }
    
    public Node getEnd() {
        return end;
    }
    
    public void setEnd(Node end) {
        this.end = end;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "Road{owner=" + owner + "}";
    }
}
