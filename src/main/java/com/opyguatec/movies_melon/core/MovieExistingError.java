package com.opyguatec.movies_melon.core;

public class MovieExistingError extends RuntimeException {
   public MovieExistingError(Movie movie) {
      super(String.format("La %s ya está registrada", movie.getTitle()));
   }
}
