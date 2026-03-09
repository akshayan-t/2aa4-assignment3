import java.util.HashMap;

public class City extends Building { //City class
    public City(Player owner, Node node) {
        this.owner = owner;
        this.location = node;

        resources.put(Resource.ORE, 3);
        resources.put(Resource.WHEAT, 2);
    }

    @Override
    public HashMap<Resource, Integer> getRequiredResources() {
        return resources;
    }

    @Override
    public int getPoints() {
        return 2;
    } //Returns 2 points

    @Override
    public int getResourceMult() {
        return 2;
    } //Returns 2 resources
}
