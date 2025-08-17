package com.example.SpringSecurityRole;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /*
    http://localhost:8080/login
    login and pass correct credentials
    */

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // src/main/resources/templates/login.html
    }

    @GetMapping("/success")
    public String successPage() {
        return "success"; // src/main/resources/templates/success.html
    }

    @GetMapping("/unauthorized")
    public String unauthorizedPage() {
        return "unauthorized"; // src/main/resources/templates/unauthorized.html
    }

}