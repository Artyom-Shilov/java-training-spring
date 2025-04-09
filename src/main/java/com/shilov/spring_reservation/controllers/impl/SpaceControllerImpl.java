package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.enums.SpaceType;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.entities.ReservationDateTime;
import com.shilov.spring_reservation.entities.Space;
import com.shilov.spring_reservation.models.SpaceModel;
import com.shilov.spring_reservation.services.SpaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SpaceControllerImpl {

    private final SpaceService spaceService;

    @Autowired
    public SpaceControllerImpl(SpaceService spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping("space/creation")
    public String showSpaceCreationForm(Model model) {
        model.addAttribute(new SpaceModel());
        return "space_creation_form";
    }

    @PostMapping("space")
    public String createSpace(@Valid @ModelAttribute SpaceModel spaceModel,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "space_creation_form";
        }
        try {
            SpaceType spaceType = SpaceType.valueOf(spaceModel.getType().trim().toUpperCase());
            Long id = spaceService.addNewSpace(new Space(
                    spaceType,
                    spaceModel.getPrice()));
            model.addAttribute("successMessage", "Space has been created with id : " + id);
        } catch (ServiceException | IllegalArgumentException e) {
            model.addAttribute("error", "Error during space creation process");
        }
        return "space_creation_form";
    }

    @GetMapping("space/removal")
    public String showSpaceRemovalForm(Model model) {
        model.addAttribute(new IdInput());
        return "space_removal_form";
    }

    @PostMapping("space/removal")
    public String deleteSpace(@Valid @ModelAttribute IdInput idInput,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "space_removal_form";
        }
        try {
            spaceService.deleteSpace(idInput.getId());
            model.addAttribute("successMessage", "Space has been deleted");
        } catch (Exception e) {
            model.addAttribute("error", "Error during space removal process");
        }
        return "space_removal_form";
    }

    @PostMapping
    public String updateSpace(@Valid @ModelAttribute SpaceModel spaceModel,
                              @Valid @ModelAttribute IdInput idInput,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "update_space_form";
        }
        try {
            Space updatedData = new Space(
                    SpaceType.valueOf(spaceModel.getType().trim().toUpperCase()),
                    spaceModel.getPrice());
            spaceService.updateSpace(idInput.getId(), updatedData);
            model.addAttribute("successMessage", "Space has been updated");
        } catch (ServiceException | IllegalArgumentException e) {
            model.addAttribute("error", "Error during space update process");
        }
        return "update_space_form";
    }

    @GetMapping("space/available/selection")
    public String showAvailableSpacesForm(Model model) {
        model.addAttribute(new ReservationDateTimeInput());
        return "space_available_form";
    }

    @GetMapping("space/available")
    public String getAvailableSpaces(@Valid @ModelAttribute ReservationDateTimeInput dateTimeInput,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "space_available_form";
        }
        try {
            List<Space> availableSpaces = spaceService.getAvailableForReservationSpaces(
                    new ReservationDateTime(dateTimeInput.getDate(),
                            dateTimeInput.getStartTime(),
                            dateTimeInput.getEndTime()));
            model.addAttribute("spaces", availableSpaces);
            return "spaces_list";
        } catch (ServiceException e) {
            model.addAttribute("error", "Error during available space selection process");
            return "space_available_form";
        }
    }
}
