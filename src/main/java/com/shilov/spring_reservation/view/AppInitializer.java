package com.shilov.spring_reservation.view;

import com.shilov.spring_reservation.common.console.ConsoleOperator;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer extends ConsoleOperator {

    private static final String WELCOME_MESSAGE = "Welcome to space reservation app!\n";

    public void initApp() {
        writeMessageInConsole(WELCOME_MESSAGE);
    }
}
