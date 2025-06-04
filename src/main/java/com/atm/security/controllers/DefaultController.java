package com.atm.security.controllers;


import com.atm.security.entities.Producto;
import com.atm.security.entities.Usuario;
import com.atm.security.repositories.ProductoRepository;
import com.atm.security.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    ProductoRepository productoRepository;
    final
    UsuarioRepository usuarioRepository;

    public DefaultController(ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
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

        //SecurityContext context = SecurityContextHolder.getContext();
        //Authentication authentication = context.getAuthentication();
        //String username = authentication.getName();
        // Object principal = authentication.getPrincipal();
        //Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String username = auth.getName();


        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        log.info("El usuario {} tiene {} authorities", username, authorities.size());
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);


        return "privado";

    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {

        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String showAlejandro()
    {
        return "loginAlejandro";
    }
}
