package fr.clem.tennisscore;

import fr.clem.tennisscore.services.ResultPrinterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;
import java.util.Scanner;

@Configuration
public class BeanConfiguration {


    /***
     * Set default output for {@link ResultPrinterService}
     * @return
     */
    @Bean
    public PrintStream defaultPrintStream() {
        return System.out;
    }


    @Bean
    public Scanner defaultScanner() {
        return new Scanner(System.in);
    }
}
