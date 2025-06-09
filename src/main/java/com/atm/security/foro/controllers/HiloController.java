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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HiloController {

    final CanalRepository canalRepository;
    final MensajesForoRepository mensajesForoRepository;
    final UsuarioRepository usuarioRepository;


    public HiloController(CanalRepository canalRepository, MensajesForoRepository mensajesForoRepository, UsuarioRepository usuarioRepository)
    {
        this.canalRepository = canalRepository;
        this.mensajesForoRepository = mensajesForoRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @GetMapping("/hilo/{mensajeID}")
    public String verForo(@PathVariable(value="mensajeID") String mensajeId, @RequestParam(required = false) Long canalId, Model model) {

        List<Canal> canales = canalRepository.findAll();

        if (canalId != null) {

            List<MensajeForo> mensajes = new ArrayList<>();
            Optional<Canal> canal = canalRepository.findById(canalId);
            Optional<MensajeForo> mensaje = mensajesForoRepository.findById(Long.parseLong(mensajeId));
            if(canal.isPresent())
            {

                //Necesitamos calcular el siguiente mensaje de primer nivel o de nivel igual al que queremos consultar
                if(mensaje.isPresent())
                {
                    Optional<MensajeForo> siguientemensaje = mensajesForoRepository.findOneByIdHiloEqualsAndIdPadreLessThanEqualAndIdGreaterThan(Long.parseLong(mensajeId), 0L, Long.parseLong(mensajeId));

                    if(siguientemensaje.isPresent())
                    {
                        mensajes = mensajesForoRepository.findAllByIdHiloEqualsAndIdPadreGreaterThanAndIdLessThan(Long.parseLong(mensajeId), 0L, Long.parseLong(mensajeId));
                        model.addAttribute("mensajes", mensajes);
                        model.addAttribute("canalSeleccionado", canal.get());
                    }
                    else
                    {
                        List<MensajeForo> mensajesok = mensajesForoRepository.buscarRespuestas(Long.parseLong(mensajeId));
                        Map<Long, List<MensajeForo>> hijosPorPadre = mensajesok.stream()
                                .collect(Collectors.groupingBy(MensajeForo::getIdPadre));
                        model.addAttribute("mensajes", hijosPorPadre);

                        //model.addAttribute("mensajes", mensajesok);
                        model.addAttribute("canalSeleccionado", canal.get());
                    }
                }
            }
        } else {
            List<MensajeForo> mensajes;
            mensajes = mensajesForoRepository.findAll();
            model.addAttribute("mensajes", mensajes);
        }

        model.addAttribute("canales", canales);

        return "hilo";
    }







    @PostMapping("/crearMensaje")
    public String postMensaje(@RequestParam String mensaje, @RequestParam String mensajePadre, @RequestParam(required = false) String canalId, Model  model, Authentication auth) {

        MensajeForo mensajeForo = new MensajeForo();
        mensajeForo.setMensaje(mensaje);
        mensajeForo.setActive(true);
        mensajeForo.setFecha(LocalDateTime.now());
        if(!canalId.isEmpty()){
            Optional<Canal> canal = canalRepository.findById(Long.parseLong(canalId));
            canal.ifPresent(mensajeForo::setCanal);

        }
        List<MensajeForo> mensajes;
        Canal canalSeleccionado;

        if(!mensajePadre.isEmpty())
            mensajeForo.setIdPadre(Long.parseLong(mensajePadre));

        if (canalId.isEmpty()) {
            mensajes = mensajesForoRepository.findAll();

        } else {

            canalSeleccionado= canalRepository.findById(Long.valueOf(canalId)).orElse(null);
            mensajes = mensajesForoRepository.findAllByCanal(canalSeleccionado);
        }

        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);

        if (usuario.isPresent()) {
            mensajeForo.setUsuario(usuario.get());
        }


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
