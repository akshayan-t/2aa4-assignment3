import java.security.SecureRandom;
import java.util.*;

public class TurnController { //Class for performing actions during players turns
    private Board board; //Private board
    private PlacementRules rules = new PlacementRules(); //Private rules
    private SecureRandom rand = new SecureRandom();

    public TurnController(Board board) {
        this.board = board;
    } //Constructor

    public void exchangeResources(Player player, Build build) {
        for (Resource resource : build.getRequiredResources().keySet()) {
            player.updateResources(resource, -1, board);
//            board.updateResources(resource, 1);
        }
    }

    public boolean handleBuildSettlement(Player player, Node node, int turn) { //Building settlements
        if (turn <= 2) { //If starting phase
            if (!rules.canPlaceSettlement(player, node, board)) { //If player cannot place Settlement
                return false;
            }
        }
        else if (!rules.canBuildSettlement(player, node, board)) { //If turn > 2, if player cannot build Settlement
            return false;
        }
        Settlement settlement = new Settlement(player, node); //Create new settlement
        player.addSettlement(node); //Add settlement location to player
        player.addBuilding(settlement); //Add Settlement to player's buildings
        node.setBuilding(settlement); //Set node's building to settlement
        node.setOwner(player); //Sets nodes owner to player

        if (turn > 2) { //If turn > 2 update resources
            exchangeResources(player, settlement);
        }
        return true;
    }

    public boolean handleUpgradeCity(Player player, Node node) { //Upgrades city
        if (rules.canUpgradeCity(player, node)) { //If player can upgrade city
            player.getBuildings().remove(node.getBuilding()); //Remove settlement
            City city = new City(player, node); //Create new city
            node.setBuilding(city); //Update values
            player.addBuilding(city);
            player.addCity(node);
            player.removeSettlement(node); //Remove settlement location

            exchangeResources(player, city);
            return true;
        }
        return false;
    }

    public boolean handleBuildRoad(Player player, Node start, Node end, int turn) { //Builds road
        if (turn <= 2) { //If start of game
            if (!rules.canPlaceRoad(player, start, end, board)) { //If player can't place road
                return false;
            }
        }
        else if (!rules.canBuildRoad(player, start, end, board)) { //If player can't build road
            return false;
        }
        Road road = new Road(start, end, player); //Create new road
        start.addRoad(road); //Add road to nodes
        end.addRoad(road);
        player.addRoad(road); //Add road to player and board
        board.addRoad(road);

        if (turn > 2) { //If turn > 2, update resources
            exchangeResources(player, road);
        }
        return true;
    }

    public List<Boolean> checkActions(Player player, List<Node> settlementNodes, List<Node> roadNodes) { //Check possible player actions
        boolean settlement = false; //Set booleans to represent actions
        boolean city = false;
        boolean road = false;
        while (settlementNodes.size() > 0) { //Checks if player can upgrade city at any node
            int random = rand.nextInt(settlementNodes.size());
            Node node = settlementNodes.get(random); //Choose random node from settlements
            settlementNodes.remove(random); //Removes node once checked
            if (rules.canUpgradeCity(player, node)) { //If true, city = true and break
                city = true;
                break;
            }
        }
        while (roadNodes.size() > 0) { //Checks every road node
            int random = rand.nextInt(roadNodes.size());
            Node node = roadNodes.get(random); //Chooses random node and removes it once checked
            roadNodes.remove(random);
            if (rules.canBuildSettlement(player, node, board) && settlement == false) { //If player can build settlement
                settlement = true;
            }
            if (road == false) {
                for (int end : node.getAdjacentNodes()) { //If road not true yet, for every road
                    if (rules.canBuildRoad(player, node, board.getNodes(end), board)) { //Checks if player can build road at connected node
                        road = true;
                    }
                }
            }
            if (settlement == true && road == true) { //If both settlement and road possible, breaks
                break;
            }
        }
        return Arrays.asList(settlement, city, road); //Returns list of booleans
    }

    private int getLongestRoad(Player player, int max, Road road, Set<Road> checkedRoads, Node startNode) { //Recursive function to calculate longest road
        max += 1; //Max increments each time function is called
        int newMax = max; //Creates new max variable
        checkedRoads.add(road); //Adds current road to checked road
        int length = 0;
        Node nextNode = road.getEnd();
        if (startNode == road.getStart()) { //Ensures next node is new node by flipping direction of road
            nextNode = road.getEnd();
        }
        else if (startNode == road.getEnd()) { //Ensures next node is new node by flipping direction of road
            nextNode = road.getStart();
        }
        Player settlement = nextNode.getOwner(); //Checks settlement owner
        if (settlement == null || settlement == player) { //If other player settlement not blocking
            for (Road nextRoad : nextNode.getRoads()) { //Checks each next road
                if (nextRoad != road && !checkedRoads.contains(nextRoad) && nextRoad.getOwner() == player) { //If next road connects to player road
                    length = getLongestRoad(player, max, nextRoad, checkedRoads, nextNode); //Call recursive function passing nextNode as node, longest length will be returned
                    if (length > newMax) { //If length is greater then new max, set new max to length
                        newMax = length;
                    }
                }
            }
        }
        return newMax; //Return new max
    }

    public int calcLongestRoad(Player player) { //Calculates players longest road length
        int max = 0;
        for  (Road road: player.getRoads()) { //For each of player's roads
            Set<Road> checkedRoads = new HashSet<>(); //Create set of checked roads
            int length1 = getLongestRoad(player, 0, road, checkedRoads, road.getStart()); //Begins at start of each road
            if (length1 > max) { //If length > max, set max to length
                max = length1;
            }
            int length2 = getLongestRoad(player, 0, road, checkedRoads, road.getEnd()); //Begins at end of each road
            if (length2 > max) { //If length > max, set max to length
                max = length2;
            }
        }
        return max; //return max length
    }

}
