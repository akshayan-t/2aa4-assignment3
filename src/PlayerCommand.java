public interface PlayerCommand { //Interface for Player commands
    public CommandResult execute(Gameplay game, TurnController turnController); //Execute method
    public void undo(Gameplay game, TurnController turnController);
    public int getCost();
}
