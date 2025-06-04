package com.atm.security.controllers;


import com.atm.security.email.EnviadorDeEmails;
import com.atm.security.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collection;

/**
 * The type Default controller.
 */
@Log4j2
@Controller
public class DefaultController {



    private final EnviadorDeEmails enviadorDeEmails;

    /**
     * The Usuario repository.
     */
    final
    UsuarioRepository usuarioRepository;

    /**
     * Instantiates a new Default controller.
     *
     * @param usuarioRepository the usuario repository
     */
    public DefaultController(UsuarioRepository usuarioRepository, EnviadorDeEmails enviadorDeEmails) {
        this.usuarioRepository = usuarioRepository;
        this.enviadorDeEmails = enviadorDeEmails;
    }


    /**
     * Show index string.
     *
     * @return the string
     */
    @GetMapping("/")
    public String showIndex()
    {
        enviadorDeEmails.enviarEmail("pepe@pepe.com","hola", "supermensaje");
        return "index";
    }

    /**
     * Show private string.
     *
     * @param model     the model
     * @param principal the principal
     * @param auth      the auth
     * @return the string
     */
    @GetMapping("/private")
    public String showPrivate( Model model, Principal principal, Authentication auth)
    {
        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        log.info("El usuario {} tiene {} authorities", username, authorities.size());
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);
        enviadorDeEmails.enviarEmailMIME("pepe@pepe.com","hola", "supermensaje", principal);

        return "privado";

    }


    /**
     * Show alejandro string.
     *
     * @return the string
     */
    @GetMapping("/login")
    public String showAlejandro()
    {
        return "loginAlejandro";
    }
}
