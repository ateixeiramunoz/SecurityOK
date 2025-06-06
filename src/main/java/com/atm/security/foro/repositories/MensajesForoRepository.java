package com.atm.security.foro.repositories;


import com.atm.security.foro.entities.Canal;
import com.atm.security.foro.entities.MensajeForo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensajesForoRepository extends JpaRepository<MensajeForo, Long> {

    List<MensajeForo> findAllByCanal(Canal canal);

    List<MensajeForo> findAllByCanalAndIdPadre(Canal canal, long idPadre);

    Optional<MensajeForo> findOneByIdHiloEqualsAndIdPadreLessThanEqualAndIdGreaterThan(long idHilo, long idPadre, long idMensaje);


    List<MensajeForo> findByIdHiloEqualsAndIdPadreGreaterThanAndIdLessThan(long idHilo, Long idPadre, long idMensaje);
    List<MensajeForo> findByIdHiloEqualsAndIdPadreGreaterThan(long l, long l1);
}




