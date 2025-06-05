package com.atm.security.foro.repositories;


import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajesForoRepository extends JpaRepository<MensajeForo, Long> {

    List<MensajeForo> findAllByCanal(Canal canal);

}

