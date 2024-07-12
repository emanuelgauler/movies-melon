package com.opyguatec.movies_melon.data;

import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MovieExistingError;
import com.opyguatec.movies_melon.core.MovieNotFoundError;
import com.opyguatec.movies_melon.core.MoviesStore;

public class MysqlMoviesStore implements MoviesStore {

   private static Logger logger = Logger.getLogger(MysqlMoviesStore.class.getName());

   private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/movies_melon";
   private static final String MYSQL_USER = "root";
   private static final String MYSQL_PASSWORD = "e.m.a.11223";
   private Connection connection;

   @Override
   public void add(Movie movie) throws MovieExistingError, Exception {

      try {
         open_connection();
         String instruction = "INSERT INTO movies(id, title, synopsys, director, release_date, poster, cast, genres) VALUES(?,?,?,?,?,?,?,?)";
         PreparedStatement create_command = connection
               .prepareStatement(instruction);
         String cast    = cast_from_movie(movie);
         String genres  = genres_from_movie(movie);

         java.sql.Date release_date = new Date( movie.getRelease().getTime() );
         create_command.setString(1, movie.its_id());
         create_command.setString(2, movie.getTitle());
         create_command.setString(3, movie.getSynopsys());
         create_command.setString(4, movie.getDirector());
         create_command.setDate(5, release_date);
         create_command.setString(6, movie.getPoster());
         create_command.setString(7, cast);
         create_command.setString(8, genres);
         create_command.executeUpdate();
         create_command.close();

         close_connection();
      } catch (SQLException e ) {
         if (1062 == e.getErrorCode())
            throw new MovieExistingError(movie);
         else
            throw new Exception(String.format(">> ERROR: [%d] %s\n", e.getErrorCode(), e.getMessage()));
      } catch (Exception e) {
         e.printStackTrace();
         throw new Exception(e.getMessage());
      }
   }

   private String cast_from_movie(Movie movie) {
      return String.join(",", movie.getCast());
   }

   private String genres_from_movie(Movie movie) {
      return String.join(",", movie.getGenres());
   }

   private void close_connection() throws SQLException {
      connection.close();
   }

   private void open_connection() throws SQLException, ClassNotFoundException {
      Class.forName("com.mysql.jdbc.Driver");
      this.connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
   }

   @Override
   public List<Movie> listing() {
      List<Movie> movies = new ArrayList<>();
      try {
         open_connection();
         PreparedStatement select_movies = connection.prepareStatement("SELECT * FROM movies");
         ResultSet result = select_movies.executeQuery();
         while (result.next()) {
            Movie movie = make_movie_from(result);
               
            movies.add(movie);
               
         }
         close_connection();
      } catch (Exception e) {
         logger.warning(e.getMessage());
         e.printStackTrace();
      }
      return movies;
   }

   @Override
   public boolean is_online() {
      boolean result = false;
      try {
         result = connection.isValid(0);
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return result;
   }

   @Override
   public Movie find_by_id(String its_id) {
      Movie movie = null;
      try {
         open_connection();
         PreparedStatement query = connection.prepareStatement("SELECT * FROM movies WHERE id=?;");
         query.setString(1, its_id);
         ResultSet result = query.executeQuery();
         if (result.next()) {
            movie = make_movie_from(result);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return movie;
   }

   private Movie make_movie_from(ResultSet result) throws SQLException {
      Movie movie;
      String movie_id = result.getString("id");
      List<String> cast = cast_from_db( result.getString("cast") );
      List<String> genres  = genres_from_db( result.getString("genres"));
      movie = new Movie();
      movie.copy_values_of(
            movie_id
            , result.getString("title")
            , result.getString("synopsys")
            , result.getTimestamp("release_date")
            , result.getString("poster")
            , result.getString("director")
            , cast
            , genres);
      return movie;
   }

   private List<String> genres_from_db(String string) {
      return List.of(string.split(","));
   }

   private List<String> cast_from_db(String string) {
      return List.of(string.split(","));
   }

   @Override
   public void save(Movie movie) throws MovieExistingError, Exception {
      try {
         String cast    = cast_from_movie(movie);
         String genres  = genres_from_movie(movie);
         open_connection();
         String instruction = "UPDATE movies SET "
         + "title=?, synopsys=?, director=?, release_date=?, poster=?, cast=?, genres=?"
         + " WHERE id=?;";
         PreparedStatement command
                     = connection.prepareStatement(instruction);
         java.sql.Date release_date = new Date(movie.getRelease().getTime());
         command.setString(1, movie.getTitle());
         command.setString(2, movie.getSynopsys());
         command.setString(3, movie.getDirector());
         command.setDate(4, release_date);
         command.setString(5, movie.getPoster());
         command.setString(6, cast);
         command.setString(7, genres);
         command.setString(8, movie.its_id());
         command.executeUpdate();
         command.close();
         close_connection();
      } catch (SQLException e) {
         if (1062 == e.getErrorCode())
            throw new MovieExistingError(movie);
         else
            throw new Exception(String.format(">> ERROR: [%d] %s\n", e.getErrorCode(), e.getMessage()));
      } catch (Exception e) {
         throw new Exception(e.getMessage());
      }
   }

   @Override
   public void remove_from_id(String id) throws MovieNotFoundError {
      String instruction = "DELETE FROM movies WHERE id=?;";
      try {
         open_connection();
         PreparedStatement command = connection.prepareStatement(instruction);
         
         command.setString(1, id);
         command.executeUpdate();

         close_connection();
      } catch (Exception e) {
         e.printStackTrace();
         throw new MovieNotFoundError();
      }
   }

}
