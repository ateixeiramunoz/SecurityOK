package com.atm.security.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String showIndex()
    {
        return "index";
    }

    @GetMapping("/private")
    public String showPrivate()
    {
        return "privado";
    }


    @GetMapping("/login")
    public String showAlejandro()
    {
        return "loginAlejandro";
    }
}
