import java.util.HashMap;

public class Buildable {
    protected Player owner;
    protected HashMap<Resource, Integer> cost = new HashMap<>();

    public Player getOwner() { //Gets owner
        return owner;
    }
    public HashMap<Resource, Integer> getCost() {
        return cost;
    }
}
