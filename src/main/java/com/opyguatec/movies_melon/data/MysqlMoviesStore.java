package com.opyguatec.movies_melon.data;

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

   private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/movies_melon";
   private static final String MYSQL_USER = "root";
   private static final String MYSQL_PASSWORD = "e.m.a.11223";
   private Connection connection;

   public MysqlMoviesStore() throws SQLException {
      this.connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
   }

   @Override
   public void add(Movie movie) throws MovieExistingError {

      try {
         open_connection();
         String instruction = "INSERT INTO movies(id, title, synopsys, director, release_date, poster) VALUES(?,?,?,?,?,?)";
         PreparedStatement create_command = connection
               .prepareStatement(instruction);

         java.sql.Date release_date = new Date( movie.getRelease().getTime() );
         create_command.setString(1, movie.its_id());
         create_command.setString(2, movie.getTitle());
         create_command.setString(3, movie.getSynopsys());
         create_command.setString(4, movie.getDirector());
         create_command.setDate(5, release_date);
         create_command.setString(6, movie.getPoster());
         create_command.executeUpdate();
         create_command.close();
         close_connection();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void close_connection() throws SQLException {
      connection.close();
   }

   private void open_connection() throws SQLException {
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
            Movie movie = new Movie();
            movie.copy_values_of(
               result.getString("id")
               , result.getString("title")
               , result.getString("synopsys")
               , result.getDate("release_date")
               , result.getString("director")
               , result.getString("poster")
               , List.of("a1", "a2", "a3")
               , List.of("ciencia ficción", "drama"));
               
            movies.add(movie);
               
         }
         close_connection();
      } catch (Exception e) {
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
      return new Movie();
   }

   @Override
   public void remove_from_id(String its_id) throws MovieNotFoundError {
   }

   @Override
   public void save(Movie movie) {
      try {
         open_connection();
         String instruction = "UPDATE movies SET "
         + "title=?, synopsys=?, director=?, release_date=?, poster=?"
         + " WHERE id=?;";
         PreparedStatement command
                     = connection.prepareStatement(instruction);
         java.sql.Date release_date = new Date(movie.getRelease().getTime());
         command.setString(1, movie.getTitle());
         command.setString(2, movie.getSynopsys());
         command.setString(3, movie.getDirector());
         command.setDate(4, release_date);
         command.setString(5, movie.getPoster());
         command.setString(6, movie.its_id());
         command.executeUpdate();
         command.close();
         close_connection();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
