import java.util.HashMap;

public class Settlement extends Building {

    public Settlement(Player owner, Node node) { //Settlement method
        this.owner = owner;
        this.location = node;

        resources.put(Resource.BRICK, 1);
        resources.put(Resource.WOOD, 1);
        resources.put(Resource.SHEEP, 1);
        resources.put(Resource.WHEAT, 1);
    }

    @Override
    public HashMap<Resource, Integer> getRequiredResources() {
        return resources;
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
