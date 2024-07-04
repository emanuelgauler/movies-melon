package com.opyguatec.movies_melon.core;
import java.util.UUID;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Movie {
   private List<String> cast = new ArrayList<String>();
   private List<String> directors = new ArrayList<>();
   private List<String> genres = new ArrayList<>();

   public void add_an_actor( String actor_name ) {
      this.cast.add( actor_name );
   }

   public List<String> its_cast() {
      return this.cast;
   }

   public void add_actors(String ...actor) {
      this.cast.addAll(Arrays.asList(actor));
   }

   public boolean has_an_actor(String actor) {
      return cast.contains(actor);
   }

   public boolean has_actors(String ...actors) {
      return cast.containsAll(List.of(actors));
   }

	public boolean has_this_directors(String director) {
      return directors.contains(director);
	}

	public void add_an_director(String director) {
      this.directors.add( director );
	}

   public boolean has_genres(String ...genres ) {
      return this.genres.containsAll( List.of(genres) );
   }

   public void add_genres(String ...genres ) {
      this.genres.addAll( List.of( genres ) );
   }

   public interface GenreDumper {
      public void dump(List<String> genres);
      public List<String> items();
   }

   public void dump_on(GenreDumper dumper) {
      dumper.dump(genres);
   }


   private String title;
   public String getTitle() {
      return title;
   }


   private String synopsys;
   public String getSynopsys() {
      return synopsys;
   }


   private Date release;
   public Date getRelease() {
      return release;
   }


   private String poster;
   private String id;

   public String getPoster() {
      return poster;
   }

   public static Movie with(String title, String synopsys, Date release, String path_file) {
      Movie new_movie = new Movie();
      new_movie.id         = UUID.randomUUID().toString();
      new_movie.title      = title;
      new_movie.synopsys   = synopsys;
      new_movie.release    = release;
      new_movie.poster     = path_file;
      return new_movie;
   }

   public static Movie with( Movie other ) {
      return with(other.title, other.synopsys, other.release, other.poster);
   }

   public String its_id() {
      return this.id;
   }

   public void copy_value_of(Movie replacement) {
      title       = replacement.title;
      synopsys    = replacement.synopsys;
      release     = replacement.release;
      poster      = replacement.poster;
   }
}
