package com.atm.security.controllers;


import com.atm.security.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collection;

@Log4j2
@Controller
public class DefaultController {

    final
    UsuarioRepository usuarioRepository;

    public DefaultController(  UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }



    @GetMapping("/")
    public String showIndex()
    {
        return "index";

    }

    @GetMapping("/private")
    public String showPrivate( Model model, Principal principal, Authentication auth)
    {
        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        log.info("El usuario {} tiene {} authorities", username, authorities.size());
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);
        return "privado";

    }


    @GetMapping("/login")
    public String showAlejandro()
    {
        return "loginAlejandro";
    }
}
