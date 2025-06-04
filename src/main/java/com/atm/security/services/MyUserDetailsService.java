package com.atm.security.services;


import com.atm.security.entities.Usuario;
import com.atm.security.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario employee = usuarioRepository.findByUsername(username);

        employee.getRoles()
        Set<SimpleGrantedAuthority> authorities = .stream()

                .map(role -> new SimpleGrantedAuthority(role.getName()))

                .collect(Collectors.toSet());

        return new User(employee.getUsername(), employee.getPassword(), authorities);





    }

}
