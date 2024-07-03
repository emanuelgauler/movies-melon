package com.opyguatec.movies_melon.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

public class CastMovieSpec {
   @Test void
   that_can_add_an_actor() {
      String actor = "Christofer Nolan";
      Movie movie = new Movie();

      movie.add_an_actor( actor );

      boolean result = movie.has_an_actor(actor);
      assertThat(result, is(true));
   }


   @Test void 
   that_can_add_multiple_actors() {
      String actor_1 = "Christofer Nolan";
      String actor_2 = "Angeline Jolie";

      Movie movie = new Movie();
      movie.add_actors( actor_1, actor_2 );

      boolean result = movie.has_actors(actor_1, actor_2);
      assertThat(result, is(true));
   }
}
