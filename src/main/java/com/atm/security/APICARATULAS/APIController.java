package com.atm.security.APICARATULAS;


import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class APIController {

    @GetMapping ("/peliculas")
    public String mostrarPaginaImagenes() {

        return "peliculas";

    }


    @GetMapping ("/peliculas/buscar")
    public String index(Model model, @RequestParam(defaultValue = "rambo") String query) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query="+query+"&include_adult=false&language=en-US"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NWM5YmNkZGUxMTJmYWUxODBlYTMxNmM3MDZmNjIzYiIsIm5iZiI6MTc0OTY0NDU0MC4wNTksInN1YiI6IjY4NDk3NGZjYmMwNGJmOWJiNjNmZDYyMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.UCI2HCbcTkhFp8_Hg-Opez_d3Z1ffifv0iKFmwaPMLI")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ResultadoBusqueda resultado = mapper.readValue(response.body(), ResultadoBusqueda.class);
            model.addAttribute("peliculasLocalizadas", resultado.getResults());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "peliculas";



    }


    @GetMapping("/poster/{filename}")
    public ResponseEntity<byte[]> proxyPoster(@PathVariable String filename) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://image.tmdb.org/t/p/w500/" + filename))
                    .build();

            HttpResponse<byte[]> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofByteArray());

            return ResponseEntity
                    .ok()
                    .header("Content-Type", "image/jpeg")
                    .body(response.body());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
