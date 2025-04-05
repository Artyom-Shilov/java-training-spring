package com.shilov.spring_reservation.controllers.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuDisplayController {

    @GetMapping("/")
    public String showAuthMenu() {
        return "auth_menu";
    }

    @GetMapping("/admin/menu")
    public String showAdminMenu() {
        return "admin_menu";
    }

    @GetMapping("/customer/menu")
    public String showCustomerMenu() {
        return "customer_menu";
    }
}
