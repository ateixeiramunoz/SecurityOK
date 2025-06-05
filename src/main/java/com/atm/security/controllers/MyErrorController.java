package com.atm.security.controllers;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {



    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        ;
        model.addAttribute("message", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("title", "Error");
        model.addAttribute("url", "/error");
        model.addAttribute("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        //do something like logging
        return "error";
    }


}