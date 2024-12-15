package fr.clem.tennisscore.services;

import fr.clem.tennisscore.model.GameResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;


@Service
public class ResultPrinterService {

    public static final String PRINT_PLAY = "Player A : %s / Player B : %s";
    public static final String PRINT_PLAY_WIN_THE_GAME = "Player %s wins the game";

    private final PrintStream output;

    @Autowired
    public ResultPrinterService(PrintStream output) {
        this.output = Optional.ofNullable(output).orElse(System.out);
    }

    public void print(List<GameResultModel> results) {

        for (GameResultModel result : results) {
            if (result.getScorePlayerA() >= 5 || result.getScorePlayerB() >= 5) {
                String winner = result.getScorePlayerA() > result.getScorePlayerB() ? "A" : "B";
                output.println(PRINT_PLAY_WIN_THE_GAME.formatted(winner));
            } else {
                String playerAScoreTenisFormat = intToTenisFormat(result.getScorePlayerA());
                String playerBScoreTenisFormat = intToTenisFormat(result.getScorePlayerB());
                output.println(PRINT_PLAY.formatted(playerAScoreTenisFormat, playerBScoreTenisFormat));
            }
        }
    }

    public void printNLastElement(List<GameResultModel> results, int n) {

        List<GameResultModel> subList = results.subList(results.size() - n, results.size());
        print(subList);
    }

    private String intToTenisFormat(Integer intScore) {
        return switch (intScore) {
            case 0 -> "0";
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            case 4 -> "advantage";
            default -> "WIN";
        };
    }

}