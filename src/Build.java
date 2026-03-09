import java.util.HashMap;

public class Build {
    protected Player owner;
    protected HashMap<Resource, Integer> resources = new HashMap<>();

    public Player getOwner() { //Gets owner
        return owner;
    }
    public HashMap<Resource, Integer> getRequiredResources() {
        return resources;
    }
}
