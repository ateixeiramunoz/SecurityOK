package com.atm.security.APICARATULAS;

import lombok.Data;
import java.util.List;

@Data
public class ResultadoBusqueda {

    public int page;
    public List<Pelicula> results;
    public int total_pages;
    public int total_results;
}
