package com.atm.security.foro.controllers;


import com.atm.security.entities.Usuario;
import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import com.atm.security.foro.repositories.CanalRepository;
import com.atm.security.foro.repositories.MensajesForoRepository;
import com.atm.security.repositories.UsuarioRepository;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/canales")
public class CanalesController {


    final CanalRepository canalRepository;


    public CanalesController(CanalRepository canalRepository, MensajesForoRepository mensajesForoRepository, UsuarioRepository usuarioRepository, MultipartConfigElement multipartConfigElement, ContentNegotiationManager mvcContentNegotiationManager) {
        this.canalRepository = canalRepository;
    }



    @GetMapping
    public String canales(Model model) {

        model.addAttribute("canales", canalRepository.findAll());
        return "canales";
    }


    @PostMapping("/crearCanal")
    public String postCanal(@RequestParam String nombre,  Model model, Authentication auth) {

        Canal canal = new Canal();
        canal.setNombre(nombre);
        canalRepository.save(canal);

        //mensajesForoRepository.save(mensajeForo);
        return "redirect:/canales";
    }





}
