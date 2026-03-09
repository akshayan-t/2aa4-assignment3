import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile {
    private int diceNumber; //Dice number
    private Resource resource; //Tile resource
    private List<Node> nodes = new ArrayList<>(); //Stores connected nodes
    private List<Integer> nodeLocations = new ArrayList<>(); //Stores node locations to access from board

    public Tile(List<Integer> nodeLocations) {
        this.nodeLocations = new ArrayList<>(nodeLocations);
    } //Constructor

    public void setNodes(Board board) { //Sets node tiles
        for (int position: nodeLocations) {
            nodes.add(board.getNodes(position));
//            board.getNodes(position).addTile(this);
        }
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    } //Sets resources

    public Resource getResource() {
        return resource;
    } //Gets resources

    public void setNumber(int number) {
        this.diceNumber = number;
    } //Sets tile number

    public int getNumber() {
        return diceNumber;
    } //Gets tile number

    public List<Node> getNodes() {
        return nodes;
    } //Gets connected nodes

    public void makeResources(Board board) { //Makes resources for each connected node
        int total = 0;
        Player owner = getOwner(); //Gets tile owner

        for (Node node : nodes) { //For each node
            if (node.getBuilding() != null) { //Increases total by resource mult
                total += node.getBuilding().getResourceMult();
            }
        }

        if (board.checkResources(resource, -total)) { //Checks if resources would be positive after change
            for (Node node : nodes) { //Updates resources for each node
                if (node.getBuilding() != null) {
                    int change = node.getBuilding().getResourceMult();
                    Player player = node.getBuilding().getOwner();
                    player.updateResources(resource, change); //Updates player and board resources
                    board.updateResources(resource, -change);
                }
            }
        }
        else if (owner != null) { //If only one player gets resources from tile and resources would go negative
            int resources = board.getResources(resource); //Change is max amount of resources
            owner.updateResources(resource, resources);
            board.updateResources(resource, -resources);
        }
    }

    public int getResourcesProduced() { //Gets how many resources would be produced by the tile
        int total = 0;
        for (Node node : nodes) {
            if (node.getBuilding() != null) {
                total += node.getBuilding().getResourceMult();
            }
        }
        return total;
    }

    public Player getOwner () { //Checks if only one player owns buildings at a tile
        Set<Player> owners = getOwners();
        if (owners.size() == 1) {
            return owners.iterator().next();
        }
        return null;
    }

    public Set<Player> getOwners () { //Checks if only one player owns buildings at a tile
        Set<Player> owners = new HashSet<>();
        for (Node node: nodes) { //Checks if every node has same owner or null owner
            owners.add(node.getOwner());
        }
        return owners;
    }
}
