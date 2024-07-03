package com.opyguatec.movies_melon.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MoviesTest {
   @Test void 
   que_retorna_un_array_de_peliculas() throws Exception {
      MoviesStore store_in_memory = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store_in_memory);

      assertThat(movies.listing(), is(empty()));
   }

   @Test void 
   que_acepta_un_almacen_de_datos_y_esta_vacio() throws Exception {
      MoviesStore store_in_memory = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store_in_memory);
      
      assertThat(movies.listing(), is(empty()));
   }


   @Test void
   que_el_almacen_guarda_la_pelicula_que_MoviesMelon_recibe() throws Exception {
      Movie movie = new Movie();
      MoviesStore store_in_memory = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store_in_memory);

      movies.add( movie );
      Movie movie_expected = movies.listing().get(0);

      assertThat(movie, equalTo(movie_expected));
   }

   @Test void 
   que_encuentra_una_pelicula_por_su_id() throws ParseException {
      DateFormat release_parser = new SimpleDateFormat("dd/MM/yyyy");
      Movie movie = Movie.with("Un título", "Un resumen de la película", release_parser.parse("26/05/2024"), "sin imagen");

      MoviesStore store = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store);
      movies.add(movie);
      
      Optional<Movie> expected_movie = movies.find_movie_for_id(movie.its_id());

      
      assertThat(expected_movie.isPresent(), is(true));
   }
}