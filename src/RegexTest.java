import static org.junit.Assert.*;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    HumanCommandParser parser = new HumanCommandParser();

    @Test
    public void testRoll() {
        String[] inputs = {"Roll"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new RollDiceCommand();
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testGo() {
        String[] inputs = {"Go"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new GoCommand();
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testList() {
        String[] inputs = {"List"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new ListStatusCommand();
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testBuildSettlement() {
        String[] inputs = {"Build settlement 5"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new BuildSettlementCommand(24);
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testBuildCity() {
        String[] inputs = {"Build city 5"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new BuildCityCommand(24);
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testBuildRoad() {
        String[] inputs = {"Build road 0, 1"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        PlayerCommand command = new BuildRoadCommand(0, 1);
        assertEquals(parser.parse(allInputs).getClass(), command.getClass());
    }

    @Test
    public void testFails() {
        String[] inputs = {"Aksjdfn", "ROLL", "gO", "Lisp", "Build settlement 24 5", "Build city 0, 4", "Build road 0 1", "Build road 0 1"};
        for (String input: inputs) {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            assertEquals(parser.parse(input), null);
        }
    }

    @Test
    public void testEverything() {
        String[] inputs = {"Roll", "Go", "List", "Build settlement 5", "Build road 1, 2", "Build city 1"};
        String allInputs = String.join("\n", inputs);
        System.setIn(new ByteArrayInputStream(allInputs.getBytes()));
        String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
        Pattern pattern = Pattern.compile(regex);

        for (String input : inputs) {
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String aaa[] = matcher.group("command").split(" ");
                System.out.println("Command: " + aaa[0]);
                System.out.println("Building: " + matcher.group("building"));
                System.out.println("NodeId: " + matcher.group("nodeId"));
                System.out.println("FromNodeId: " + matcher.group("fromNodeId"));
                System.out.println("---");
            }
        }
    }
}
