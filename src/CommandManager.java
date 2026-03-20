import java.util.Stack;

public class CommandManager {
    private Stack<PlayerCommand> undoStack = new Stack<>();
    private Stack<PlayerCommand> redoStack = new Stack<>();

    public CommandResult executeCommand(PlayerCommand command, Gameplay game, TurnController turnController) {
        CommandResult result = command.execute(game, turnController);
        if (command instanceof ListStatusCommand || command instanceof GoCommand || result == null) {
            if (command instanceof GoCommand) {
                undoStack.clear();
                redoStack.clear();
            }
            return result;
        }
        undoStack.push(command);
        redoStack.clear(); // Clear redo history
        return result;
    }

    public boolean undo(Gameplay game, TurnController turnController) {
        if (!undoStack.isEmpty()) {
            PlayerCommand command = undoStack.pop();
            command.undo(game, turnController);
            redoStack.push(command);
            return true;
        }
        return false;
    }

    public boolean redo(Gameplay game, TurnController turnController) {
        if (!redoStack.isEmpty()) {
            PlayerCommand command = redoStack.pop();
            command.execute(game, turnController);
            undoStack.push(command);
            System.out.println();
            return true;
        }
        return false;
    }
}