package com.opyguatec.movies_melon.core;

import java.util.List;

public interface MoviesStore {

   void add(Movie movie) throws MovieExistingError;

   List<Movie> listing() throws Exception;

   boolean is_online();

   Movie find_by_id(String its_id);

}
