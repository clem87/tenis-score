package fr.clem.tennisscore.services;

import fr.clem.tennisscore.model.GameResultModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreComputingService {

    public List<GameResultModel> compute(String userInput) {
        List<GameResultModel> results = new ArrayList<>();

        // Initialize first result
        results.add(GameResultModel.builder()
                .scorePlayerA(0)
                .scorePlayerB(0)
                .build());

        // Process each input character
        for (char player : userInput.toCharArray()) {
            GameResultModel lastResult = results.getLast();
            GameResultModel newResult = incrementScore(player, lastResult);
            results.add(newResult);

            // Stop if one player has won
            if (hasWinner(newResult)) {
                break;
            }
        }
        return results;
    }

    private GameResultModel incrementScore(char winnerChar, GameResultModel previousGameResult) {
        // Clone the previous result to create a new state
        GameResultModel gameResult = previousGameResult.clone();

        // compute winner and looser score from previous Game result
        int winnerScore = (winnerChar == 'A') ? gameResult.getScorePlayerA() + 1 : gameResult.getScorePlayerB() + 1;
        int loserScore = (winnerChar == 'A') ? gameResult.getScorePlayerB() : gameResult.getScorePlayerA();

        // Manage special case with advantage. If other player have the advantage both lose the advantage and come back to 40
        if (winnerScore == 4 && loserScore == 4) {
            winnerScore = 3;
            loserScore = 3;
        }
        // Manage special case with advantage. The winner take adventage but other doesn't ha 40. Winner win the set
        else if (winnerScore == 4 && loserScore < 3) {
            winnerScore = 5;
        }

        // Apply the updated scores directly back to newResult
        if (winnerChar == 'A') {
            gameResult.setScorePlayerA(winnerScore);
            gameResult.setScorePlayerB(loserScore);
        } else {
            gameResult.setScorePlayerB(winnerScore);
            gameResult.setScorePlayerA(loserScore);
        }
        return gameResult;
    }

    public boolean hasWinner(GameResultModel result) {
        return result.getScorePlayerA() >= 5 || result.getScorePlayerB() >= 5;
    }
}
