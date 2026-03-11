import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonExporter {

    public static void export(Gameplay gameplay, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(toJson(gameplay));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file: " + filename, e);
        }
    }

    public static String toJson(Gameplay gameplay) {
        Board board = gameplay.getBoard();

        StringBuilder sb = new StringBuilder();
        sb.append("{ ");

        sb.append("\"roads\": ");
        sb.append(roadsToJson(board.getRoads()));
        sb.append(", ");

        sb.append("\"buildings\": ");
        sb.append(buildingsToJson(board.getNodes()));

        sb.append(" }");
        return sb.toString();
    }

    private static String roadsToJson(List<Road> roads) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);

            sb.append("{ ");
            sb.append("\"a\": ").append(road.getStart().getNodeId()).append(", ");
            sb.append("\"b\": ").append(road.getEnd().getNodeId()).append(", ");
            sb.append("\"owner\": \"").append(playerColourToJson(road.getOwner())).append("\"");
            sb.append(" }");

            if (i < roads.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String buildingsToJson(Node[] nodes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        boolean first = true;

        for (Node node : nodes) {
            if (node.getBuilding() != null && node.getOwner() != null) {
                if (!first) {
                    sb.append(", ");
                }

                sb.append("{ ");
                sb.append("\"node\": ").append(node.getNodeId()).append(", ");
                sb.append("\"owner\": \"").append(playerColourToJson(node.getOwner())).append("\", ");
                sb.append("\"type\": \"").append(buildingTypeToJson(node.getBuilding())).append("\"");
                sb.append(" }");

                first = false;
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private static String playerColourToJson(Player player) {
        if (player == null || player.getColour() == null) {
            return "UNKNOWN";
        }
        return player.getColour().name();
    }

    private static String buildingTypeToJson(Building building) {
        if (building instanceof Settlement) {
            return "SETTLEMENT";
        }
        if (building instanceof City) {
            return "CITY";
        }
        return building.getClass().getSimpleName().toUpperCase();
    }
}
