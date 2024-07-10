package com.opyguatec.movies_melon.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoviesTest {
   DateFormat release_parser = new SimpleDateFormat("dd/MM/yyyy");
   Movie movie_1 = null, movie_2 = null;

   @BeforeEach
   void setup() throws ParseException {
      movie_1 = Movie.with("titulo1", "sinopsis 1",
            release_parser.parse("26/04/2023"),
            "some-path", "some director", List.of());
      movie_2 = Movie.with("titulo2", "sinopsis 2",
            release_parser.parse("26/02/2020"),
            "some-other-path", "some other director", List.of());
   }

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
   que_encuentra_una_pelicula_por_su_id() throws MovieExistingError, Exception {
      Movie movie = movie_1;

      MoviesStore store = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store);
      movies.add(movie);
      
      Movie expected_movie = movies.find_movie_for_id(movie.its_id());

      
      assertThat(expected_movie, is(equalTo(movie)));
   }

   @Test void 
   que_puede_eliminar_una_pelicula_por_su_id() throws Exception {
      MoviesStore store = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store);
      movies.add( movie_1 );
      movies.add( movie_2 );
      String movie_1_id = movie_1.its_id();

      movies.remove_from_id( movie_1.its_id() );

      Movie actual = movies.find_movie_for_id(movie_1_id);
      assertThat( actual, is(nullValue()));
   }

   @Test
   void que_lanza_una_excepcion_si_la_pelicula_a_eliminar_no_existe() throws MovieExistingError, Exception {
      MoviesStore store = new StoreInMemory();
      MoviesMelon movies = new MoviesMelon(store);
      movies.add(movie_1);
      movies.add(movie_2);

      Exception exception = assertThrows(MovieNotFoundError.class, () -> {
         movies.remove_from_id("any_id");
      });

      String expectedMessage = "La película solicitada no se encuentra almacenada";
      String actualMessage = exception.getMessage();

      assertThat(actualMessage, containsString(expectedMessage));
   }
}