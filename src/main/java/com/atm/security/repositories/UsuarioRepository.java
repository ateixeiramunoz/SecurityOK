package com.atm.security.repositories;


import com.atm.security.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String jetBrains);
    Optional<Usuario> findByUsername(String jetBrains);
    Optional<Usuario> findByCorreo(String jetBrains);

}