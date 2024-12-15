package fr.clem.tennisscore;

import fr.clem.tennisscore.model.GameResultModel;
import fr.clem.tennisscore.services.ResultPrinterService;
import fr.clem.tennisscore.services.ScoreComputingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class GameInputHandlerService implements CommandLineRunner {

    public static final String WELCOME_MESSAGE = """
            Welcome to the Tennis Set Score Calculator!
            To input points, type 'A' for Player A or 'B' for Player B.
            You can enter a sequence of points (e.g., 'AABBAA') or type 'Q' to quit.
            """;
    public static final String INVALIDE_INPUT_MESSAGE = "❌ Invalid input. Please enter only 'A', 'B', or 'Q' to quit.";
    public static final String PROMPT_MESSAGE = "\nEnter the point sequence for the set (or 'Q' to quit): ";
    public static final String SET_IS_OVER_MESSAGE = "\n🎉 The set is over! ";

    private final ResultPrinterService resultPrinterService;
    private final Scanner scanner;
    private final ScoreComputingService scoreComputingService;

    @Autowired
    public GameInputHandlerService(ScoreComputingService scoreComputingService, ResultPrinterService resultPrinterService, Scanner scanner) {
        this.scoreComputingService = scoreComputingService;
        this.resultPrinterService = resultPrinterService;
        this.scanner = scanner;
    }

    @Override
    public void run(String... args) {
        play();
    }

    public void play() {

        boolean setWon = false;
        String setScore = "";
        System.out.println(WELCOME_MESSAGE);

        while (!setWon) {
            System.out.print(PROMPT_MESSAGE);

            String userInput = scanner.nextLine();
            if ("Q".equals(userInput)) {
                setWon = true;
            } else {
                // Validate input (only A and B allowed)
                if (isValidInput(userInput)) {
                    setScore = setScore + userInput;
                    List<GameResultModel> result = scoreComputingService.compute(setScore);

                    resultPrinterService.printNLastElement(result, userInput.length());

                    GameResultModel lastResult = result.getLast();
                    setWon = scoreComputingService.hasWinner(lastResult);

                    if (setWon) {
                        System.out.println(SET_IS_OVER_MESSAGE);
                    }

                } else {
                    System.out.println(INVALIDE_INPUT_MESSAGE);
                }
            }
        }
    }

    private boolean isValidInput(String input) {
        return input != null && input.matches("[ABQ]*");
    }
}