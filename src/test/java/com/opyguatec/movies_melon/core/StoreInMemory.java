package com.opyguatec.movies_melon.core;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class StoreInMemory implements MoviesStore {
   
   List<Movie> movies = new ArrayList<Movie>();

   @Override
   public void add(Movie movie) {
      movies.add(movie);
   }

   @Override
   public List<Movie> listing() {
      return movies;
   }

   @Override
   public boolean is_online() {
      return true;
   }

   @Override
   public Optional<Movie> find_by_id(String its_id) {
      return  movies.stream()
         .filter( e -> e.its_id() == its_id )
         .findFirst();
   }

}
