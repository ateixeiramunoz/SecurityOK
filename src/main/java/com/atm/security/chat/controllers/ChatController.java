package com.atm.security.chat.controllers;

import com.atm.security.chat.entities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {


    @MessageMapping("/ciencia")
    @SendTo("/topic/ciencia")
    public ChatMessage responderMensaje(ChatMessage pregunta) throws Exception {


        ChatMessage respuesta = new ChatMessage();
        respuesta.setMessage("hola "+pregunta.getName());


        return respuesta;
    }



    @GetMapping("/mostrarChat")
    public String mostrarChat() {
        return "chat";
    }







}