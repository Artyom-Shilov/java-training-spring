package com.shilov.spring_reservation.common.console;

import java.util.Scanner;

public abstract class ConsoleOperator {

    protected static final String ENTER_OPTION_NUMBER = "Please, enter menu option number: ";
    protected static final String ENTER_DATE = "Please, enter date in format yyyy-mm-dd:";
    protected static final String ENTER_START_TIME = "Please, enter start time in format HH:mm:ss:";
    protected static final String ENTER_END_TIME = "Please, enter end time in format HH:mm:ss:";
    protected static final String ENTER_SPACE_ID = "Please, enter space id:";
    protected static final String ENTER_RESERVATION_ID = "Please, enter reservation id:";
    protected static final String ENTER_SPACE_TYPE = "Please, enter space type from list: ";
    protected static final String ENTER_SPACE_PRICE = "Please, enter space price rate: ";

    private static final Scanner SCANNER = new Scanner(System.in);

    protected String readLineFromConsole() {
        return SCANNER.nextLine();
    }

    protected void writeMessageInConsole(String message) {
        System.out.println(message);
    }
}
