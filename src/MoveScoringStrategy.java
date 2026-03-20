public interface MoveScoringStrategy {
    double calculateValue(PlayerCommand command, Gameplay game, TurnController turnController);
}
