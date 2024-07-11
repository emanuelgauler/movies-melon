package com.opyguatec.movies_melon.core;
import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Movie {
   private String id;
   private String title;
   private String synopsys;
   private String director;
   private Date release;
   private String poster;
   private List<String> cast = new ArrayList<String>();
   private List<String> genres = new ArrayList<String>();


   public List<String> getCast() {
      return cast;
   }

   public String getDirector() {
      return director;
   }

   public List<String> getGenres() {
      return genres;
   }

   public String getTitle() {
      return title;
   }


   public String getSynopsys() {
      return synopsys;
   }


   public Date getRelease() {
      return release;
   }


   public String getPoster() {
      return poster;
   }

   public static Movie with(
      String title
      , String synopsys
      , Date release
      , String path_file
      , String director
      , List<String> cast
      , List<String> genres) {
      Movie new_movie = new Movie();
      new_movie.id         = UUID.randomUUID().toString();
      new_movie.copy_values_of(title, synopsys, release, path_file, director, cast, genres);
      return new_movie;
   }

   public static Movie with( Movie other ) {
      return with(other.title, other.synopsys, other.release, other.poster, other.director, other.cast, other.genres);
   }

   public String its_id() {
      return this.id;
   }

   public void copy_value_of(Movie replacement) {
      copy_values_of(replacement.title, replacement.synopsys, replacement.release,
            replacement.poster,
            replacement.director, replacement.cast, replacement.genres);
   }

   public void copy_values_of( String title, String synopsys, Date release, String path_file, String director, List<String> cast, List<String> genres) {
      this.title        = title;
      this.synopsys     = synopsys;
      this.release      = release;
      this.director     = director;
      this.cast         = cast;
      this.genres       = genres;
      this.poster       = path_file;
   }

   public void copy_values_of(String id, String title
   , String synopsys, Date release, String path_file, String director,
         List<String> cast, List<String> genres) {
      this.id = id;
      this.copy_values_of(title, synopsys, release, path_file, director, cast, genres );
   }
}
