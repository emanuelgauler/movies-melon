package com.opyguatec.movies_melon.core;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.opyguatec.movies_melon.core.Movie.GenreDumper;

public class GenreMovieSpec {
   @Test void 
   that_can_add_multiple_genres() {
      String genre_1 = "Drama";
      String genre_2 = "Terror";
      Movie movie = new Movie();
      movie.add_genres( genre_1, genre_2 );
      boolean result = movie.has_genres(genre_1, genre_2);
      assertThat( result, is(true) );
   }

   @Test void 
   that_can_dump_your_genres_on_output() {
      GenreDumper dumper = new GenreDumper() {

         public List<String> genres = new ArrayList<String>();

         @Override
         public void dump(List<String> genres) {
            this.genres.addAll( genres );
         }

         @Override
         public List<String> items() {
            return this.genres;
         }

      };
      
      Movie movie = new Movie();
      movie.add_genres("Drama", "Terror");

      movie.dump_on( dumper );
      
      List<String> output = dumper.items();

      assertThat(output, hasItems("Drama", "Terror"));
   }
}
