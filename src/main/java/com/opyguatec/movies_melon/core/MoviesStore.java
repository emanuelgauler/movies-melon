package com.opyguatec.movies_melon.core;

import java.util.List;

public interface MoviesStore {

   void add(Movie movie) throws MovieExistingError, Exception;

   List<Movie> listing();

   boolean is_online();

   Movie find_by_id(String its_id);

   void remove_from_id(String its_id) throws MovieNotFoundError;

   void save(Movie movie);

}
