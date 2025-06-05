package com.atm.security.foro.entities;


import com.atm.security.entities.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeForo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idPadre;

    private String titulo;
    private String mensaje;

    private boolean isActive;
    private boolean isCensored;
    private boolean isVisible;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Canal canal;

}
