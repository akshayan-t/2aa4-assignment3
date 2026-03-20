import java.util.List;

public class BenefitScoringStrategy implements MoveScoringStrategy {
    @Override
    public double calculateValue(PlayerCommand command, Gameplay game, TurnController turnController) {
        double score = 0.0;
        Player agent = game.getCurrentPlayer();

        // R3.2: Earning a VP = 1.0
        if (leadsToVictoryPoint(command, game, turnController)) {
            score = 1.0;
        }
        // R3.2: Building without VP = 0.8
        else if (isBuildingAction(command)) {
            score = 0.8;
        }

        // R3.2: Less than 5 cards remaining = 0.5
        if (resultsInSmallHand(command, agent)) {
            score = 0.5;
        }

        return score;
    }

    private boolean leadsToVictoryPoint(PlayerCommand command, Gameplay game, TurnController turnController) {
        if (command instanceof BuildSettlementCommand || command instanceof BuildCityCommand) {
            return true;
        }
        else if (command instanceof BuildRoadCommand) {
            if (((BuildRoadCommand) command).leadsToVictoryPoint(game, turnController)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBuildingAction(PlayerCommand cmd) {
        return cmd instanceof BuildRoadCommand;
    }

    private boolean resultsInSmallHand(PlayerCommand cmd, Player p) {
        int cost = cmd.getCost(); // Assuming commands can report their resource cost
        return (p.getTotalResources() - cost) < 5;
    }
}
