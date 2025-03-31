package com.shilov.spring_reservation.view;

import com.shilov.spring_reservation.common.console.ConsoleOperator;
import com.shilov.spring_reservation.common.enums.MainMenuInteractionOutput;
import com.shilov.spring_reservation.common.enums.ResponseStatus;
import com.shilov.spring_reservation.controllers.AuthController;
import com.shilov.spring_reservation.controllers.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainMenu extends ConsoleOperator {

    private static final String MAIN_MENU_OPTIONS = """
            1: Admin login
            2: User login
            3: Exit
            """;
    private static final String ENTER_LOGIN_MESSAGE = "Please, enter your login: ";

    private final AuthController authController;

    @Autowired
    public MainMenu(AuthController authController) {
        this.authController = authController;
    }

    public void showMenuOptions() {
        writeMessageInConsole(MAIN_MENU_OPTIONS);
    }

    public MainMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processAdminLogin();
            case "2" -> processCustomerLogin();
            case "3" -> processExit();
            default -> MainMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private MainMenuInteractionOutput processAdminLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsAdmin(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_ADMIN_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processCustomerLogin() {
        writeMessageInConsole(ENTER_LOGIN_MESSAGE);
        Response response = authController.loginAsCustomer(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? MainMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : MainMenuInteractionOutput.INTERACTION_FAILED;
    }

    private MainMenuInteractionOutput processExit() {
        writeMessageInConsole(authController.logout().getPayload());
        return MainMenuInteractionOutput.SESSION_FINISHED;
    }
}
