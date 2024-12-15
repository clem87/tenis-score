package fr.clem.tennisscore.services;

import fr.clem.tennisscore.GameInputHandlerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class GameInputHandlerServiceTest {

    private ScoreComputingService scoreComputingService;
    private ResultPrinterService resultPrinterService;
    private Scanner scanner;
    private GameInputHandlerService gameInputHandlerService;

    @BeforeEach
    void setUp() {
        scoreComputingService = Mockito.spy(ScoreComputingService.class);
        resultPrinterService = mock(ResultPrinterService.class);
        scanner = mock(Scanner.class);
        gameInputHandlerService = new GameInputHandlerService(scoreComputingService, resultPrinterService, scanner);
    }

    @Test
    void testSpyGameScoreServiceComputeCalledTwice() {
        // GIVEN
        when(scanner.nextLine())
                .thenReturn("AAB")   // First valid input
                .thenReturn("BAA"); // Second valid input

        // WHEN
        gameInputHandlerService.play();

        // THEN
        // Verify that `compute` was called twice with the correct arguments
        verify(scoreComputingService, times(1)).compute("AAB");
        verify(scoreComputingService, times(1)).compute("AABBAA");

        // Verify the total number of calls to the scanner
        verify(scanner, times(2)).nextLine();
    }


    @Test
    void testSpyGameScoreServiceComputeCalledOnceBeforeQuit() {
        // GIVEN
        when(scanner.nextLine())
                .thenReturn("AA")   // First valid input
                .thenReturn("Q")    // Quit command
                .thenReturn("BB");  // Input after quit (should not be processed)

        // WHEN
        gameInputHandlerService.play();

        // THEN
        // Verify that `compute` was called only once with the first valid input
        verify(scoreComputingService, times(1)).compute("AA");

        // Ensure `compute` was NOT called for "BB"
        verify(scoreComputingService, never()).compute("AABB");

        // Verify scanner was called exactly twice (for "AA" and "Q")
        verify(scanner, times(2)).nextLine();
    }

}
