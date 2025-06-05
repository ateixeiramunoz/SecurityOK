package com.atm.security.foro.controllers;


import com.atm.security.entities.Usuario;
import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import com.atm.security.foro.repositories.CanalRepository;
import com.atm.security.foro.repositories.MensajesForoRepository;
import com.atm.security.repositories.UsuarioRepository;
import jakarta.servlet.MultipartConfigElement;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/foro")
public class ForoController {


    final CanalRepository canalRepository;

    final
    MensajesForoRepository mensajesForoRepository;
    final
    UsuarioRepository usuarioRepository;
    private final MultipartConfigElement multipartConfigElement;
    private final ContentNegotiationManager mvcContentNegotiationManager;

    public ForoController(CanalRepository canalRepository, MensajesForoRepository mensajesForoRepository, UsuarioRepository usuarioRepository, MultipartConfigElement multipartConfigElement, ContentNegotiationManager mvcContentNegotiationManager) {
        this.canalRepository = canalRepository;
        this.mensajesForoRepository = mensajesForoRepository;
        this.usuarioRepository = usuarioRepository;
        this.multipartConfigElement = multipartConfigElement;
        this.mvcContentNegotiationManager = mvcContentNegotiationManager;
    }


    @GetMapping
    public String foro(Model model) {

        model.addAttribute("mensajes", mensajesForoRepository.findAll());
        return "foro";
    }


    @PostMapping("/crearMensaje")
    public String postMensaje(@RequestParam String mensaje, @RequestParam String mensajePadre, Model model, Authentication auth) {


        MensajeForo mensajeForo = new MensajeForo();
        mensajeForo.setMensaje(mensaje);
        mensajeForo.setActive(true);
        mensajeForo.setCanal(canalRepository.findById(0L).get());



        if(!mensajePadre.isEmpty())
            mensajeForo.setIdPadre(Long.parseLong(mensajePadre));

        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            mensajeForo.setUsuario(usuario.get());
        }


        mensajesForoRepository.save(mensajeForo);
        return "redirect:/foro";
    }






}
