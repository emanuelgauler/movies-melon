package com.opyguatec.movies_melon.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.opyguatec.movies_melon.core.Movie;
import com.opyguatec.movies_melon.core.MovieExistingError;
import com.opyguatec.movies_melon.core.MoviesStore;

public class StoreInMemory implements MoviesStore {

   private static List<Movie> movies = new ArrayList<>();
   
   public StoreInMemory() throws ParseException {
      if (movies.isEmpty()) {
         DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
         movies.add(
               Movie.with("Furiosa: La Saga de Mad Max",
                     "En un mundo postapocalíptico donde todo ha perdido su valor, los pocos supervivientes se guían por la ley del más fuerte. Sin aprecio por la vida, lo único que despierta un brutal interés es la gasolina, sinónimo de poder y objetivo de bandas armadas hasta los dientes y sin escrúpulos. En este contexto conoceremos la historia de la arrebatadoramente despiadada, salvaje y joven Furiosa, quien cae en manos de una horda de motoristas y debe sobrevivir a muchas pruebas mientras reúne los medios para encontrar el camino de vuelta a casa. Precuela de 'Mad Max: Furia en la carretera' (2015).",
                     formatter.parse("23/05/2024"),
                     "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/tGHUlykWn9V2IIQ4ZaATIAq9VLB.jpg"));
         movies.add(
               Movie.with("Atlas",
                     "Una brillante analista antiterrorista recela de la IA hasta que descubre que podría ser su única esperanza cuando se tuerce una misión para atrapar a un robot rebelde.",
                     formatter.parse("24/5/2024"),
                     "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wSnBSv7oHgm1kZmiM8IqithlTmJ.jpg"));

      }
   }

   @Override
   public void add(Movie movie) {
      boolean movie_exists 
         = movies.stream().anyMatch( e -> e.getTitle().equals(movie.getTitle()));
      if( movie_exists ) throw new MovieExistingError(movie);

      movies.add(movie);
   }

   @Override
   public List<Movie> listing() {
      return movies;
   }

   @Override
   public boolean is_online() {
      return true;
   }

   @Override
   public Movie find_by_id(String its_id) {
      return movies.stream()
         .filter( e -> its_id.equals(e.its_id()) )
         .findFirst().orElse(null);
   }

}
