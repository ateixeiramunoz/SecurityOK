package com.atm.security.APICARATULAS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Pelicula {

    public boolean adult;

    @JsonProperty("backdrop_path")
    public String backdropPath;

    @JsonProperty("genre_ids")
    public List<Integer> genreIds;

    public int id;

    @JsonProperty("original_language")
    public String originalLanguage;

    @JsonProperty("original_title")
    public String originalTitle;

    public String overview;
    public double popularity;

    @JsonProperty("poster_path")
    public String posterPath;

    @JsonProperty("release_date")
    public String releaseDate;

    public String title;
    public boolean video;

    @JsonProperty("vote_average")
    public double voteAverage;

    @JsonProperty("vote_count")
    public int voteCount;
}
