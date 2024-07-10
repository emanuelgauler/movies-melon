package com.opyguatec.movies_melon.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MoviesStore;

public class MysqlMoviesStore_Test {
   private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/movies_melon";
   private static final String MYSQL_USER = "root";
   private static final String MYSQL_PASSWORD = "e.m.a.11223";
   private Connection connection;
   
   MysqlMoviesStore_Test() throws SQLException {
      connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
   }
   
   Movie make_a_movie(
      String synopsys
      , String title
      , String path
      , String director, List<String> cast, String release) throws ParseException {
      Date date_release = new SimpleDateFormat("dd/MM/yyyy")
            .parse(release);
      return Movie.with(title , synopsys, date_release, path , director , cast );
   }

   @BeforeEach public void 
   setup_db() throws SQLException {
      connection.createStatement().execute("DELETE FROM movies;");
   }
   
   @Test void 
   that_can_connect_to_server() throws SQLException {
      MoviesStore movies = new MysqlMoviesStore();

      boolean is_valid = movies.is_online();
      assertThat(is_valid, is(true));
   }
   


   @Test void
   that_can_add_a_movie() throws ParseException, SQLException {
      Date date = new SimpleDateFormat("dd/MM/yyyy")
            .parse("26/05/2024");
      List<String> cast = List.of("a1", "a2", "a3");
      Movie a_movie = Movie.with("title", "synopsys", date, "path-file"
      , "director", cast );

      MoviesStore movies = new MysqlMoviesStore();

      movies.add( a_movie );

      assertThat(movies.listing(), is(not(empty())));
   }

   @Test void 
   that_can_change_a_data_of_movie() throws SQLException, ParseException {
      MoviesStore movies = new MysqlMoviesStore();
      List<String> cast = List.of("A1", "A2");
      Date release_date = new SimpleDateFormat("dd-MM-yyyy").parse("25-05-2023");
      Movie movie = Movie.with("A title", "A synopsys", release_date, "a_path_file", "A director", cast);
      
      movies.add(movie);
      

      String new_synopsys = "Una sinopsis nueva";
      movie.copy_values_of(movie.getTitle()
      , new_synopsys
      , movie.getRelease()
      , movie.getPoster()
      , movie.getDirector()
      , movie.getCast()
      , movie.getGenres()
      );
      
      assertThat(movie.getSynopsys(), is(equalTo(new_synopsys)));

      movies.save(movie);

   }
}