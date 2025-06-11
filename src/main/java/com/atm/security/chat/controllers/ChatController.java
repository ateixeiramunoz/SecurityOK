package com.atm.security.chat.controllers;

import com.atm.security.chat.entities.ChatMessage;
import com.atm.security.chat.repositories.ChatMessageRepository;
import com.atm.security.entities.Usuario;
import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import com.atm.security.foro.repositories.CanalRepository;
import com.atm.security.foro.repositories.MensajesForoRepository;
import com.atm.security.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class ChatController {

    private final UsuarioRepository usuarioRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final CanalRepository canalRepository;
    private final MensajesForoRepository mensajesForoRepository;

    public ChatController(UsuarioRepository usuarioRepository, ChatMessageRepository chatMessageRepository, CanalRepository canalRepository, MensajesForoRepository mensajesForoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.canalRepository = canalRepository;
        this.mensajesForoRepository = mensajesForoRepository;
    }

    @MessageMapping("/ciencia") //es decir /app/ciencia
    @SendTo("/topic/ciencia") //Al recibir el mensaje en /app/ciencia enviamos el mensaje a /topic/ciencia
    public ChatMessage reenviarMensaje(ChatMessage pregunta, Authentication authentication) throws Exception {
        String nombre = "";
        try {
            nombre = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        catch (NullPointerException np) {
            EntityNotFoundException re = new EntityNotFoundException("Usuario no encontrado");
            re.setStackTrace(np.getStackTrace());
            throw re;
        }

        ChatMessage respuesta = new ChatMessage();
        usuarioRepository.findByUsername(nombre).ifPresentOrElse(respuesta::setUsuario, RuntimeException::new);
        respuesta.setMessage(pregunta.getMessage());
        respuesta.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(respuesta);


        MensajeForo mensajeForo = new MensajeForo();
        mensajeForo.setMensaje(pregunta.getMessage());
        mensajeForo.setActive(true);
        Optional<Canal> canal = canalRepository.findById(1L);
        canal.ifPresent(mensajeForo::setCanal);
        mensajeForo.setIdPadre(0L);
        mensajeForo.setUsuario(respuesta.getUsuario());
        mensajesForoRepository.save(mensajeForo);


        //Devuelvo el mensaje que me han enviado al broker, por el canal de topic ciencia
        // Cualquiera el que esté suscrito y conectado lo recibirá
        return respuesta;
    }



    @GetMapping("/mostrarChat")
    public String mostrarChat() {
        return "chat";
    }






}