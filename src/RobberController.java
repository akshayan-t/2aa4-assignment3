import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RobberController {
    Random rand = new Random();

    public void activateRobber(Player turnPlayer, List<Player> players, Board board) {
        discardResources(turnPlayer, players, board);
        Player victim = pickVictim(turnPlayer, board);
        if (victim != null) {
            robPlayer(turnPlayer, victim);
        }
    }

    private void discardResources(Player turnPlayer, List<Player> players, Board board) {
        Resource resource = null;
        for (Player p : players) {
            if (p != turnPlayer) {
                if (p.getTotalResources() > 7) {
                    int newTotal = p.getTotalResources()/2;
                    while (p.getTotalResources() > newTotal) {
                        resource = chooseResource(p);
                        p.updateResources(resource, -1);
                        board.updateResources(resource, 1);
                    }
                }
            }
        }
    }

    private Player pickVictim(Player turnPlayer, Board board) {
        Tile tile = pickRandomTile(board);
        List<Player> owners = new ArrayList<>(tile.getOwners());
        owners.remove(turnPlayer);
        owners.remove(null);
        Iterator<Player> iterator = owners.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getTotalResources() <= 0) {
                iterator.remove();
            }
        }
        if (owners.size() > 0) {
            Player robbedPlayer = owners.get(rand.nextInt(owners.size()));
            if (robbedPlayer != null) {
                return robbedPlayer;
            }
        }
        return null;
    }

    private void robPlayer(Player turnPlayer, Player robbedPlayer) {
        Resource resource = null;
        int currentResources = robbedPlayer.getTotalResources();
        while (robbedPlayer.getTotalResources() == currentResources) {
            resource = chooseResource(robbedPlayer);
            System.out.print(" Player " + robbedPlayer.getPlayerNumber() + " has been robbed! (-1 " + resource + ")");
            robbedPlayer.updateResources(resource, -1);
            turnPlayer.updateResources(resource, 1);
        }
    }

    private Resource chooseResource(Player player) {
        Resource[] resources = Resource.values();
        Resource resource = null;
        while (true) {
            resource = resources[rand.nextInt(resources.length)];
            if (player.getResources(resource) > 0) {
                break;
            }
        }
        return resource;
    }

    private Tile pickRandomTile(Board board) {
        Tile[] tiles = board.getTiles();
        Tile tile = tiles[rand.nextInt(tiles.length)];
        return tile;
    }
}
