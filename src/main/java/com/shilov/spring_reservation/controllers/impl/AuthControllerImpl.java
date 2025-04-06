package com.shilov.spring_reservation.controllers.impl;

import com.shilov.spring_reservation.common.enums.UserRole;
import com.shilov.spring_reservation.common.exceptions.ServiceException;
import com.shilov.spring_reservation.models.LoginInput;
import com.shilov.spring_reservation.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthControllerImpl {

    private final AuthService authService;

    @Autowired
    public AuthControllerImpl(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam("role") String userRole,  Model model) {
        model.addAttribute(new LoginInput());
        model.addAttribute("role", userRole);
        return "login_form";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("role") String userRole,
                          @Valid @ModelAttribute("loginInput") LoginInput loginInput,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("role", userRole);
            return "login_form";
        }
        try {
            UserRole role = UserRole.valueOf(userRole.trim().toUpperCase());
            authService.signIn(loginInput.getLogin(), role);
            return switch (role) {
                case ADMIN -> "redirect:/admin/menu";
                case CUSTOMER -> "redirect:/customer/menu";
            };
        } catch (ServiceException | IllegalArgumentException e) {
            model.addAttribute("error", "Error during login process");
            model.addAttribute("role", userRole);
            return "login_form";
        }
    }

    @PostMapping("logout")
    public String logout() throws ServiceException {
            authService.signOut();
        return "redirect:/";
    }
}
