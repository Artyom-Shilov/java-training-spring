package com.shilov.spring_reservation.view;


import com.shilov.spring_reservation.common.console.ConsoleOperator;
import com.shilov.spring_reservation.common.enums.CustomerMenuInteractionOutput;
import com.shilov.spring_reservation.common.enums.ResponseStatus;
import com.shilov.spring_reservation.controllers.ReservationController;
import com.shilov.spring_reservation.controllers.SpaceController;
import com.shilov.spring_reservation.controllers.requests.MakeCurrentUserReservationRequest;
import com.shilov.spring_reservation.controllers.requests.ReservationDateTimeInput;
import com.shilov.spring_reservation.controllers.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMenu extends ConsoleOperator {

    private static final String CUSTOMER_MENU_OPTIONS = """
            Customer menu options
            
            1: Browse available spaces
            2: Make a reservation
            3: View my reservations
            4: Cancel reservation
            5: Return to main menu
            """;

    private final SpaceController spaceController;
    private final ReservationController reservationController;

    @Autowired
    public CustomerMenu(SpaceController spaceController, ReservationController reservationController) {
        this.spaceController = spaceController;
        this.reservationController = reservationController;
    }


    public void showMenuOptions() {
        writeMessageInConsole(CUSTOMER_MENU_OPTIONS);
    }

    public CustomerMenuInteractionOutput processMenuInteractions() {
        writeMessageInConsole(ENTER_OPTION_NUMBER);
        return switch (readLineFromConsole()) {
            case "1" -> processAvailableSpacesBrowse();
            case "2" -> processReservationCreation();
            case "3" -> processCurrentUserReservationBrowse();
            case "4" -> processReservationCancel();
            case "5" -> CustomerMenuInteractionOutput.BROWSE_MAIN_MENU;
            default -> CustomerMenuInteractionOutput.INTERACTION_FAILED;
        };
    }

    private CustomerMenuInteractionOutput processAvailableSpacesBrowse() {
        Response response = spaceController.getAvailableSpaces(readReservationTime());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private ReservationDateTimeInput readReservationTime() {
        writeMessageInConsole(ENTER_DATE);
        String date = readLineFromConsole();
        writeMessageInConsole(ENTER_START_TIME);
        String startTime = readLineFromConsole();
        writeMessageInConsole(ENTER_END_TIME);
        String endTime = readLineFromConsole();
        return new ReservationDateTimeInput(date, startTime, endTime);
    }

    private CustomerMenuInteractionOutput processReservationCreation() {
        writeMessageInConsole(ENTER_SPACE_ID);
        String spaceId = readLineFromConsole();
        ReservationDateTimeInput reservationTime = readReservationTime();
        Response response = reservationController.makeCurrentUserReservation(
                new MakeCurrentUserReservationRequest(spaceId, reservationTime));
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private CustomerMenuInteractionOutput processCurrentUserReservationBrowse() {
        Response response = reservationController.getCurrentUserReservations();
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }

    private CustomerMenuInteractionOutput processReservationCancel() {
        writeMessageInConsole(ENTER_RESERVATION_ID);
        Response response = reservationController.cancelReservation(readLineFromConsole());
        writeMessageInConsole(response.getPayload());
        return response.getStatus() == ResponseStatus.SUCCESS
                ? CustomerMenuInteractionOutput.BROWSE_CUSTOMER_MENU
                : CustomerMenuInteractionOutput.INTERACTION_FAILED;
    }
}
