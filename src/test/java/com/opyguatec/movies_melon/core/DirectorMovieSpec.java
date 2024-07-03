package com.opyguatec.movies_melon.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class DirectorMovieSpec {

   @Test void 
   that_can_add_multiple_directors() {
      String director_a = "Steven Spielberg";
      Movie movie = new Movie();
      movie.add_an_director( director_a );
      boolean result = movie.has_this_directors(director_a);
      assertThat(result, is(true));
   }
}
