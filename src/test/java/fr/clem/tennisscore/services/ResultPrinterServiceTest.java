package fr.clem.tennisscore.services;

import fr.clem.tennisscore.model.GameResultModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class ResultPrinterServiceTest {

    @Test
    void testPrint_WithCustomOutputStream() {
        // GIVEN
        List<GameResultModel> results = List.of(
                GameResultModel.builder()
                        .scorePlayerA(1)
                        .scorePlayerB(0)
                        .build(),
                GameResultModel.builder()
                        .scorePlayerA(2)
                        .scorePlayerB(0)
                        .build(),
                GameResultModel.builder()
                        .scorePlayerA(3)
                        .scorePlayerB(0)
                        .build(),
                GameResultModel.builder()
                        .scorePlayerA(3)
                        .scorePlayerB(1)
                        .build(),
                GameResultModel.builder()
                        .scorePlayerA(5)
                        .scorePlayerB(1)
                        .build()
        );

        // Créer un ByteArrayOutputStream pour capturer la sortie
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream customPrintStream = new PrintStream(outContent);

        // Injecter le PrintStream personnalisé dans ResultPrinter
        ResultPrinterService printer = new ResultPrinterService(customPrintStream);

        // WHEN
        printer.print(results);

        // THEN
        String expectedOutput = """
            Player A : 15 / Player B : 0
            Player A : 30 / Player B : 0
            Player A : 40 / Player B : 0
            Player A : 40 / Player B : 15
            Player A wins the game
            """;
        Assertions.assertThat(outContent.toString())
                .isEqualToNormalizingNewlines(expectedOutput);
    }

}
