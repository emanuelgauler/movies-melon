package com.opyguatec.movies_melon.core;

import java.util.List;
import java.util.Optional;

public interface MoviesStore {

   void add(Movie movie) throws MovieExistingError;

   List<Movie> listing() throws Exception;

   boolean is_online();

   Optional<Movie> find_by_id(String its_id);

}
