package com.atm.security.foro.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Canal {

    @Id
    private long id;

    private String nombre;

    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<MensajeForo> mensajes;


}
