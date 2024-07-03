package com.opyguatec.movies_melon.data;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MoviesStore;

public class MysqlMoviesStore implements MoviesStore {

   private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/movies_melon";

   @Override
   public void add(Movie movie) {
      
   }

   @Override
   public List<Movie> listing() {
      return Arrays.asList();
   }

   @Override
   public boolean is_online() {
      try {
         Connection connection = DriverManager.getConnection(MYSQL_URL,
               "root", "e.m.a.11223");
         connection.createStatement()
         .executeQuery("CREATE TABLE movies(id varchar(36) primary key, title varchar(50) not null unique);");
         return !connection.isClosed();
      } catch (SQLException e) {
         System.err.println(e.getMessage());
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }
   

   public static void main(String []args) {
      try (Connection connection = DriverManager.getConnection(MYSQL_URL, "root", "e.m.a.11223")) {
         System.out.printf(">> %s\n", connection.isClosed() ? "closed":"open");
         connection.close();
      } catch (SQLException e) {
         e.printStackTrace();
      } {
   }
   }

   @Override
   public Movie find_by_id(String its_id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'find_by_id'");
   }
}
