package com.atm.security.foro.repositories;


import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {

}

