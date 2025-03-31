package com.shilov.spring_reservation.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class View {

    private final MainMenu mainMenu;
    private final AdminMenu adminMenu;
    private final CustomerMenu customerMenu;

    @Autowired
    public View(MainMenu mainMenu, AdminMenu adminMenu, CustomerMenu customerMenu) {
        this.mainMenu = mainMenu;
        this.adminMenu = adminMenu;
        this.customerMenu = customerMenu;
    }

    public void dispatchMainMenuOutput(boolean isMenuOptionsVisible) {
        if (isMenuOptionsVisible) {
            mainMenu.showMenuOptions();
        }
        switch (mainMenu.processMenuInteractions()) {
            case SESSION_FINISHED -> System.exit(0);
            case INTERACTION_FAILED -> dispatchMainMenuOutput(false);
            case BROWSE_ADMIN_MENU -> dispatchAdminMenuOutput(true);
            case BROWSE_CUSTOMER_MENU -> dispatchCustomerMenuOutput(true);
        }
    }

    private void dispatchCustomerMenuOutput(boolean isMenuOptionsVisible) {
        if (isMenuOptionsVisible) {
            customerMenu.showMenuOptions();
        }
        switch (customerMenu.processMenuInteractions()) {
            case BROWSE_CUSTOMER_MENU, INTERACTION_FAILED -> dispatchCustomerMenuOutput(false);
            case BROWSE_MAIN_MENU -> dispatchMainMenuOutput(true);
        }
    }

    private void dispatchAdminMenuOutput(boolean isMenuOptionsVisible) {
        if (isMenuOptionsVisible) {
            adminMenu.showMenuOptions();
        }
        switch (adminMenu.processMenuInteractions()) {
            case BROWSE_ADMIN_MENU, INTERACTION_FAILED -> dispatchAdminMenuOutput(false);
            case BROWSE_MAIN_MENU -> dispatchMainMenuOutput(true);
        }
    }
}
