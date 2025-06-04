package com.atm.security.loaders;

import ch.qos.logback.core.LayoutBase;
import com.atm.security.entities.Usuario;
import com.atm.security.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Configuration
@Log4j2
@Profile("local")
public class LocalDataLoader {

    final
    UsuarioRepository usuarioRepository;

    public LocalDataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @PostConstruct
    public void loadDataLocal() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        log.info("Iniciando la carga de datos para el perfil local");

        Usuario usuario = new Usuario();
        usuario.setUsername("user");
        usuario.setPassword(encoder.encode("123456"));
        usuario.setNombre("Usuario");
        usuario.setApellido("Apellido");
        usuario.setCorreo("correo");
        usuario.setRol("ROLE_ADMIN");

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        Collection<GrantedAuthority> updatedAuthorities = new ArrayList<GrantedAuthority>();
        updatedAuthorities.add(authority);

        usuario.getAuthorities().addAll(updatedAuthorities);

        usuarioRepository.save(usuario);


        usuario = new Usuario();
        usuario.setUsername("alejandro");
        usuario.setPassword(encoder.encode("123456"));
        usuario.setNombre("Usuario");
        usuario.setApellido("Apellido");
        usuario.setCorreo("correo");
        usuarioRepository.save(usuario);


        log.info("Datos de entidades cargados correctamente.");
    }





}
