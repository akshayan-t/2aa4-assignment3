import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a player in the Catan game
 */
public class Player {
    private Map<Resource, Integer> resources;
    private List<Road> roads;
    private List<Building> buildings;
    
    public Player() {
        this.resources = new HashMap<>();
        this.roads = new ArrayList<>();
        this.buildings = new ArrayList<>();
        initializeResources();
    }
    
    private void initializeResources() {
        for (Resource resource : Resource.values()) {
            resources.put(resource, 0);
        }
    }
    
    public Map<Resource, Integer> getResources() {
        return resources;
    }
    
    public List<Road> getRoads() {
        return roads;
    }
    
    public List<Building> getBuildings() {
        return buildings;
    }
    
    public int calculatePoints() {
        int points = 0;
        for (Building building : buildings) {
            points += building.getPoints();
        }
        return points;
    }
    
    public boolean spendResource(Resource resource, int amount) {
        int currentAmount = resources.getOrDefault(resource, 0);
        if (currentAmount >= amount) {
            resources.put(resource, currentAmount - amount);
            return true;
        }
        return false;
    }
    
    public void addResource(Resource resource, int amount) {
        int currentAmount = resources.getOrDefault(resource, 0);
        resources.put(resource, currentAmount + amount);
    }
    
    @Override
    public String toString() {
        return "Player{points=" + calculatePoints() + ", resources=" + resources + "}";
    }
}
