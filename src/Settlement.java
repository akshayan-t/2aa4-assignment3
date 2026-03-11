import java.util.HashMap;

public class Settlement extends Building {

    public Settlement(Player owner, Node node) { //Settlement method
        this.owner = owner;
        this.location = node;

        cost.put(Resource.BRICK, 1);
        cost.put(Resource.WOOD, 1);
        cost.put(Resource.SHEEP, 1);
        cost.put(Resource.WHEAT, 1);
    }

    @Override
    public HashMap<Resource, Integer> getCost() {
        return cost;
    }

    @Override
    public int getPoints() { //Returns 1 point
        return 1;
    }

    @Override
    public int getResourceMult() {
        return 1;
    } //Returns 1 resource
}
