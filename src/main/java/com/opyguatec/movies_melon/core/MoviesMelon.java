package com.opyguatec.movies_melon.core;
import java.util.List;

public class MoviesMelon {

   private MoviesStore store;

   public MoviesMelon(MoviesStore movies_store) {
      this.store = movies_store;
   }

   public List<Movie> listing() throws Exception {
      return store.listing();
   }

   public void add(Movie movie) throws MovieExistingError, Exception {
      store.add(movie);
   }

   public Movie find_movie_for_id(String its_id) {
      return store.find_by_id(its_id);
   }

   public void remove_from_id(String its_id) throws MovieNotFoundError {
      store.remove_from_id(its_id);
   }

   public void save(Movie movie) throws MovieNotFoundError, Exception {
      store.save(movie);
   }

}
