package fr.clem.tennisscore.services;

import fr.clem.tennisscore.model.GameResultModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ComputeScoreComputingServiceTest {

    private ScoreComputingService computeScoreComputingService;

    @BeforeEach
    void setUp() {
        computeScoreComputingService = new ScoreComputingService();
    }

    @Test
    void testPlayerAWinsDirectly() {
        // score player A : 0 15 30 40 WIN ; player B:  0 0 0 0
        String userInput = "AAAA";

        // Compute score
        List<GameResultModel> results = computeScoreComputingService.compute(userInput);

        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();

        Assertions.assertThat(allScorePlayerA).containsExactly(0,1,2,3, 5);
        Assertions.assertThat(allScorePlayerB).containsExactly(0,0,0,0, 0);

    }

    @Test
    void testPlayerBWinsDirectly() {
        String userInput = "BBBB";

        List<GameResultModel> results = computeScoreComputingService.compute(userInput);
        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();

        Assertions.assertThat(allScorePlayerA).containsExactly(0, 0, 0, 0, 0);
        Assertions.assertThat(allScorePlayerB).containsExactly(0, 1, 2, 3, 5);
    }


    @Test
    void testAAABBBABBAAA() {
        String userInput= "AAABBBABBAAA";

        List<GameResultModel> results = computeScoreComputingService.compute(userInput);
        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();
        Assertions.assertThat(allScorePlayerA).containsExactly(0, 1, 2, 3, 3, 3, 3, 4, 3, 3, 3, 4 , 5);
        Assertions.assertThat(allScorePlayerB).containsExactly(0, 0, 0, 0, 1, 2, 3, 3, 3, 4, 3, 3, 3);
    }


    @Test
    void testAAAAAAAAAAAAAAAAAAAAAAAAAA() {
        //Test with to much char. The computeGameScoring must print only the victory
        String userInput= "AAAAAAAAAAAAAAAAAAAAAAAAAA";

        List<GameResultModel> results = computeScoreComputingService.compute(userInput);
        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();
        Assertions.assertThat(allScorePlayerA).containsExactly(0, 1, 2, 3, 5);
        Assertions.assertThat(allScorePlayerB).containsExactly(0, 0, 0, 0, 0);
    }


    @Test
    void testSinglePoint() {
        String userInput = "A";

        List<GameResultModel> results = computeScoreComputingService.compute(userInput);
        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();

        Assertions.assertThat(allScorePlayerA).containsExactly(0, 1);
        Assertions.assertThat(allScorePlayerB).containsExactly(0, 0);
    }

    @Test
    void testSinglePointB() {
        String userInput = "B";

        List<GameResultModel> results = computeScoreComputingService.compute(userInput);
        List<Integer> allScorePlayerA = results.stream().map(GameResultModel::getScorePlayerA).toList();
        List<Integer> allScorePlayerB = results.stream().map(GameResultModel::getScorePlayerB).toList();

        Assertions.assertThat(allScorePlayerA).containsExactly(0, 0);
        Assertions.assertThat(allScorePlayerB).containsExactly(0, 1);
    }



}
