package com.opyguatec.movies_melon.api.utils;

import com.opyguatec.movies_melon.core.Movie;

public class MovieResponseModel {

   public static MovieResponseModel with( Movie movie ) {
      MovieResponseModel model = new MovieResponseModel();
      model.movie = movie;
      model.movie_url = URLFormatter
            .with("localhost", 8080, "movies-melon")
            .format(movie);
      return model;
   }

   private Movie movie;
   public Movie getMovie() {
      return movie;
   }
   private String movie_url;
   public String getMovie_url() {
      return movie_url;
   }

}
