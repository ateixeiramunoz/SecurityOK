package com.atm.security.foro.controllers;


import com.atm.security.entities.Usuario;
import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import com.atm.security.foro.repositories.CanalRepository;
import com.atm.security.foro.repositories.MensajesForoRepository;
import com.atm.security.repositories.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/foro")
public class ForoController {


    final CanalRepository canalRepository;

    final
    MensajesForoRepository mensajesForoRepository;
    final
    UsuarioRepository usuarioRepository;


    public ForoController(CanalRepository canalRepository, MensajesForoRepository mensajesForoRepository, UsuarioRepository usuarioRepository)
    {
        this.canalRepository = canalRepository;
        this.mensajesForoRepository = mensajesForoRepository;
        this.usuarioRepository = usuarioRepository;

    }


    @GetMapping("")
    public String verForo(@RequestParam(required = false) Long canalId, Model model) {
        List<Canal> canales = canalRepository.findAll();
        if (canalId != null) {

            List<MensajeForo> mensajes;
            Optional<Canal> canal = canalRepository.findById(canalId);
            if(canal.isPresent())
            {
                mensajes  = mensajesForoRepository.findAllByCanalAndIdPadre(canal.get(), 0);
                //mensajes = mensajesForoRepository.findAllByCanal(canal.get());
                model.addAttribute("mensajes", mensajes);
                model.addAttribute("canalSeleccionado", canal.get());
            }

        } else {
            List<MensajeForo> mensajes;
            mensajes = mensajesForoRepository.findAll();
            model.addAttribute("mensajes", mensajes);
        }

        model.addAttribute("canales", canales);

        return "foro";
    }







    @PostMapping("/crearMensaje")
    public String postMensaje(@RequestParam String mensaje,
                              @RequestParam String mensajePadre,
                              @RequestParam(required = false) String canalId,
                              Model model,
                              Authentication auth) {

        MensajeForo mensajeForo = new MensajeForo();
        mensajeForo.setMensaje(mensaje);
        mensajeForo.setActive(true);
        mensajeForo.setVisible(false);
        mensajeForo.setCensored(false);
        mensajeForo.setFecha(LocalDateTime.now());

        // Canal
        if (canalId != null && !canalId.isEmpty()) {
            canalRepository.findById(Long.parseLong(canalId))
                    .ifPresent(mensajeForo::setCanal);
        }

        // Usuario autenticado
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        usuarioRepository.findByUsername(username)
                .ifPresent(mensajeForo::setUsuario);

        // ID Padre
        long idPadre = 0L;
        if (mensajePadre != null && !mensajePadre.isEmpty()) {
            idPadre = Long.parseLong(mensajePadre);
        }
        mensajeForo.setIdPadre(idPadre);

        // 1er guardado (obtenemos ID si es raíz)
        mensajeForo = mensajesForoRepository.save(mensajeForo);

        // ID Hilo
        if (idPadre == 0L) {
            // mensaje raíz → idHilo es su propio id
            mensajeForo.setIdHilo(mensajeForo.getId());
        } else {
            // respuesta → idHilo es el idHilo del mensaje padre
            mensajeForo.setIdHilo(
                    mensajesForoRepository.findById(idPadre)
                            .map(MensajeForo::getIdHilo)
                            .orElse(idPadre) // fallback si el padre no se encuentra
            );
        }

        // 2º guardado (con idHilo)
        mensajesForoRepository.save(mensajeForo);

        return "redirect:/foro?canalId=" + canalId;
    }




    @PostMapping("/responderMensaje")
    public String responderMensaje(@RequestParam String mensaje, @RequestParam String mensajePadre, @RequestParam long canalId, Model  model, Authentication auth)
    {
        MensajeForo mensajeForo = new MensajeForo();
        mensajeForo.setMensaje(mensaje);
        mensajeForo.setActive(true);
        Optional<Canal> canal = canalRepository.findById(canalId);
        canal.ifPresent(mensajeForo::setCanal);


        if(!mensajePadre.isEmpty())
            mensajeForo.setIdPadre(Long.parseLong(mensajePadre));

        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            mensajeForo.setUsuario(usuario.get());
        }


        mensajesForoRepository.save(mensajeForo);
        return "redirect:/foro?canalId=" + canalId;
    }






}
