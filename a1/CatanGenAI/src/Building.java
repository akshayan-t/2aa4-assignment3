/**
 * Abstract base class for buildings (Settlement and City)
 */
public abstract class Building {
    private Player owner;
    private Node location;
    
    public Building(Player owner, Node location) {
        this.owner = owner;
        this.location = location;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    public Node getLocation() {
        return location;
    }
    
    public void setLocation(Node location) {
        this.location = location;
    }
    
    public abstract int getPoints();
}
