package com.atm.security.services;


import com.atm.security.entities.Usuario;
import com.atm.security.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    final
    UsuarioRepository usuarioRepository;

    public MyUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Email " +
                        nombreUsuario + " not found"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }



    @PostAuthorize("#username == authentication.principal.username")
    public Usuario modifyUser(Usuario usuario) throws UsernameNotFoundException {

        Usuario usuarioRecuperado = new Usuario();
        usuarioRecuperado = usuarioRepository.findByUsername(usuario.getUsername()).get();
        return usuarioRecuperado;

    }



    @PostAuthorize("returnObject.count < 3")
    public void registrarDispositivos(Usuario usuario) throws UsernameNotFoundException {

        return usuarioRecuperado;

    }



}
