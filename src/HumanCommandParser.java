import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanCommandParser {
    private String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
    private Pattern pattern = Pattern.compile(regex);

    public PlayerCommand parse(String command) {
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) {
            command = command.replace(",", "");
            String line[] = command.split(" ");
            if (line[0].equals("Roll")) {
                return new RollDiceCommand();
            } else if (line[0].equals("Go")) {
                return new GoCommand();
            } else if (line[0].equals("List")) {
                return new ListStatusCommand();
            } else if (line[0].equals("Build")) {
                int nodeId = Integer.valueOf(line[2].replace(",", ""));

                if (line[1].equals("settlement")) {
                    return new BuildSettlementCommand(nodeId);
                } else if (line[1].equals("city")) {
                    return new BuildCityCommand(nodeId);
                } else if (line[1].equals("road")) {
                    int toNodeId = Integer.valueOf(line[3]);
                    return new BuildRoadCommand(nodeId, toNodeId);
                }
            }
            System.out.println();
        }
        return null;
    }
}
